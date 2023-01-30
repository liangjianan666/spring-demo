package com.lja.infrastructure.callback;

/**
 * @author LiangJianAn
 * @Description 奔驰 报名比赛
 * @Date 2023/1/30 14:24
 */
public class BenzCar {
    public static void main(String[] args) {
        CarCallBack carCallBack = () -> {
            System.out.println("Benz 奔驰！！！");
            System.out.println("Benz提交资料，报名");
            System.out.println("Benz我开始组装自己的报名车辆，安排车辆换胎人员");
            System.out.println("Benz开启三百时速，冲！！！");
        };
        CarRace.race(carCallBack);
    }
}
