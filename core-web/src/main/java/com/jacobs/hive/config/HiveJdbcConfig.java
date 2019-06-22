package com.jacobs.hive.config;

import java.util.function.Supplier;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lichao
 * Created on 2019-06-20
 */
@ConditionalOnProperty(name = "dataarch.common.hive.jdbc.client.enable", havingValue = "true")
@Component
@ConfigurationProperties(prefix = "dataarch.common.hive.jdbc.client")
public class HiveJdbcConfig {

    public static final Supplier<HiveJdbcPoolConfig> DEFAULT_POOL = HiveJdbcPoolConfig::new;

    // 用户名
    private String userName = "";

    // 密码
    private String password = "";

    // 是否加密
    private boolean decrypt = false;

    // 主库地址
    private String masterUrl;

    // 连接池
    private HiveJdbcPoolConfig pool = DEFAULT_POOL.get();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDecrypt() {
        return decrypt;
    }

    public void setDecrypt(boolean decrypt) {
        this.decrypt = decrypt;
    }

    public String getMasterUrl() {
        return masterUrl;
    }

    public void setMasterUrl(String masterUrl) {
        this.masterUrl = masterUrl;
    }

    public HiveJdbcPoolConfig getPool() {
        return pool;
    }

    public void setPool(HiveJdbcPoolConfig pool) {
        this.pool = pool;
    }
}
