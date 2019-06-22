package com.jacobs.hive;

import java.util.List;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.jacobs.hive.config.HiveJdbcPoolConfig;

/**
 * @author lichao
 * Created on 2019-06-20
 */
public class HiveDataSource extends DruidDataSource {
    // 慢查询
    public static final int SLOW_SQL_MILLIS = 10 * 60 * 1000;

    public HiveDataSource(String url, String userName, String password, boolean isDecrypt,
            boolean readOnly, HiveJdbcPoolConfig jdbcPoolConfig) {
        super();
        this.jdbcUrl = url;
        this.username = userName;
        this.password = password;

        this.initialSize = jdbcPoolConfig.getInitialSize();
        this.minIdle = jdbcPoolConfig.getMinIdle();
        this.maxIdle = jdbcPoolConfig.getMaxIdle();
        this.maxActive = jdbcPoolConfig.getMaxActive();
        this.maxWait = jdbcPoolConfig.getMaxWait();
        this.timeBetweenEvictionRunsMillis = jdbcPoolConfig.getTimeBetweenEvictionRunsMillis();
        this.minEvictableIdleTimeMillis = jdbcPoolConfig.getMinEvictableIdleTimeMillis();
        this.validationQuery = jdbcPoolConfig.getValidationQuery();
        this.driverClass = jdbcPoolConfig.getDriverClassName();
        this.maxOpenPreparedStatements = jdbcPoolConfig.getMaxOpenPreparedStatements();
        this.removeAbandoned = jdbcPoolConfig.isRemoveAbandoned();
        this.removeAbandonedTimeoutMillis = jdbcPoolConfig.getRemoveAbandonedTimeout();
        setDefaultReadOnly(readOnly);
        setTestWhileIdle(jdbcPoolConfig.isTestWhileIdle());
        setTestOnBorrow(jdbcPoolConfig.isTestOnBorrow());
        setTestOnReturn(jdbcPoolConfig.isTestOnReturn());
        setConnectionProperties("config.decrypt=" + Boolean.toString(isDecrypt));

        //filters
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(SLOW_SQL_MILLIS);
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        List<Filter> filters = Lists.newArrayList();
        filters.add(statFilter);
        setProxyFilters(filters);
    }
}
