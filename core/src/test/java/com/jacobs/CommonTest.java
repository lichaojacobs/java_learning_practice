package com.jacobs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.LockSupport;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Created by lichao on 2016/11/24.
 */
public class CommonTest {

    private HashMap<String, Student> studentHashMap = new HashMap();
    private Student student = new Student("张三");

    public static void main(String[] args) throws Exception {
        //    System.out.println(words.stream().reduce((s, s2) -> s + s2).get());
        //        Pattern pattern = Pattern.compile("^.*\\.([^.]*)\\.count.*");
        //        Matcher matcher = pattern.matcher("application_1523784538522_4680.1.jvm.G1-Old-Generation.count");
        //        matcher.group();
        //        List<String> result = Lists.newArrayList("123", "145434");
        ////        List<String> result2 = Lists.newArrayList(result);
        ////        result = Lists.newArrayList();
        ////        result.forEach(System.out::println);
        ////        result2.forEach(System.out::println);
        ////
        ////        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        ////        LocalDate fromDatDate = LocalDate.parse("20180719", dayFormatter);
        ////        LocalDate toDatDate = LocalDate.parse("20180723", dayFormatter);
        ////        System.out.println(Period.between(fromDatDate, toDatDate).getDays());
        ////        System.out.println(fromDatDate.plusDays(4).format(dayFormatter));
        //        Thread thread = new Thread(() -> {
        //            LockSupport.park();
        //            System.out.println(Thread.currentThread().getName() + "被唤醒");
        //        });
        //        thread.start();
        //        try {
        //            Thread.sleep(10000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        LockSupport.unpark(thread);
    }


    public static void testQuery(String filePath, int rate) throws Exception {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(30000);
        requestBuilder = requestBuilder.setSocketTimeout(30000);
        httpClientBuilder.setDefaultRequestConfig(requestBuilder.build());
        HttpClient client = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost("http://streaming.mobvoi.com/speech2text");
        // 文件路径
        httpPost.setEntity(new InputStreamEntity(new FileInputStream(filePath)));
        // 16000是采样率
        httpPost.setHeader("Content-Type", String.format("audio/x-wav;rate=%d", rate));
        try {
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
                return;
            }
            System.out.println(
                "Fail to do speech recognition with status code " + statusCode + ", " + EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to do speech recognition.");
        } finally {
            System.out.println("closeing resource...");
        }
    }
}
