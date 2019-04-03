package com.jacobs.hbase;

import com.jacobs.hbase.factory.MainHTableFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 2017/3/10.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ZookeeperConfig.class, AdminConfig.class})
public class MainHbaseConfiguration {

  @Bean
  @ConditionalOnBean(name = "zookeeperConfig")
  public org.apache.hadoop.conf.Configuration configuration(ZookeeperConfig zookeeperConfig) {
    org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
    if (StringUtils.isNotBlank(zookeeperConfig.getQuorum())) {
      log.info(
          "hbase zookeeper [quorum] from application.yaml value: [" + zookeeperConfig.getQuorum() +
              "]");
      config.set("hbase.zookeeper.quorum", zookeeperConfig.getQuorum()
          .trim());
    }
    if (zookeeperConfig.getPort() != null) {
      log.info("hbase zookeeper [port] from application.yaml value: [" + zookeeperConfig.getPort() +
          "]");
      config.set("hbase.zookeeper.property.clientPort", zookeeperConfig.getPort()
          .toString());
    }
    log.info("hbase pool size is [" + zookeeperConfig.getMaxSize() + "]");
    config.set("hbase.htable.threads.max", zookeeperConfig.getMaxSize()
        .toString());
    log.info("Hbase config init ok.");
    return config;
  }

  @Bean
  public AdminConfig adminConfig(AdminConfig adminConfig) {
    return adminConfig;
  }

  @Bean
  @ConditionalOnBean(name = "configuration")
  public MainHTableFactory mainHTableFactory(org.apache.hadoop.conf.Configuration configuration) {
    MainHTableFactory mainHTableFactory = new MainHTableFactory(configuration);
    log.info("HTable factory init ok");
    return mainHTableFactory;
  }

  @Bean(name = "hbaseTemplate")
  @ConditionalOnBean(name = {"configuration","mainHTableFactory"})
  public HBaseTemplate hBaseTemplate(org.apache.hadoop.conf.Configuration configuration,
      MainHTableFactory mainHTableFactory) {
    HBaseTemplate hBaseTemplate = new HBaseTemplate(configuration);
    hBaseTemplate.setTableFactory(mainHTableFactory);
    log.info("HBase template init ok");
    return hBaseTemplate;
  }
}
