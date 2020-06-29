package com.zjx.statistics.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 进位制处理
 *
 * @author Aaron
 * @date 2020/6/16 17:07
 */
@Slf4j
public class CarryUtil {
    /**
     * 最大值
     */
    private static final int LIMIT_UP = 9999999;
    /**
     * 高低位 的位数 限制
     */
    private static final int BIT_SINGLE = 24;

    private static final int BIT = 48;

    private static final String ZERO = "0";


    /**
     * 解析排序的分数
     *
     * @param experience 经验值
     * @param medal      奖章数
     * @return
     */
    public static double scoreParser(int experience, int medal, long score) {
        // 转化为 二进制数
        String scoreBinary = Long.toBinaryString(score);
        String highStr = null;
        String lowStr = null;
        int highInt = 0;
        int lowInt = 0;

        if (!ZERO.equals(scoreBinary)) {
            // 判断是否是 48 位 二进制数 如果不是 补位
            int len = scoreBinary.length();
            if (len < BIT) {
                String fullZero = generateZero(BIT - len);
                scoreBinary = fullZero + scoreBinary;
            }
            // 分隔高 24位 和 低 24位
            highStr = scoreBinary.substring(0, 24);
            lowStr = scoreBinary.substring(25, 48);
            // 转化为 十进制
            highInt = Integer.valueOf(highStr, 2);
            lowInt = Integer.valueOf(lowStr, 2);
        }

        // 计算高低位值
        int resHighInt = highInt + experience;
        int resLowInt = lowInt + medal;

        // 判断是否 超过 最大值
        if (resHighInt > LIMIT_UP || resLowInt > LIMIT_UP) {
            // TODO 此处需要异常处理
            log.error("出现异常，不能超过最大值");
        }

        // 转化为 二进制 并且补位
        String resHighIntBinary = binaryHandler(resHighInt);
        String resLowIntBinary = binaryHandler(resLowInt);

        // 转换为 long
        return Long.valueOf(resHighIntBinary + resLowIntBinary, 2);
    }

    private static String binaryHandler(int num) {
        String numBinary = Integer.toBinaryString(num);
        int length = numBinary.length();
        if (length < BIT_SINGLE) {
            // 将高位进行补位
            int dif = BIT_SINGLE - length;
            String zero = generateZero(dif);
            return zero + numBinary;
        }
        return numBinary;
    }

    private static String generateZero(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(ZERO);
        }
        return sb.toString();
    }
}
