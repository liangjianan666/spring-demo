package com.lja.test;

/**
 * @author LiangJianAn
 * @Description
 * @Date 2022/9/8 9:21
 */
public class ThreadDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
    }
}
