package com.example.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lichao on 2017/4/17.
 */
@Configuration
@ConfigurationProperties(prefix = "mongo.settings")
public class MongoDBConfiguration {

  @Getter
  @Setter
  private String mongoURI;

  @Bean
  public MongoDatabase mongoDatabase() {
    MongoClientURI connectionString = new MongoClientURI(
        "mongodb://zjy:zjy@ec2-54-159-208-93.compute-1.amazonaws.com:27017");
    MongoClient mongoClient = new MongoClient(connectionString);

    return mongoClient.getDatabase("test");
  }
}
