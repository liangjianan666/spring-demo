package com.lja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author LiangJianAn
 * @Description    Redis测试类
 * @Date 2022/8/10 14:17
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test1() {
        redisTemplate.opsForValue().set("name1", "lja", 3000, TimeUnit.SECONDS);
        String name1 = redisTemplate.opsForValue().get("name1");
        System.out.println(name1);
    }
}
