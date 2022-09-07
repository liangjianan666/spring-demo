package com.lja.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author LiangJianAn
 * @Description
 * @Date 2022/8/26 15:43
 */
public interface HelloService {
    @Transactional
    Object hello(Integer id);
}
