package com.jacobs.logger;

import java.util.Date;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import ch.qos.logback.core.html.IThrowableRenderer;

/**
 * Created by lichao on 2017/3/16.
 */
public class MyLogLayOut extends LayoutBase<ILoggingEvent> {

  public static String PROJECT_NAME = "unknown-project-name";

  IThrowableRenderer<ILoggingEvent> throwableRenderer = new MyThrowableRender();

  private final static ThreadLocal<String> currentTraceId = new ThreadLocal<String>() {
    @Override
    protected String initialValue() {
      return "NTI";
    }
  };

  @Override
  public String doLayout(ILoggingEvent event) {
    StackTraceElement[] stackTrace = event.getCallerData();

    String lineNumber;
    String methodName;
    if (stackTrace != null && stackTrace.length > 0) {
      lineNumber = Integer.toString(stackTrace[0].getLineNumber());
      methodName = stackTrace[0].getMethodName();
    } else {
      lineNumber = CallerData.NA;
      methodName = CallerData.NA;
    }

    String traceId = currentTraceId.get();

    StringBuilder sb = new StringBuilder(256);
    sb.append("[Java_learning_practice] ");
    sb.append(new Date().toString())
        .append(" ");
    sb.append(event.getLevel());
    sb.append(" [")
        .append(event.getThreadName())
        .append("] ");
    sb.append(event.getLoggerName())
        .append(" ");
    sb.append(methodName)
        .append(" ");
    sb.append(lineNumber)
        .append(": ");
    sb.append("[Java_learning_practice] ")
        .append("|");
    sb.append(traceId)
        .append("|");
    sb.append(event.getFormattedMessage());
    if (event.getThrowableProxy() != null) {
      sb.append(CoreConstants.LINE_SEPARATOR);
      throwableRenderer.render(sb, event);
    }
    sb.append(CoreConstants.LINE_SEPARATOR);
    return sb.toString();
  }

  public static void setCurrentTraceId(String traceId) {
    MyLogLayOut.currentTraceId.set(traceId);
  }

  public static String getCurrentTraceId() {
    return MyLogLayOut.currentTraceId.get();
  }
}
