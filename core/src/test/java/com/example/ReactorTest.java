package com.example;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by lichao on 2017/9/17.
 */
@SpringBootTest
public class ReactorTest {

  private static List<String> words = Arrays.asList(
      "the",
      "quick",
      "brown",
      "fox",
      "jumped",
      "over",
      "the",
      "lazy",
      "dog"
  );

  @Test
  public void simpleCreation() {
    Flux<String> fewWords = Flux.just("Hello", "World");
    Flux<String> manyWords = Flux.fromIterable(words);

    fewWords.subscribe(System.out::println);
    System.out.println();
    manyWords.subscribe(System.out::println);
  }

  @Test
  public void findingMissingLetter() {
    Flux<String> manyLetters = Flux.fromIterable(words)
        .flatMap(word -> Flux.fromArray(word.split("")))
        .distinct()
        .sort()
        .zipWith(Flux.range(1, Integer.MAX_VALUE),
            (string, count) -> String.format("%2d. %s", count, string));

    manyLetters.subscribe(System.out::println);
  }

  @Test
  public void restoringMissingLetter() {
    Mono<String> missing = Mono.just("s");
    Flux<String> allletters = Flux.fromIterable(words)
        .flatMap(word -> Flux.fromArray(word.split("")))
        .concatWith(missing)
        .distinct()
        .sort()
        .zipWith(Flux.range(1, Integer.MAX_VALUE),
            (string, count) -> String.format("%2d. %s", count, string));

    allletters.subscribe(System.out::println);
  }

  @Test
  public void shortCircuit() {
    //This snippet prints "Hello", but fails to print the delayed "world" because the test terminates too early.
    Flux<String> helloPauseWorld = Mono.just("Hello").concatWith(Mono.just("world"))
        .delaySubscriptionMillis(500);

    helloPauseWorld.subscribe(System.out::println);
  }

  @Test
  public void blocks() {
    //you could solve that issue is by using one of the operators that revert back to the non-reactive world. Specifically, toIterable and toStream will both produce a blocking instance.
    //As you would expect, this prints "Hello" followed by a short pause, then prints "world" and terminates.
    Flux<String> helloPauseWorld =
        Mono.just("Hello")
            .concatWith(Mono.just("world")
                .delaySubscriptionMillis(500));

    helloPauseWorld.toStream()
        .forEach(System.out::println);
  }

  @Test
  public void firstEmitting() {
    Mono<String> a = Mono.just("oops I'm late")
        .delaySubscriptionMillis(450);
    Flux<String> b = Flux.just("let's get", "the party", "started")
        .delayMillis(400);

    Flux.firstEmitting(a, b)
        .toIterable()
        .forEach(System.out::println);
  }
}
