package com.example;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

import com.alibaba.fastjson.JSON;
import com.example.module.MongoUser;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
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
