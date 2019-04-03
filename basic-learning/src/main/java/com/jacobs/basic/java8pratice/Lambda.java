package com.jacobs.basic.java8pratice;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lichao on 16/7/10.
 */
public class Lambda {

  public static void main(String[] args) {
    //testCollectors();
    System.out.println(anInterface.parseString(555));
  }

  static FcInterfaceTest<String> anInterface = (int number) -> String.valueOf(number);

  public static void testCollectors() {
    List<Person> personList = Lists.newArrayList(Person.builder()
        .age(22)
        .firstName("chao")
        .lastName("li")
        .salary(12000)
        .build(), Person.builder()
        .firstName("chao")
        .lastName("wang")
        .age(23)
        .salary(11111)
        .build(), Person.builder()
        .salary(15000)
        .age(22)
        .firstName("lu")
        .lastName("wang")
        .build());

    Map<Integer, List<Person>> mapGroupbyAge = personList.stream()
        .collect(Collectors.groupingBy(Person::getAge));
    System.out.println(JSON.toJSONString(mapGroupbyAge));

    System.out.println(personList.stream()
        .collect(Collectors.summingInt(Person::getAge)));

    //join
    System.out.println(personList.stream()
        .map(Person::getFirstName)
        .collect(Collectors.joining(", ")));

    //所有特殊情况的一般化
    personList.stream()
        .collect(Collectors.reducing(0, Person::getAge, (i, j) -> i + j));

    System.out.println(personList.stream()
        .map(Person::getSalary)
        .reduce(Integer::sum)
        .get());
  }

  public static void testMapReduce() {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 6, 5, 7, 8, 9, 10);
    IntSummaryStatistics stats = numbers
        .stream()
        .mapToInt((x) -> x)
        .summaryStatistics();
    System.out.println("List中最大的数字 : " + stats.getMax());
    System.out.println("List中最小的数字 : " + stats.getMin());
    System.out.println("所有数字的总和   : " + stats.getSum());
    System.out.println("所有数字的平均值 : " + stats.getAverage());

    numbers.stream()
        .sorted()
        .map(integer -> "结果" + integer.toString())
        .collect(Collectors.toList())
        .forEach(System.out::println);
    numbers.stream()
        .reduce((sum, item) -> sum = sum + item)
        .ifPresent((i) -> System.out.println(i));
    numbers.stream()
        .sorted()
        .forEach(integer -> System.out.println(integer));
    testFlatMap();
    tesReduce();

    Properties props = new Properties();
    props.put("a", 5);
    props.put("b", "true");
    props.put("c", "-3");
  }

  public static int readDuration(Properties properties, String name) {
    return Optional.ofNullable(properties.getProperty(name))
        .flatMap(Lambda::stringToInt)
        .filter(integer -> integer > 0)
        .orElse(0);
  }

  public static Optional<Integer> stringToInt(String s) {
    try {
      return Optional.of(Integer.valueOf(s));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public static CompletableFuture<String> testFuture() {
    CompletableFuture<String> future = new CompletableFuture<>();
    return future.supplyAsync(() -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "hello";
    });
  }

  public static void tesReduce() {
    List<Integer> numbers = new ArrayList<>(); //Arrays.asList(1, 2, 3, 4, 6, 5, 7, 8, 9, 10);
    numbers.stream()
        .collect(() -> new ArrayList<Integer>(), (list, item) -> list.add(item),
            (list1, list2) -> list1.addAll(list2));
  }

  public static void testFiles() {
    try (Stream<String> lines = Files.lines(Paths.get(""), Charset.defaultCharset())) {
      long uniqueWrods = lines.flatMap(line -> Arrays.stream(line.split("")))
          .distinct()
          .count();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void testFlatMap() {
    List<Integer> number1 = Arrays.asList(1, 2, 3);
    List<Integer> number2 = Arrays.asList(3, 4);
    List<int[]> list = number1.stream()
        .flatMap(i -> number2.stream()
            .map(j -> new int[]{i, j}))
        .collect(Collectors.toList());
    list.forEach(ints -> {
      for (int i = 0; i < ints.length; i++) {
        System.out.print(ints[i] + ",");
      }
      System.out.println();
    });
  }

  public static void test() {
    //另一个例子是Optional值不满足filter指定的条件。
    Optional<String> anotherName = Optional.of("Sanabbb");
    Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
    //输出：name长度不足6字符
    //System.out.println(shortName.orElse("The name is less than 6 characters"));

    Optional<String> name = Optional.of("Sanabbb");
    //name.ifPresent((va)->{System.out.println("The name's length is"+va.length());});

    String[] atp = {
        "Rafael Nadal",
        "Novak Djokovic",
        "Stanislas Wawrinka",
        "David Ferrer",
        "Roger Federer",
        "Andy Murray",
        "Tomas Berdych",
        "Juan Martin Del Potro"
    };
    List<String> players = Arrays.asList(atp);
    //遍历
    //players.forEach((player)->{System.out.println("the player is"+player);});
    //players.forEach(System.out::println);
    List<Person> javaProgrammers = new ArrayList<Person>() {
      {
        add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
        add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
        add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
        add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
        add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
        add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
        add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
        add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
        add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
        add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
      }
    };

    List<Person> phpProgrammers = new ArrayList<Person>() {
      {
        add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
        add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
        add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
        add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
        add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
        add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
        add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
        add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
        add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
        add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
      }
    };

    javaProgrammers.sort((a1, a2) -> a1.getFirstName()
        .compareTo(a2.getFirstName()));

    javaProgrammers.stream()
        .collect(Collectors.groupingBy(Person::getSalary))
        .forEach((integer, persons) -> {
          System.out.println(integer + ":" + persons.toString());
        });
  }
}
