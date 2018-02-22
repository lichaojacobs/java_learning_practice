package com.jacobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lichao on 2016/12/16.
 */
@Slf4j
@SpringBootTest
public class JerseyTest {

    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:9000");
    private static final Gson GSON = new Gson();
    private static final ExecutorService HTTP_THREAD_POOL = Executors.newFixedThreadPool(10);

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target.path("v1/hello/message").request().get(String.class);
        System.out.println("Got it" + responseMsg);
    }

    public static void generateHelloworld() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main") // main代表方法名
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)// Modifier 修饰的关键字
                .addParameter(String[].class, "args") // 添加string[]类型的名为args的参数
                .addStatement("$T.out.println($S)", System.class, "Hello World")// 添加代码，这里$T和$S后面会讲，这里其实就是添加了System,out.println("Hello
                                                                                // World");
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")// HelloWorld是类名
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC).addMethod(main) // 在类中添加方法
                .build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec).build();

        javaFile.writeTo(System.out);
    }

    @Test
    public void testGraph() {
        String responseMsg = client
                .target("http://10.77.110.125/render?&target=sumSeries(sinakeeper.mcq.13702.10_73_*.wmb.heap_size)&from=-1min&format=json")
                .request().get(String.class);
        JsonArray jsonArray = new Gson().fromJson(responseMsg, JsonArray.class);
        System.out.printf(responseMsg.toString());

        JsonArray array = (JsonArray) ((JsonObject) jsonArray.get(0)).getAsJsonArray("datapoints");

        System.out.printf(array.toString());
    }

    @Test
    public void testGraph2() {
        Type type = new TypeToken<ArrayList<GraphiteData>>() {}.getType();
        String responseMsg = client
                .target("http://10.77.110.125/render?&target=sumSeries(sinakeeper.mcq.13702.10_73_*.wmb.heap_size)&from=-1min&format=json")
                .request().get(String.class);
        ArrayList<GraphiteData> graphiteDataArrayList = GSON.fromJson(responseMsg, type);
        System.out.printf(GSON.toJson(graphiteDataArrayList));
        Double num = graphiteDataArrayList.get(0).getDataPoints().stream().filter(list -> list.get(0) != null)
                .collect(Collectors.averagingDouble(value -> value.get(0)));
        System.out.println(num.intValue());
    }

    // @Test
    // public void testGraph3() throws Exception {
    //
    // }

    public static void main(String[] args) throws Exception {
        JerseyTest jerseyTest = new JerseyTest();
        Map<String, Integer> resultMap = jerseyTest.getWmbDataByKeyFromGraphite();
        System.out.println(GSON.toJson(resultMap));
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }


    public Map<String, Integer> getWmbDataByKeyFromGraphite() throws Exception {
        Map<String, Integer> result = new HashMap<>();
        List<Future<Map<String, Integer>>> tasks = new ArrayList<>();

        // 13702_wmb_yf
        tasks.add(submitWmbQueryByKeyFromGraphite("yf", "wmb", "13702", "10_75_*"));
        // 13702_wmb_tc)
        tasks.add(submitWmbQueryByKeyFromGraphite("tc", "wmb", "13702", "10_73_*"));

        // 13704_tcV45_ipenapi_gz
        tasks.add(submitWmbQueryByKeyFromGraphite("tc", "tcV45_openapi_gz", "13704", "10_73_*"));
        // 13704_yfV45_ipenapi_gz
        tasks.add(submitWmbQueryByKeyFromGraphite("yf", "yfV45_openapi_gz", "13704", "10_75_*"));
        // 13704_aliV45_ipenapi_gz
        tasks.add(submitWmbQueryByKeyFromGraphite("aliyun", "aliV45_openapi_gz", "13704", "10_85_*"));

        for (Future<Map<String, Integer>> future : tasks) {
            Map<String, Integer> tempResultMap = future.get();
            if (!tempResultMap.isEmpty()) {
                result.putAll(tempResultMap);
            }
        }

        return result;
    }

    private Future<Map<String, Integer>> submitWmbQueryByKeyFromGraphite(final String pool, final String key, final String port,
            final String ipRange) {
        return HTTP_THREAD_POOL.submit(() -> getWmbDetailData(pool, port, ipRange, key));
    }

    public Map<String, Integer> getWmbDetailData(String pool, String port, String ipRange, String key) {
        String[] types = new String[] {"heap_size", "set"};
        String requestUrl = String.format(
                "http://10.77.110.125/render?&target=sumSeries(sinakeeper.mcq.%s.%s.%s.%s)&target=sumSeries(sinakeeper.mcq.%s.%s.%s.%s)&from=-1min&format=json",
                port, ipRange, key, types[0], port, ipRange, key, types[1]);
        log.info("requestUrl: " + requestUrl);
        // pool_key_port_type
        String resultKeyPrefix = String.format("%s_%s_%s_", pool, key, port);
        Map<String, Integer> result = new HashMap<>();
        String responseMsg = client.target(requestUrl).request().get(String.class);
        log.info("responseMsg {}", responseMsg);
        try {
            Type typeToken = new TypeToken<ArrayList<GraphiteData>>() {}.getType();
            ArrayList<GraphiteData> graphiteDataArrayList = GSON.fromJson(responseMsg, typeToken);
            log.info(GSON.toJson(graphiteDataArrayList));

            // 对于heap_size以及set分别取最近一分钟的数据求平均值
            for (int index = 0; index < graphiteDataArrayList.size(); index++) {
                Double averageValue = graphiteDataArrayList.get(index).getDataPoints().stream().filter(list -> list.get(0) != null)
                        .collect(Collectors.averagingDouble(value -> value.get(0)));
                result.put(resultKeyPrefix + types[index], averageValue.intValue());
            }
        } catch (Exception e) {
            log.error(new StringBuilder().append("getWmbDetailData error, [url]:").append(requestUrl).append(", [response]: ")
                    .append(responseMsg).append("[errorMsg]: ").append(e.getMessage()).toString());
        }

        log.info("resultMap: {}", result);
        return result;
    }

    //99+238+14+
    @Test
    public void testGetIps() {
        System.out.println(getIps("openapi_webv2-yf-outer-docker"));
    }

    private final static String RAPTOR_BASE_URL = "http://raptor.api.weibo.com:5353";
    private final static String RAPTOR_USER = "leijian";
    private final static String RAPTOR_APPKEY = "B38541C6D897A389F13C6A9D883C5264";
    private final static String GET_POOL_IPS_URL = RAPTOR_BASE_URL + "/v1/manage/node/list/?user=%s&appkey=%s&service_pool=%s&limit=1000";
    private final static int TIMEOUT_IN_SECONDS = 5;
    private final static int TRY_COUNTS = 2;
    private final static OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).build();

    public int getIps(String poolName) {
        List<String> ips = Lists.newArrayList();
        String url = String.format(GET_POOL_IPS_URL, RAPTOR_USER, RAPTOR_APPKEY, poolName);
        Request request = new Request.Builder().url(url).get().build();
        String respText = null;
        Response response = null;
        for (int tryCount = 0; tryCount < TRY_COUNTS; tryCount++) {
            long startTime = System.currentTimeMillis();
            try {
                response = HTTP_CLIENT.newCall(request).execute();
                respText = response.body().string();
                JSONObject respJObject = JSONObject.parseObject(respText);
                JSONArray dataContentJArray = respJObject.getJSONObject("data").getJSONArray("content");
                for (int i = 0; i < dataContentJArray.size(); i++) {
                    JSONObject dataContentJObject = dataContentJArray.getJSONObject(i);
                    String ip = dataContentJObject.getString("ip");
                    ips.add(ip);
                }
                log.info("time-cost={}, finish getIps: tryCount={}, poolName={}, ips={}, statusCode={}, url={}",
                        System.currentTimeMillis() - startTime, tryCount, poolName, ips.size(), response.code(), url);
                return ips.size();
            } catch (Exception e) {
                log.error("time-cost={}, failed to getIps: tryCount={}, poolName={}, statusCode={}, url={}",
                        System.currentTimeMillis() - startTime, tryCount, poolName, response == null ? -1 : response.code(), url, e);
            }
        }
        return ips.size();
    }
}
