package com.jacobs.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.helpers.Transform;
import ch.qos.logback.core.html.IThrowableRenderer;

/**
 * Created by lichao on 2017/3/16.
 */
public class MyThrowableRender implements IThrowableRenderer<ILoggingEvent> {
  @Override
  public void render(StringBuilder stringBuilder, ILoggingEvent iLoggingEvent) {
    IThrowableProxy tp = iLoggingEvent.getThrowableProxy();
    while (tp != null) {
      render(stringBuilder, tp);
      tp = tp.getCause();
    }
  }

  void render(StringBuilder sbuf, IThrowableProxy tp) {
    printFirstLine(sbuf, tp);

    int commonFrames = tp.getCommonFrames();
    StackTraceElementProxy[] stepArray = tp.getStackTraceElementProxyArray();

    for (int i = 0; i < stepArray.length - commonFrames; i++) {
      StackTraceElementProxy step = stepArray[i];
      sbuf.append(Transform.escapeTags(step.toString()));
      sbuf.append(CoreConstants.LINE_SEPARATOR);
    }

    if (commonFrames > 0) {
      sbuf.append("\t... ")
          .append(commonFrames)
          .append(" common frames omitted")
          .append(CoreConstants.LINE_SEPARATOR);
    }
  }

  public void printFirstLine(StringBuilder sb, IThrowableProxy tp) {
    int commonFrames = tp.getCommonFrames();
    if (commonFrames > 0) {
      sb.append(CoreConstants.CAUSED_BY);
    }
    sb.append(tp.getClassName())
        .append(": ")
        .append(Transform.escapeTags(tp.getMessage()));
    sb.append(CoreConstants.LINE_SEPARATOR);
  }
}
