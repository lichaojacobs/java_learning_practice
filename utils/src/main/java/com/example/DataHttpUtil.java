package com.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Strings;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Credentials;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lichao on 2017/4/12.
 */
@Data
@Slf4j
public class DataHttpUtil {

  private static final int DEFAULT_MAX_IDLE_CONNECTION = 100;
  private static final int DEFAULT_KEEPALIVE_DURATION = 100;
  private static final int DEFAULT_CONTENT_TIME_OUT = 1000;
  private static final int DEFAULT_READ_TIME_OUT = 1000;
  private static final int DEFAULT_WRITE_TIME_OUT = 1000;

  private OkHttpClient okHttpClient;

  enum HttpMethod {
    GET(), POST()
  }

  private <T, R> Function<T, R> unChecked(UnCheckedFunction<T, R> function) {
    return t -> {
      try {
        return function.apply(t);
      } catch (Exception e) {
        //log.warn("UnCheckedFunction ex: ", e);
      }
      return null;
    };
  }


  @Builder()
  private DataHttpUtil(Integer maxIdleConnection, Integer keepaliveDuration, Integer connectTimeout,
      Integer readTimeout, Integer writeTimeout, ExecutorService executorService) {
    final ConnectionPool connectionPool = new ConnectionPool(
        Optional.ofNullable(maxIdleConnection).orElse(DEFAULT_MAX_IDLE_CONNECTION),
        Optional.ofNullable(keepaliveDuration).orElse(DEFAULT_KEEPALIVE_DURATION),
        TimeUnit.SECONDS);

    okHttpClient = new OkHttpClient.Builder()
        .connectionPool(connectionPool)
        .connectTimeout(Optional.ofNullable(connectTimeout).orElse(DEFAULT_CONTENT_TIME_OUT),
            TimeUnit.MILLISECONDS)
        .readTimeout(Optional.ofNullable(readTimeout).orElse(DEFAULT_READ_TIME_OUT),
            TimeUnit.MILLISECONDS)
        .writeTimeout(Optional.ofNullable(writeTimeout).orElse(DEFAULT_WRITE_TIME_OUT),
            TimeUnit.MILLISECONDS)
        .dispatcher(new Dispatcher(executorService))
        .build();
  }

  public DataRequest get(String url) {
    return new DataRequest(url, HttpMethod.GET);
  }

  public DataRequest post(String url) {
    return new DataRequest(url, HttpMethod.POST);
  }

  public class DataRequest {

    private URL url;
    private HttpMethod method;
    private List<Parameter> header = new ArrayList<>();
    private List<Parameter> param = new ArrayList<>();

    private String json;
    private boolean jsonBody = false;
    private Auth auth;

    public DataRequest(String url, HttpMethod method) {
      try {
        this.url = new URL(url);

      } catch (MalformedURLException ex) {
        throw new RuntimeException(ex);
      }
    }

    public DataRequest header(String k, String v) {
      header.add(Parameter.of(k, v));
      return this;
    }

    public DataRequest header(Map<String, String> headers) {
      headers.forEach(this::header);
      return this;
    }

    public DataRequest param(String k, String v) {
      param.add(Parameter.of(k, v));
      return this;
    }

    public DataRequest paramIf(String k, String v) {
      if (Objects.nonNull(v)) {
        param.add(Parameter.of(k, v));
      }
      return this;
    }

    public DataRequest param(Map<String, String> params) {
      params.forEach(this::param);
      return this;
    }

    public DataRequest body(String json) {
      this.json = json;
      this.jsonBody = true;
      return this;
    }

    public DataRequest auth(String username, String password) {
      this.auth = Auth.of(username, password);
      return this;
    }

    public HttpResponse send() {
      HttpUrl httpUrl = getHttpUrl();
      Request request = getRequest(httpUrl);
      Call call = okHttpClient.newCall(request);
      return getHttpResponse(call);
    }

    public Optional<HttpResponse> send(long timeout, TimeUnit unit) {
      Future<HttpResponse> async = async();
      try {
        return Optional.of(async.get(timeout, unit));
      } catch (Exception e) {
        async.cancel(true);
        return Optional.empty();
      }
    }

    public Optional<HttpResponse> send(int retry, long timeout, TimeUnit unit) {
      return Stream.generate(this::async)
          .map(unChecked(async -> async.get(timeout, unit)))
          .limit(retry)
          .filter(Objects::nonNull)
          .findFirst();
    }

    public Future<HttpResponse> async() {
      HttpUrl httpUrl = getHttpUrl();
      Request request = getRequest(httpUrl);
      CompletableFuture<HttpResponse> future = new CompletableFuture<>();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          future.completeExceptionally(e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          future.complete(getHttpResponse(response));
        }
      });
      future.whenCompleteAsync((r, e) -> {
        if (Objects.isNull(r) && CancellationException.class.isInstance(e)) {
          call.cancel();
        }
      });
      return future;
    }

    public void async(Consumer<HttpResponse> callback) {
      HttpUrl httpUrl = getHttpUrl();
      Request request = getRequest(httpUrl);
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          callback.accept(new HttpResponse(e));
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          callback.accept(getHttpResponse(response));
        }
      });
    }

    private HttpResponse getHttpResponse(Call call) {
      try {
        return new HttpResponse(call.execute());
      } catch (IOException e) {
        return new HttpResponse(e);
      }
    }

    private HttpResponse getHttpResponse(Response response) {
      return new HttpResponse(response);
    }

    private Request getRequest(HttpUrl httpUrl) {
      Request.Builder builder = new Request.Builder()
          .url(httpUrl);

      header.forEach(params -> builder.header(params.getKey(), params.getValue()));

      if (HttpMethod.POST.equals(method)) {
        if (jsonBody) {
          RequestBody requestBody = RequestBody
              .create(MediaType.parse("application/json; charset=utf-8"), json);
          builder.post(requestBody);
        } else {
          FormBody.Builder formBodyBuilder = new FormBody.Builder();
          param.forEach(params -> formBodyBuilder.add(params.getKey(), params.getValue()));
          builder.post(formBodyBuilder.build());
        }
      }

      if (auth != null) {
        builder
            .addHeader("Authorization", Credentials.basic(auth.getUsername(), auth.getPassword()));
      }

      return builder.build();
    }

    private HttpUrl getHttpUrl() {
      HttpUrl.Builder builder = new HttpUrl.Builder()
          .scheme(url.getProtocol())
          .host(url.getHost())
          .addPathSegments(
              url.getPath().startsWith("/") ? url.getPath().substring(1) : url.getPath())
          .query(url.getQuery());

      if (-1 != url.getPort()) {
        builder.port(url.getPort());
      }

      if (HttpMethod.GET.equals(method)) {
        param.forEach(params -> builder.addQueryParameter(params.getKey(), params.getValue()));
      }

      return builder.build();
    }
  }

  @Data
  public class HttpResponse {

    private final Map<String, List<String>> headers;
    private final int code;
    private boolean isJson = false;
    private byte[] body;
    private boolean successful = false;
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private IOException ioException;
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private Supplier<Exception> exceptionSupplier;

    HttpResponse(Response response) {
      this.headers = response.headers().toMultimap();
      this.code = response.code();
      try {
        this.body = response.body().bytes();
        this.successful = response.isSuccessful();
        this.isJson = !Strings.isNullOrEmpty(response.header("Content-Type")) && response
            .header("Content-Type").contains("json");
      } catch (IOException e) {
        this.ioException = e;
      }
      this.exceptionSupplier =
          () -> successful ? new NullPointerException(
              "response body is null, http code:" + code) : ioException;
    }

    HttpResponse(IOException e) {
      this.ioException = e;
      this.headers = Collections.emptyMap();
      this.code = -1;
      this.exceptionSupplier = () -> ioException;
    }

    public String string() throws Exception {
      return tryString().orElseThrow(exceptionSupplier);
    }

    public JSONObject json() throws Exception {
      return tryJson().orElseThrow(exceptionSupplier);
    }

    public <T> T object(Class<T> tClass) throws Exception {
      return tryObject(tClass).orElseThrow(exceptionSupplier);
    }

    public Optional<String> tryString() {

      return Optional.ofNullable(this.body).map(body -> new String(body, StandardCharsets.UTF_8));

    }

    public Optional<JSONObject> tryJson() {
      if (isJson) {
        return tryString().map(JSON::parseObject);
      } else {
        return Optional.empty();
      }
    }

    public Optional<JSONArray> tryJsonArray() {
      if (isJson) {
        return tryString().map(JSON::parseArray);
      } else {
        return Optional.empty();
      }
    }


    public <T> Optional<T> tryObject(Class<T> tClass) {
      return tryJson().map(json -> json.toJavaObject(tClass));
    }

    public RequestResponse toResponse() throws Exception {
      return new RequestResponse(isJson ? string() : null);
    }

    public Optional<RequestResponse> tryResponse() {
      return tryString().map(str -> isJson ? str : null).map(RequestResponse::new);
    }

    public Optional<JSONObject> tryOptionalJson() {
      return tryResponse().map(RequestResponse::getBody).map(JSON::parseObject);
    }

    public Optional<JSONArray> tryOptionalJsonArray() {
      return tryResponse().map(RequestResponse::getBody).map(JSON::parseArray);
    }

    public <T> Optional<T> tryOptionalObject(Class<T> tClass) {
      return tryOptionalJson().map(json -> json.toJavaObject(tClass));
    }
  }

  @Data
  public class RequestResponse {

    private Error error;
    private String body;
    private boolean successful = false;

    RequestResponse(String body) {
      if (body == null) {
      } else if (body.contains("error_code")) {
        this.error = JSON.parseObject(body, Error.class);
      } else {
        this.body = body;
        this.successful = true;
      }
    }

    public JSONObject json() {
      return Optional.ofNullable(body).map(JSON::parseObject).orElse(null);
    }

    public JSONArray jsonArray() {
      return Optional.ofNullable(body).map(JSON::parseArray).orElse(null);
    }

    public <T> T object(Class<T> tClass) {
      return Optional.ofNullable(json()).map(json -> json.toJavaObject(tClass)).orElse(null);
    }
  }

  @Data
  static public class Error {

    @JSONField(name = "display_msg")
    private String display_msg;
    @JSONField(name = "error_code")
    private int error_code;
    @JSONField(name = "error_msg")
    private String error_msg;
    @JSONField(name = "http_code")
    private int http_code;
    @JSONField(name = "request_uri")
    private String request_uri;
  }

  @Data
  @AllArgsConstructor(staticName = "of")
  private static class Parameter {

    private String key;
    private String value;
  }

  @Data
  @AllArgsConstructor(staticName = "of")
  private static class Auth {

    private String username;
    private String password;
  }

}
