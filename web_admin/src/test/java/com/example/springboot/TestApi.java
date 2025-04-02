package com.example.springboot;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@SpringBootTest
public class TestApi {

    @Test
    public void test() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.stream()
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.startsWith("A");
                    }
                })
                .forEach(System.out::println);

        names.stream().filter(s -> false);

    }
}
