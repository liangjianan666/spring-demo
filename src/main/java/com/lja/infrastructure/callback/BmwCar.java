package com.lja.infrastructure.callback;

/**
 * @author LiangJianAn
 * @Description 宝马 报名比赛
 * @Date 2023/1/30 14:24
 */
public class BmwCar {
    public static void main(String[] args) {
        CarCallBack carCallBack = () -> {
            System.out.println("Bmw 宝马！！！");
            System.out.println("Bmw提交资料，报名");
            System.out.println("Bmw我开始组装自己的报名车辆，安排车辆换胎人员");
            System.out.println("Bmw开启三百五时速，冲！！！");
        };
        CarRace.race(carCallBack);
    }
}
