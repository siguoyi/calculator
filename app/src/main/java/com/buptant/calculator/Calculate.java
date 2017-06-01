package com.buptant.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenlei on 2017/5/31.
 */

public class Calculate {

    public static String getResult(String text) {
        // 规则，只能出现数字和加减乘除符号，最前和最后都是数字，即字符串能有效计算的
//        String text = "30x1+4x2x10-10+40÷20";
//        String text = "2x1.5+3x4-10÷5";

        // 计算内容分割
        List<String> numList = new ArrayList<String>();
        int splitIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '+' || c == '-' || c == 'x' || c == '÷') {
                numList.add(text.substring(splitIndex, i));
                numList.add(c + "");
                splitIndex = i + 1;
            }
        }
        // 因为使用符号做判断，增加前一位和符号，所以最后一位数字不会在循环里处理
        numList.add(text.substring(splitIndex, text.length()));

        // 先做乘除计算
        List<String> list = new ArrayList<String>();
        BigDecimal temp = null; // 用于做乘除计算临时变量
        for (int i = 1; i < numList.size(); i += 2) { // 这里只循环运算符号
            if ("+".equals(numList.get(i)) || "-".equals(numList.get(i))) {
                if (null != temp) { // 存在临时变量，说明前面进行过乘除计算
                    list.add(temp.toString());
                    temp = null;
                } else {
                    list.add(numList.get(i - 1));
                }
                list.add(numList.get(i)); // 把符号加进去
                if (i == numList.size() - 2) { // 处理到最后时遇到直接处理

                    list.add(numList.get(i + 1));


                }
            } else if ("x".equals(numList.get(i))) {
                if (null == temp) {
                    temp = new BigDecimal(numList.get(i - 1)).multiply(new BigDecimal(numList.get(i + 1)));
                } else {
                    temp = temp.multiply(new BigDecimal(numList.get(i + 1)));
                }
                if (i == numList.size() - 2) { // 处理到最后时遇到直接处理
                    list.add(temp.toString());
                    temp = null;
                }
            } else if ("÷".equals(numList.get(i))) {
                if (null == temp) {
                    temp = new BigDecimal(numList.get(i - 1)).divide(new BigDecimal(numList.get(i + 1)),3,BigDecimal.ROUND_UP);
                } else {
                    temp = temp.divide(new BigDecimal(numList.get(i + 1)),3,BigDecimal.ROUND_UP);
                }
                if (i == numList.size() - 2) { // 处理到最后时遇到直接处理
                    list.add(temp.toString());
                    temp = null;
                }
            }
        }

        // 再做加减计算
        BigDecimal sum = new BigDecimal(list.get(0)); // 第一位不会在循环里处理
        for (int i = 1; i < list.size(); i += 2) { // 这里只循环运算符号
            if ("+".equals(list.get(i))) {
                sum = sum.add(new BigDecimal(list.get(i + 1)));
            } else if ("-".equals(list.get(i))) {
                sum = sum.subtract(new BigDecimal(list.get(i + 1)));
            }
        }

        String result = sum.toString();
        return result;
    }
}
