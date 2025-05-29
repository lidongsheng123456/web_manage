package com.example.springboot;


import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestApi {

    @Test
    public void test() {
        // 缓存中的文化项目
        Map<String,String> map = new HashMap<>();
        map.put("1","文化1");
        map.put("2","文化2");
        map.put("3","文化3");

        // 数据库中的分页范围中的文化项目id
        List<String> strings = List.of("1", "2", "3", "4");

        // 缓存中没有命中的文化项目
        ArrayList<String> noHitIdList = new ArrayList<>(strings.size());

        for (String string : strings) {
            if (!map.containsKey(string)) {
                noHitIdList.add(string);
            }
        }

        System.out.println(noHitIdList);
    }
}
