package com.jacobs.hive.config;

/**
 * @author lichao
 * Created on 2019-06-20
 */
public class HiveJdbcPoolConfig {
    public static final String DEFAULT_DRIVER_CLASS_NAME = "org.apache.hive.jdbc.HiveDriver";

    public static final int DEFAULT_INITIAL_SIZE = 5;

    public static final int DEFAULT_MIN_IDLE = 1;

    public static final int DEFAULT_MAX_IDLE = 5;

    public static final int DEFAULT_MAX_ACTIVE = 10;

    public static final int DEFAULT_MAX_WAIT = 10000;

    public static final int DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60000;

    public static final int DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 300000;

    public static final String DEFAULT_VALIDATION_QUERY = "SELECT 'x'";

    public static final boolean DEFAULT_TEST_WHILE_IDLE = true;

    public static final boolean DEFAULT_TEST_ON_BORROW = false;

    public static final boolean DEFAULT_TEST_ON_RETURN = false;

    public static final int DEFAULT_MAX_OPEN_PREPARED_STATEMENTS = 50;

    public static final int DEFAULT_REMOVE_ABANDONED_TIMEOUT = 180;

    public static final boolean DEFAULT_REMOVE_ABANDONED = true;

    // Driver Class Name
    private String driverClassName = DEFAULT_DRIVER_CLASS_NAME;

    // 初始数量
    private int initialSize = DEFAULT_INITIAL_SIZE;

    // 最小空闲数
    private int minIdle = DEFAULT_MIN_IDLE;

    // 最大空闲数
    private int maxIdle = DEFAULT_MAX_IDLE;

    // 最大连接数
    private int maxActive = DEFAULT_MAX_ACTIVE;

    // 最大等待时间
    private int maxWait = DEFAULT_MAX_WAIT;

    // 空闲连接回收的间隔时间
    private int timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    // 空闲连接被回收的时间
    private int minEvictableIdleTimeMillis = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    // 用于检查连接的SQL
    private String validationQuery = DEFAULT_VALIDATION_QUERY;

    // 是否开启空闲连接校验
    private boolean testWhileIdle = DEFAULT_TEST_WHILE_IDLE;

    // 借出连接是否校验
    private boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;

    // 返回连接是否校验
    private boolean testOnReturn = DEFAULT_TEST_ON_RETURN;

    private int maxOpenPreparedStatements = DEFAULT_MAX_OPEN_PREPARED_STATEMENTS;
    private boolean removeAbandoned = DEFAULT_REMOVE_ABANDONED;
    private int removeAbandonedTimeout = DEFAULT_REMOVE_ABANDONED_TIMEOUT;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }

    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }
}
