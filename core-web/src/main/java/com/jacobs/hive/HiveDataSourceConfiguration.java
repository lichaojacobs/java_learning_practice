package com.jacobs.hive;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.jacobs.hive.config.HiveJdbcConfig;


/**
 * @author lichao
 * Created on 2019-06-20
 */
@Configuration
@ConditionalOnProperty(name = "dataarch.common.hive.jdbc.client.enable", havingValue = "true")
public class HiveDataSourceConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(HiveDataSourceConfiguration.class);
    @Autowired
    private HiveJdbcConfig hiveJdbcConfig;
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @PostConstruct
    public void init() {
        logger.info("HiveDataSourceConfiguration init jdbc client pool...");
        createDataSourceBean();
    }

    private void createDataSourceBean() {
        DataSource masterDataSource = new HiveDataSource(
                hiveJdbcConfig.getMasterUrl(),
                hiveJdbcConfig.getUserName(),
                hiveJdbcConfig.getPassword(),
                hiveJdbcConfig.isDecrypt(),
                false,
                hiveJdbcConfig.getPool()
        );

        beanFactory.registerSingleton("hiveJdbcTemplate", new HiveJdbcTemplate(masterDataSource));
        logger.info("HiveDataSourceConfiguration init jdbc client pool finished...");
    }
}
