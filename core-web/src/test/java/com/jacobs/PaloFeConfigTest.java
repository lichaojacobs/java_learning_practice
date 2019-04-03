package com.jacobs;

import com.jacobs.models.Config;
import java.io.FileReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lichao
 * @date 2018/11/17
 */
public class PaloFeConfigTest {

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface ConfField {

        String value() default "";
    }

    public static Properties props = new Properties();

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.init("/Users/lichao15/Downloads/palo-0.8.2-release-20180824/fe/conf/fe.conf");
        System.out.println();
    }
}
