package com.jacobs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jacobs.module.Address;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 * Created by lichao on 2017/5/5.
 */
public class MockitoTest {

  @Test
  public void testGson() {
    String jsonString = "[{\"id\":1,\"province\":\"Beijing\",\"city\":\"Beijing\"}]";
    Type type = new TypeToken<List<Address>>() {
    }.getType();
    List<Address> beanOnes = new Gson().fromJson(jsonString, type);
    System.out.println(beanOnes);
  }

  @Test
  public void ListTest() {
    List mockedList = mock(List.class);

    // using mock object - it does not throw any "unexpected interaction" exception
    mockedList.add("one");
    mockedList.clear();

    // selective, explicit, highly readable verification
    verify(mockedList).add("one");
    verify(mockedList).clear();
  }

  @Test
  public void stubMethod() {
    // you can mock concrete classes, not only interfaces
    LinkedList mockedList = mock(LinkedList.class);

// stubbing appears before the actual execution
    when(mockedList.get(0)).thenReturn("first");

// the following prints "first"
    System.out.println(mockedList.get(0));
    verify(mockedList).get(0);

// the following prints "null" because get(999) was not stubbed
    System.out.println(mockedList.get(999));
  }
}
