package com.lja.infrastructure.callback;

/**
 * @author LiangJianAn
 * @Description 赛车比赛
 * @Date 2023/1/30 11:20
 */
public class CarRace {

    public static void race(CarCallBack carCallBack) {
        System.out.println("发布比赛公告！！！");
        //各车辆开始自己准备、比赛
        carCallBack.drive();
        //记录成绩排名、颁奖
        System.out.println("记录成绩排名、颁奖");
    }
}
