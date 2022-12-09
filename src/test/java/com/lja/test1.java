package com.lja;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiangJianAn
 * @Description 匹配最后一个符号位置
 * @Date 2022/12/1 16:01
 */
public class test1 {

    private static String REGEX = "[,?;:，。；：~！!.]";
//    private static String REGEX = "[abcf]";
    private static String PARAM_STRING = ".abcf123afdasfa324.32.4fd,a.!ff.jljfas3#l!affafdjjdjl;j.3ueiouop";

    public static void main(String[] args) {
        Pattern compile = Pattern.compile(REGEX);
        Matcher matcher = compile.matcher(PARAM_STRING);
        boolean b = matcher.find();
        int start = 0;
        while (b) {
            System.out.println("true");
            //匹配到子字符串的开始位置
            start = matcher.start();
//            String group = matcher.group();
//            System.out.println("group: " + group);
            b = matcher.find();
        }
        System.out.println("false");
        System.out.println("参数字符串的长度: " + PARAM_STRING.length());
        System.out.println("最后子字符串的索引位置: " + start);
    }
}
