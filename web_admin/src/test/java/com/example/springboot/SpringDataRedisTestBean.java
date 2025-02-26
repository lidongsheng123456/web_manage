package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Spring Data Redis 测试类
 */
@SpringBootTest
public class SpringDataRedisTestBean {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试 RedisTemplate 的基本操作
     */
    /**
     * 1.字符串（String）
     * 数据结构：字符串是最简单的数据类型，可以存储字符串、数字等。
     * <p>
     * 2.哈希（Hash）
     * 数据结构：哈希是一个键值对的集合，类似于 Java 中的 Map。
     * <p>
     * 3.列表（List）
     * 数据结构：列表是一个有序的字符串集合，可以进行从头部或尾部插入和删除操作。
     * <p>
     * 4.集合（Set）
     * 数据结构：集合是一个无序且不重复的字符串集合。
     * <p>
     * 5.有序集合（Sorted Set）
     * 数据结构：有序集合是一个有序的字符串集合，每个成员都有一个分数（score），成员按照分数排序。
     */
    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate); // 打印 RedisTemplate 对象
        ValueOperations valueOperations = redisTemplate.opsForValue(); // 获取操作字符串类型数据的对象
        HashOperations hashOperations = redisTemplate.opsForHash(); // 获取操作哈希类型数据的对象
        ListOperations listOperations = redisTemplate.opsForList(); // 获取操作列表类型数据的对象
        SetOperations setOperations = redisTemplate.opsForSet(); // 获取操作集合类型数据的对象
        ZSetOperations zSetOperations = redisTemplate.opsForZSet(); // 获取操作有序集合类型数据的对象
    }

    /**
     * 操作字符串类型的数据
     */
    @Test
    public void testString() {
        // 设置键 "name" 的值为 "小明"
        redisTemplate.opsForValue().set("name", "小明");
        // 获取键 "name" 的值并打印
        String city = (String) redisTemplate.opsForValue().get("name");
        System.out.println(city);
        // 设置键 "code" 的值为 "1234"，并设置过期时间为 3 分钟
        redisTemplate.opsForValue().set("code", "1234", 3, TimeUnit.MINUTES);
        // 如果键 "lock" 不存在，则设置其值为 "1"
        redisTemplate.opsForValue().setIfAbsent("lock", "1");
        // 尝试设置键 "lock" 的值为 "2"，但由于键已存在，不会生效
        redisTemplate.opsForValue().setIfAbsent("lock", "2");
    }

    /**
     * 操作哈希类型的数据
     */
    @Test
    public void testHash() {
        HashOperations hashOperations = redisTemplate.opsForHash(); // 获取操作哈希类型数据的对象

        // 在哈希 "100" 中设置键 "name" 的值为 "tom"
        hashOperations.put("100", "name", "tom");
        // 在哈希 "100" 中设置键 "age" 的值为 "20"
        hashOperations.put("100", "age", "20");

        // 获取哈希 "100" 中键 "name" 的值并打印
        String name = (String) hashOperations.get("100", "name");
        System.out.println(name);

        // 获取哈希 "100" 的所有键并打印
        Set keys = hashOperations.keys("100");
        System.out.println(keys);

        // 获取哈希 "100" 的所有值并打印
        List values = hashOperations.values("100");
        System.out.println(values);

        // 删除哈希 "100" 中的键 "age"
        hashOperations.delete("100", "age");
    }

    /**
     * 操作列表类型的数据
     */
    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList(); // 获取操作列表类型数据的对象

        // 向列表 "mylist" 左侧批量插入元素 "a", "b", "c"
        listOperations.leftPushAll("mylist", "a", "b", "c");
        // 向列表 "mylist" 左侧插入元素 "d"
        listOperations.leftPush("mylist", "d");

        // 获取列表 "mylist" 的所有元素并打印
        List mylist = listOperations.range("mylist", 0, -1);
        System.out.println(mylist);

        // 从列表 "mylist" 右侧弹出一个元素
        listOperations.rightPop("mylist");

        // 获取列表 "mylist" 的长度并打印
        Long size = listOperations.size("mylist");
        System.out.println(size);
    }

    /**
     * 操作集合类型的数据
     */
    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet(); // 获取操作集合类型数据的对象

        // 向集合 "set1" 添加元素 "a", "b", "c", "d"
        setOperations.add("set1", "a", "b", "c", "d");
        // 向集合 "set2" 添加元素 "a", "b", "x", "y"
        setOperations.add("set2", "a", "b", "x", "y");

        // 获取集合 "set1" 的所有成员并打印
        Set members = setOperations.members("set1");
        System.out.println(members);

        // 获取集合 "set1" 的大小并打印
        Long size = setOperations.size("set1");
        System.out.println(size);

        // 获取集合 "set1" 和 "set2" 的交集并打印
        Set intersect = setOperations.intersect("set1", "set2");
        System.out.println(intersect);

        // 获取集合 "set1" 和 "set2" 的并集并打印
        Set union = setOperations.union("set1", "set2");
        System.out.println(union);

        // 从集合 "set1" 中删除元素 "a", "b"
        setOperations.remove("set1", "a", "b");
    }

    /**
     * 操作有序集合类型的数据
     */
    @Test
    public void testZset() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet(); // 获取操作有序集合类型数据的对象

        // 向有序集合 "zset1" 添加元素 "a"，分数为 10
        zSetOperations.add("zset1", "a", 10);
        // 向有序集合 "zset1" 添加元素 "b"，分数为 12
        zSetOperations.add("zset1", "b", 12);
        // 向有序集合 "zset1" 添加元素 "c"，分数为 9
        zSetOperations.add("zset1", "c", 9);

        // 获取有序集合 "zset1" 的所有元素并打印
        Set zset1 = zSetOperations.range("zset1", 0, -1);
        System.out.println(zset1);

        // 增加有序集合 "zset1" 中元素 "c" 的分数 10
        zSetOperations.incrementScore("zset1", "c", 10);

        // 从有序集合 "zset1" 中删除元素 "a", "b"
        zSetOperations.remove("zset1", "a", "b");
    }

    /**
     * 通用命令操作
     */
    @Test
    public void testCommon() {
        // 获取所有键并打印
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);

        // 检查键 "name" 是否存在
        Boolean name = redisTemplate.hasKey("name");
        // 检查键 "set1" 是否存在
        Boolean set1 = redisTemplate.hasKey("set1");

        // 遍历所有键，获取每个键的数据类型并打印
        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
        }

        // 删除键 "mylist"
        redisTemplate.delete("mylist");
    }
}
