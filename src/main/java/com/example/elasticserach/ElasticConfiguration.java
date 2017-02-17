package com.example.elasticserach;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;

/**
 * Created by lichao on 2017/2/17.
 */
@EnableConfigurationProperties
@Configuration
public class ElasticConfiguration {

  @Resource
  private ElasticsearchProperties config;

  @Bean
  public TransportClient transportClient() {
    Settings settings = Settings.builder()
        .put("cluster.name", this.config.getClusterName())
        .put("client.transport.sniff", this.config.isSniff())
        .put("client.transport.ignore_cluster_name", this.config.isIgnoreClusterName())
        .put("client.transport.ping_timeout", this.config.getPingTimeout())
        .put("client.transport.nodes_sampler_interval", this.config.getNodesSamplerInterval())
        .build();
    PreBuiltTransportClient client = new PreBuiltTransportClient(settings, new Class[0]);
    this.config.getNodes()
        .stream()
        .map((node) -> {
          String[] temp = node.split(":");
          String host = temp[0];
          int port = Integer.parseInt(temp[1]);

          try {
            return new InetSocketTransportAddress(InetAddress.getByName(host), port);
          } catch (UnknownHostException var5) {
            throw new RuntimeException(var5);
          }
        })
        .forEach(client::addTransportAddress);
    return client;
  }
}
