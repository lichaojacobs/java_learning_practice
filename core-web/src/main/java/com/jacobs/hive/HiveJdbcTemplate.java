package com.jacobs.hive;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author lichao
 * Created on 2019-06-20
 */
public class HiveJdbcTemplate extends JdbcTemplate {

    public HiveJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }
}
