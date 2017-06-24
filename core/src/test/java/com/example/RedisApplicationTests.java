package com.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.aspects.LogMethod;
import com.example.aspects.LogService;
import com.example.constants.BaseConstant;
import com.example.module.User;
import com.example.resource.DemoResource;
import com.example.task.Task;
import com.google.common.collect.Lists;
import com.mobvoi.data.cache.commands.StringCommand;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@PropertySource(value = "classpath:configs.properties")
@Slf4j
public class RedisApplicationTests {

  @Resource
  DemoResource demoResource;
  @Resource
  LogService logService;
  @Resource
  LogMethod logMethod;
  @Resource
  Task task;

  @Resource
  KafkaTemplate<String, String> dataKafkaProducer;

  @Test
  public void testConfig() {
    logService.logTest();
  }

  //  @Test
  //  public void contextLoads() {
  //    elasticSearchService.getTestReuslt();
  //  }

  @Test
  public void testRedis() {
    demoResource.putCache();
    try {
      java.lang.Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    demoResource.testCache();
  }

  //  @Test
  //  public void testHBase() {
  //    String tableName = "test";
  //    String columnFamilyName = "i";
  //
  //    String userName = "user_name";
  //    String age = "age";
  //
  //
  //    hBaseTemplate.execute(tableName, table -> {
  //      PutExtension putExtension = new PutExtension(columnFamilyName, "row2".getBytes()).build(
  //          userName, "wanglu")
  //          .build(age, 22);
  //      table.put(putExtension);
  //      return true;
  //    });
  //
  //  }

  //  @Test
  //  public void testGetHBase() {
  //    String tableName = "test";
  //    String columnFamilyName = "i";
  //
  //    List<User> users = hBaseTemplate.find(tableName, columnFamilyName, "i", (result, i) -> {
  //      return result.listCells()
  //          .stream()
  //          .findFirst()
  //          .map(cell -> Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
  //              cell.getValueLength()))
  //          .map(jsonStr -> JSON.parseObject(jsonStr, User.class))
  //          .orElse(null);
  //    });
  //
  //    System.out.println(JSON.toJSONString(users));
  //  }

  @Test
  public void aopTest() {
    logMethod.sayHello();
  }


  @Test
  public void FastJsonTes() {
    HashSet<Long> set = new HashSet<>();
    HashSet<Long> values = JSON.parseObject("[100000,2000000]", new TypeReference<HashSet<Long>>() {
    });
    System.out.println(values);
  }

  @Test
  public void taskTest() throws Exception {
    Future<String> task1 = task.doTaskOne();
    Future<String> task2 = task.doTaskTwo();
    Future<String> task3 = task.doTaskThree();
    while (true) {
      if (task1.isDone() && task2.isDone() && task3.isDone()) {
        System.out.println("所有任务完成");
        break;
      }
    }
  }

  @Test
  public void testMap() {
    List<User> users = new ArrayList<>();
    User user1 = new User();
    user1.setFirstName("li");
    user1.setLastName("chao");
    user1.setId(1l);
    users.add(user1);
    User user2 = new User();
    user2.setFirstName("wang");
    user2.setLastName("lu");
    user2.setId(2l);
    users.add(user2);

    users.forEach(
        user -> System.out.println("userName: " + user.getFirstName() + user.getLastName()));

    List<User> users1 = users.stream()
        .map(user -> {
          try {
            User user3 = user.clone();
            user3.setLastName("测试");
            return user3;
          } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
          }
        })
        .collect(Collectors.toList());

    users1.forEach(
        user -> System.out.println("userName: " + user.getFirstName() + user.getLastName()));

    users.forEach(
        user -> System.out.println("userName: " + user.getFirstName() + user.getLastName()));
  }

  @Test
  public void testSortedMap() {
    TreeMap<Integer, String> sortedMap = new TreeMap<>();
    sortedMap.put(3, "3");
    sortedMap.put(4, "4");
    sortedMap.put(1, "1");
    sortedMap.forEach((index, str) -> {
      System.out.println(index + " " + str);
    });
  }

  @Test
  public void testJson() {
    List<User> users = new ArrayList<>();
    User user1 = new User();
    user1.setFirstName("li");
    user1.setLastName("chao");
    user1.setId(1l);
    users.add(user1);
    User user2 = new User();
    user2.setFirstName("wang");
    user2.setLastName("lu");
    user2.setId(2l);

    User temp = users.get(0);
    temp.setLastName("jacobs");
    users.remove(temp);

    users.add(null);
    List<User> userList = users.stream()
        .map(user -> Optional.ofNullable(user))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
    System.out.println(JSON.toJSONString(userList));
  }

  @Test
  public void testJedis() {
    List<Integer> list = Lists.newArrayList();
    for (int i = 0; i < 10000; i++) {
      list.add(i);
    }

    list.forEach(integer -> {
      StringCommand.getJedis(BaseConstant.REDIS_NAME)
          .setWithExpire("test" + integer, 600, integer.toString());
    });
  }

  @Test
  public void testKafka() {
    dataKafkaProducer.send("test", "helloworld");
  }

}
