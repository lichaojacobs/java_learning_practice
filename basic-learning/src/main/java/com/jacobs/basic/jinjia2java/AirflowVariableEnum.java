package com.jacobs.basic.jinjia2java;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 自定义sql兼容airflow macros 对时间的操作
 */
public enum AirflowVariableEnum implements Operation {
    DS("ds") {

        @Override
        public String apply() {
            return addDays(-1, DATE_TIME_FORMATTER);
        }
    },

    DS_NO_DASH("ds_nodash") {

        @Override
        public String apply() {
            return addDays(-1, DATE_TIME_FORMATTER_NO_DASH);
        }
    };

    private String name;

    public String getName() {
        return name;
    }

    AirflowVariableEnum(String name) {
        this.name = name;
    }
}

interface Operation {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTimeFormatter DATE_TIME_FORMATTER_NO_DASH = DateTimeFormat.forPattern("yyyyMMdd");

    String apply();

    default String addDays(int days, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.print(LocalDate.now().plusDays(days));
    }
}
