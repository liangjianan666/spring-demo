package com.lja.test;

/**
 * @author LiangJianAn
 * @Description
 * @Date 2022/9/8 9:23
 */
//测试类
public class TheadTest {
    public static void main(String[] args)  {
        Thread th1 = new Thread(new ThreadDemo());
        Thread th = new Thread(new ThreadDemo());
        th.start();
        th1.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
        System.out.println("线程--------------------------------------------------");
    }
}