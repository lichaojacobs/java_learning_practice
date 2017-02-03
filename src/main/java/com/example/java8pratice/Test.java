package com.example.java8pratice;

import com.jacobs.exercise.Children;
import com.jacobs.exercise.Person;
import com.jacobs.exercise.java8.lazylist.LazyList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lichao on 16/8/4.
 */
public class Test {
  public static void main(String[] args) {
    //    List<String> des = new ArrayList<>();
    //    List<String> src = new ArrayList<>();
    //    src.add("demo");
    //    src.add("test");
    //    //copyList(des, src);
    //    src.forEach(System.out::println);
    //    src.stream().sorted(Comparator.comparing(s -> s));
    //    predicate.filter(src, str -> str != null && str != "").forEach(System.out::println);
    //    Children children = new Children();
    //    children.setStudent(true);
    //    children.setId(1);
    //    List<Children> childrens = new ArrayList<>();
    //    childrens.add(children);
    //    Children children1 = new Children();
    //    children1.setStudent(true);
    //    children.setId(2);
    //    childrens.add(children1);
    //    childrens.add(null);
    //    childrens = testFilter(childrens, null);
    //    if (childrens == null) {
    //      System.out.println("返回结果为null");
    //    }


    //    Children testNonable = null;
    //    boolean flag = Optional.ofNullable(testNonable).filter(Objects::nonNull).map(m -> {
    //      System.out.println("忽略了第一个filter");
    //      return true;
    //    }).orElse(false);
    //
    //    System.out.println(flag);
    //    String result = new TestModel<String>().getValue(number -> "结果" + String.valueOf(number));
    //    System.out.println(result);
    List<Person> persons = null;
    Optional.ofNullable(persons)
        .ifPresent(persons1 -> persons1.stream().forEach(person -> System.out.println("hello")));

    //testOptional().ifPresent(child->System.out.println("存在"));
    //System.out.println(Objects.nonNull(testOptional()));
  }

  static Function<String, Integer> changes = (String str) -> {
    return Integer.valueOf(str);
  };

  public static Optional<Children> testOptional() {
    return Optional.empty();
  }

  public static List<Children> testFilter(List<Children> childrens, Integer fromUserId) {
    return childrens.stream().filter(children -> Objects.nonNull(fromUserId)).map(child -> {
      child.setStudent(false);
      return child;
    }).collect(Collectors.toList());
  }

  public static LazyList<Integer> from(int n) {
    return new LazyList<>(n, () -> from(n + 1));
  }

  public static boolean isNotEmpty(String string) {
    return string != null && string != "";
  }

  /**
   * 第一个T用来定义类型的
   */
  public static <T> T copyList(List<? super T> des, List<T> src) {
    des.addAll(src);
    src.forEach(System.out::println);
    return src.get(0);
  }

  public static void getName(FcInterfaceTest<String> fc, int number) {
    System.out.println(fc.parseString(number));
  }
}
