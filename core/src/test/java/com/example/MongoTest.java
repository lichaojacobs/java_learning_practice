package com.example;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

import com.alibaba.fastjson.JSON;
import com.example.module.MongoUser;
import com.example.service.DemoService;
import com.google.common.collect.Lists;
import com.mongodb.AggregationOptions;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import java.util.Arrays;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.print.Doc;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lichao on 2017/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MongoTest {

  @Resource
  MongoDatabase mongoDatabase;
  @Resource
  DemoService demoService;

  public static final String LIKE_COLLECTION = "like";

  @Test
  public void testMongo() {
    List<Document> documents = Lists.newArrayList();
    documents.add(new Document().append("userName", "liyao").append("age", 18));
    documents.add(new Document().append("userName", "wagnlu").append("age", 20));
    documents.add(new Document().append("userName", "yujingxuan").append("age", 5));
    mongoDatabase.getCollection("users").insertMany(documents);
  }

  @Test
  public void testGetMongo() {
//    System.out.println(mongoDatabase.getCollection("users").find()
//        .filter(new BasicDBObject().append("userName", "chao")).sort().first().toJson());
    //System.out.println(mongoDatabase.getCollection("users").find(new BasicDBObject("userName","wagnlu")).first());
    System.out
        .println(mongoDatabase.getCollection("users").find().sort(descending("age")).first());
  }

  @Test
  public void testLike() {
    Document document = new Document().append("from", "123456").append("to", "234567")
        .append("count", 4)
        .append("timestamp", System.currentTimeMillis())
        .append("identify", getDateSpanDaysFirstSecond(0));

    //直接findOneAndReplace就可以确保一次了（如果没有找到就插入，如果找到了就替换，完美解决复杂的逻辑）
    mongoDatabase.getCollection(LIKE_COLLECTION)
        .findOneAndReplace(and(eq("identify", 1492358400000L), eq("from", "123456")), document,
            new FindOneAndReplaceOptions().upsert(true));
    //mongoDatabase.getCollection(LIKE_COLLECTION).insertOne(document);
  }


  @Test
  public void testFans() {
    //fans实现中limit等于15
    MongoCursor<Document> mongoCursor = mongoDatabase.getCollection(LIKE_COLLECTION)
        .find(eq("from", "123456"))
        .sort(descending("timestamp")).limit(3).iterator();

    while (mongoCursor.hasNext()) {
      System.out.println(mongoCursor.next().toJson());
    }

  }

  @Test
  public void testFind() {
    System.out.println(
        mongoDatabase.getCollection(LIKE_COLLECTION)
            .find(and(eq("from", "123456"), eq("identify", getDateSpanDaysFirstSecond(0))))
            .iterator()
            .hasNext());
  }

  @Test
  public void testAggreateFind() {
    //aggregate 的pipline跟顺序有关,一般是数组从左到右依次pipline,一般pipline aggregation stages有 $match $group $sortByCount...
    List<Document> documents = mongoDatabase.getCollection("like")
        .aggregate(Lists.newArrayList(
            Document.parse(String.format("{$match:{identity:%d}}", getDateSpanDaysFirstSecond(0))),
            Document.parse(String.format("{$match:{from:{$in:%s}}}", JSON.toJSONString(Lists
                .newArrayList("296075535799459c9da864418a2cd02e",
                    "bac374ef017d1c9bb1452fde7a03d9ee")))),
            Document.parse("{$group:{_id:\"$from\",count:{$sum:1}}}"),
            Document.parse("{$sort:{count:1}}")))//1表示升序 -1表示降序
        .into(Lists.newArrayList());
    System.out.println(JSON.toJSONString(documents));
  }

  @Test
  public void testAggreateFind2() {
    List<Document> documents = Lists.newArrayList();
    documents.add(new Document("$match", new Document("identity", getDateSpanDaysFirstSecond(0))));
    documents.add(new Document("$match",
        new Document("from",
            new Document("$in", JSON.toJSONString(Lists
                .newArrayList("296075535799459c9da864418a2cd02e",
                    "bac374ef017d1c9bb1452fde7a03d9ee"))))));
    documents.add(new Document("$group", Lists.newArrayList(new Document("_id", "$from"),
        new Document("count", new Document("$sum", 1)))));

    //mongoDatabase.getCollection("like").aggregate(documents).into(Lists.);
    System.out.println(JSON.toJSONString(documents));
  }

  public long getDateSpanDaysFirstSecond(int daySpan) {
    Calendar calendar = Calendar.getInstance(Locale.getDefault());

    calendar.add(Calendar.MONTH, 0);
    calendar.add(Calendar.DAY_OF_MONTH, daySpan);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    return calendar.getTime().getTime();
  }
}
