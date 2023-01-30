package com.lja.service;

import com.lja.infrastructure.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiangJianAn
 * @Description
 * @Date 2023/1/11 16:28
 */
@Component
//@Scope( "prototype") //多例 循环依赖， 启动会死循环
public class A {
    @Autowired
    B b;

}
