package com.jacobs.jersey;

import com.google.common.collect.Maps;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Encoded;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.message.MessageUtils;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lichao on 2017/4/19.
 */
public class ContainerRequestUtil {

  private static final Logger log = LoggerFactory.getLogger(ContainerRequestUtil.class);
  private static final Annotation encodedAnnotation = getEncodedAnnotation();

  public ContainerRequestUtil() {
  }

  private static Annotation getEncodedAnnotation() {
    @Encoded
    final class EncodedAnnotationTemp {

      EncodedAnnotationTemp() {
      }
    }

    return EncodedAnnotationTemp.class.getAnnotation(Encoded.class);
  }

  public static boolean isMultipartRequest(ContainerRequest request) {
    return MediaTypes.typeEqual(MediaType.MULTIPART_FORM_DATA_TYPE, request.getMediaType());
  }

  public static MultivaluedMap<String, String> getFormParameters(ContainerRequest request,
      boolean decode) {
    if (!MediaTypes.typeEqual(MediaType.APPLICATION_FORM_URLENCODED_TYPE, request.getMediaType())) {
      MultivaluedMap rst1 = (new Form()).asMap();
      String bodyString1 = readBody(request);
      rst1.putSingle("body_param", bodyString1);
      return rst1;
    } else {
      request.bufferEntity();
      Form rst;
      if (decode) {
        rst = request.readEntity(Form.class);
        if (rst == null || rst.asMap().isEmpty()) {
          rst = (Form) request.getProperty("jersey.config.server.representation.decoded.form");
        }
      } else {
        Annotation[] bodyString = new Annotation[]{encodedAnnotation};
        rst = request.readEntity(Form.class, bodyString);
      }

      try {
        request.getEntityStream().reset();
      } catch (IOException var4) {
        log.error(var4.getMessage(), var4);
      }

      return rst == null ? (new Form()).asMap() : rst.asMap();
    }
  }

  public static String getParam(ContainerRequestContext requestContext, String key) {
    Map paramsMap = getParametersMap(requestContext);
    if (paramsMap.containsKey(key)) {
      String value = (String) paramsMap.get(key);
      if (StringUtils.isNotBlank(value)) {
        return value.trim();
      }
    }

    return null;
  }

  public static Map<String, String> getParametersMap(ContainerRequestContext requestContext) {
    Map rst = toMap(getParameters(requestContext));
    requestContext.setProperty("request_params", rst);
    return rst;
  }

  public static String getHeaderParam(ContainerRequestContext requestContext, String key) {
    MultivaluedMap map = requestContext.getHeaders();
    if (map != null) {
      List values = (List) map.get(key);
      if (CollectionUtils.isNotEmpty(values)) {
        return (String) values.get(0);
      }
    }

    return null;
  }

  private static Map<String, String> toMap(MultivaluedMap<String, String> parameters) {
    HashMap map = Maps.newHashMap();
    if (parameters != null && !parameters.isEmpty()) {
      Iterator var2 = parameters.keySet().iterator();

      while (var2.hasNext()) {
        String key = (String) var2.next();
        map.put(key, parameters.getFirst(key));
      }
    }

    return map;
  }

  public static Map<String, String> getPathParameters(ContainerRequestContext requestContext) {
    return toMap(requestContext.getUriInfo().getPathParameters());
  }

  public static MultivaluedMap<String, String> getParameters(
      ContainerRequestContext requestContext) {
    if ("GET".equalsIgnoreCase(requestContext.getMethod())) {
      return requestContext.getUriInfo().getQueryParameters();
    } else if (isMultipartRequest((ContainerRequest) requestContext)) {
      //// TODO: 2017/4/19  multipart request
      return new MultivaluedHashMap<>();
    } else {
      return MediaTypes
          .typeEqual(MediaType.APPLICATION_FORM_URLENCODED_TYPE, requestContext.getMediaType())
          ? getFormParameters((ContainerRequest) requestContext, true) : (new Form()).asMap();
    }
  }

  public static String readBody(ContainerRequestContext requestContext) {
    StringBuilder b = new StringBuilder();

    try {
      InputStream e = logInboundEntity(b, requestContext.getEntityStream(),
          MessageUtils.getCharset(requestContext.getMediaType()));
      requestContext.setEntityStream(e);
    } catch (IOException var3) {
      var3.printStackTrace();
    }

    return b.toString();
  }

  public static InputStream logInboundEntity(StringBuilder b, InputStream stream, Charset charset)
      throws IOException {
    if (!(stream).markSupported()) {
      stream = new BufferedInputStream(stream);
    }

    (stream).mark(2147483647);
    byte[] entity = IOUtils.toByteArray(stream);
    b.append(new String(entity, 0, entity.length, charset));
    stream.reset();
    return stream;
  }

  public static String getRemoteIP(ContainerRequestContext requestContext) {
    String ip = requestContext.getHeaderString("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = requestContext.getHeaderString("Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = requestContext.getHeaderString("WL-Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = requestContext.getHeaderString("x-real-ip");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = requestContext.getUriInfo().getRequestUri().getHost();
    }

    if (ip.contains(",")) {
      String[] res = ip.split(",");
      ip = res[0].trim();
    }

    return ip;
  }
}
