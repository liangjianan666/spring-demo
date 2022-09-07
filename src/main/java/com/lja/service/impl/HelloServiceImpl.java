package com.lja.service.impl;

import com.lja.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.TimeUnit;

/**
 * @author LiangJianAn
 * @Description 测试spring事务, 核心逻辑 TransactionSynchronizationManager 管理类里面事务相关属性
 * @Date 2022/8/26 15:42
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public Object hello(Integer id) {
        // 向数据库插入一条记录
//        String sql = "insert into user (id,name,age) values (" + id + ",'fsx',21)";
        String sql = "insert into student (id,name,code) values (" + id + ",'lja',id)";
        jdbcTemplate.update(sql);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            // 在事务提交之后执行的代码块（方法）  此处使用TransactionSynchronizationAdapter，其实在Spring5后直接使用接口也很方便了~
            @Override
            public void afterCommit() {
                new Thread(() -> {
                    String query = "select count(1) from student where id = " + id;
                    Integer count = jdbcTemplate.queryForObject(query, Integer.class);
                    System.out.println("count: " + count);
                }).start();
            }
        });

//        new Thread(() -> {
//            String query = "select count(1) from student where id = " + id;
//            Integer count = jdbcTemplate.queryForObject(query, Integer.class);
//            System.out.println("count: " + count);
//        }).start();


        // 把问题放大
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(1 / 0);
        return "service hello";
    }

}
