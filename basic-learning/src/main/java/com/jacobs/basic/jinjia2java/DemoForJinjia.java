package com.jacobs.basic.jinjia2java;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;

public class DemoForJinjia {

    public static void main(String[] args) throws Exception {
        String rawStr = "Hello, {{ name }}!";

        Jinjava jinjava = new Jinjava();
        Map<String, Object> context = Maps.newHashMap();
        context.put("name", "Jared");

        //        String template = Resources.toString(Resources.getResource("my-template.html"),
        //                Charsets.UTF_8);

        String renderedTemplate = jinjava.render(rawStr, context);
        System.out.println(renderedTemplate);
    }
}
