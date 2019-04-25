package com.jacobs.basic.jinjia2java;

import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.RenderResult;
import com.hubspot.jinjava.lib.Importable;

public class DemoForJinjia implements Importable {

    public static void main(String[] args) throws Exception {
        //        String rawStr = "Hello, {{macros.ds_add(ds,2)}}!";
        //
        //        JinjavaConfig outputSizeLimitedConfig = JinjavaConfig.newBuilder()
        //                .withFailOnUnknownTokens(true).withMaxOutputSize(20).build();
        //
        //        Jinjava jinjava = new Jinjava(outputSizeLimitedConfig);
        //        Map<String, Object> context = MacrosUtil.getAirflowVariableMap();
        //        jinjava.getGlobalContext()
        //                .registerFunction(new CustomELFunctionDefinition("test", "testUtilRegistry",
        //                        DemoForJinjia.class, "testUtilRegistry", Double.class, Double.class));
        //        jinjava.getGlobalContext().registerClasses(DemoForJinjia.class);

        //        String template = Resources.toString(Resources.getResource("my-template.html"),
        //                Charsets.UTF_8);

        //        String renderedTemplate = jinjava.render(rawStr, context);
        //        RenderResult renderResult = jinjava.renderForResult(rawStr, context);
        //        System.out.println(renderResult);
        //        System.out.println(new MacrosUtil().ds_format("20190419", "yyyyMMdd", "yyyy-MM-dd"));

        String rawSql = "select a.uid\n" + "from \n" + "(\n" + "(select identity_user_id as uid\n"
                + "from ks_dws.dw_dws_user_userid_activeuser_di\n" + "where p_date between\n"
                + "'{{ macros.ds_format(macros.ds_add(ds, -13), \"%Y-%m-%d\", \"%Y%m%d\") }}'"
                + " and" + " \n" + "'{{ macros.ds_format(macros.ds_add(ds, -7), \"%Y-%m-%d\", "
                + "\"%Y%m%d\") }}'\n and p_date={{ds_nodash}}" + " and location_country = '中国'";

        System.out.println(rawSql);

        System.out.println(MacrosUtil.getTransformedSql(rawSql));
    }

    public double testUtilRegistry(Double x1, Double x2) {
        return x1 + x2;
    }

    @Override
    public String getName() {
        return "DemoForJinjia";
    }
}
