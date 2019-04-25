package com.jacobs.basic.jinjia2java;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.ImmutableMap;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.RenderResult;

/**
 * 适配airflow sql中macros
 */
public class MacrosUtil {

    private static Logger logger = LoggerFactory.getLogger(MacrosUtil.class);
    private static final Jinjava JIN_JAVA = new Jinjava(
            JinjavaConfig.newBuilder().withFailOnUnknownTokens(true).build());

    /**
     * 适配macros 中ds_add方法，之所以用下划线命名是为了完全兼容airflow sql写法
     * 如可以让业务方适配则可调整成Java中正确的命名规范
     * @param ds 执行日期 YYYY-MM-DD
     * @param days 需要增加的天数
     * @return
     */
    public String ds_add(String ds, int days) {
        return AirflowVariableEnum.DATE_TIME_FORMATTER
                .print(AirflowVariableEnum.DATE_TIME_FORMATTER.parseDateTime(ds).plusDays(days));
    }

    /**
     * 兼容airflwo macros 中ds_format方法
     * @param ds 执行日期
     * @param inputFormat 日期输入格式
     * @param outputFormat 日期输出格式
     * @return
     */
    public String ds_format(String ds, String inputFormat, String outputFormat) {
        return DateTimeFormat.forPattern(inputFormat).parseDateTime(ds).toString(outputFormat);
    }

    /**
     * 获取需要替换raw sql中的配置map
     * @return
     */
    public static Map<String, Object> getAirflowVariableMap() {
        return ImmutableMap.of(AirflowVariableEnum.DS.getName(), AirflowVariableEnum.DS.apply(),
                AirflowVariableEnum.DS_NO_DASH.getName(), AirflowVariableEnum.DS_NO_DASH.apply(),
                "macros", new MacrosUtil());
    }

    /**
     * hard code: 支持python中日期format格式
     * @param rawSql
     * @return
     */
    private static String fitPythonDateStr(String rawSql) {
        return rawSql.replaceAll("%Y", "yyyy").replaceAll("%m", "MM").replaceAll("%d", "dd");
    }

    /**
     * 用于保存算子时验证自定义sql是否满足格式要求
     * @param rawSql
     */
    public static void vlidateCustomSql(String rawSql) {
        if (StringUtils.isBlank(rawSql)) {
            throw new RuntimeException("自定义sql为空");
        }
        RenderResult renderResult = getRenderResult(fitPythonDateStr(rawSql));
        if (!CollectionUtils.isEmpty(renderResult.getErrors())) {
            logger.warn("vlidateCustomSql renderResult has errors: {}, output is: {}",
                    renderResult.getErrors(), renderResult.getOutput());
            throw new RuntimeException("自定义sql有不支持的函数操作");
        }
    }

    /**
     * 用于生成python代码时转换成实际的sql查询语句
     * @param rawSql
     * @return
     */
    public static String getTransformedSql(String rawSql) {
        RenderResult renderResult = getRenderResult(fitPythonDateStr(rawSql));
        return renderResult.getOutput();
    }

    public static RenderResult getRenderResult(String rawSql) {
        Map<String, Object> context = getAirflowVariableMap();
        RenderResult renderResult = JIN_JAVA.renderForResult(rawSql, context);
        return renderResult;
    }
}
