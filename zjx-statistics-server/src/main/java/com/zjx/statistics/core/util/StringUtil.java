package com.zjx.statistics.core.util;

/**
 * 字符串处理工具
 *
 * @author Aaron
 * @date 2020/6/18 17:22
 */
public class StringUtil {

    public static final char UNDERLINE = '_';

    /**
     * 下划线转为驼峰格式
     *
     * @param str 字符串
     * @return
     */
    public static String underlineToCamel(String str) {
        if (str == null || "".equals(str.trim())) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        // "_" 后转大写标志,默认字符前面没有"_"
        boolean flag = false;
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == UNDERLINE) {
                flag = true;
                // 标志设置为true,跳过
                continue;
            } else {
                if (flag == true) {
                    // 表示当前字符前面是"_" ,当前字符转大写
                    sb.append(Character.toUpperCase(str.charAt(i)));
                    // 重置标识
                    flag = false;
                } else {
                    sb.append(Character.toLowerCase(str.charAt(i)));
                }
            }
        }
        return sb.toString();
    }
}
