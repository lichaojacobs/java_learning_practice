package com.jacobs;

import static org.junit.Assert.assertEquals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lichao
 * @date 2018/1/11
 */
@SpringBootTest
public class ModelMapperTest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {

        Customer customer;
        Address billingAddress;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {

        Name name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Name {

        String firstName;
        String lastName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {

        String street;
        String city;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDTO {

        String customerFirstName;
        String customerLastName;
        String billingAddressStreet;
        String billingAddressCity;
    }

    @Test
    public void testMapper() {
        Order order = Order.builder().customer(Customer.builder().name(Name.builder().firstName("chao").lastName("li").build()).build())
                .billingAddress(Address.builder().street("海淀").city("北京").build()).build();

        ModelMapper modelMapper = new ModelMapper();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
    }
}
