package com.lja;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author liangjianan
 * @Description
 * @Date 2021/10/12 9:53
 */
@SpringBootApplication
@EnableAspectJAutoProxy //启用aop配置
@MapperScan("com.lja.mapper")
public class StartApplication {
    public static void main(final String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}