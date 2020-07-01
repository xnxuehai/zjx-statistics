package com.zjx.statistics.handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 统计处理器上下文
 *
 * @author Aaron
 * @date 2020/7/1 13:46
 */
public class StatisticsHandlerContext {
    /**
     * 统计处理器集合
     */
    static List<StatisticsHandler> statisticsHandlersList = new CopyOnWriteArrayList<>();

    /**
     * 调用所有的处理器
     */
    public static void processAll() {

        for (StatisticsHandler handler : statisticsHandlersList) {
            // 循环执行每一个处理器
            handler.process();
        }
    }

    /**
     * 添加处理器
     *
     * @param statisticsHandler
     */
    public static void addStatisticsHandler(StatisticsHandler statisticsHandler) {
        statisticsHandlersList.add(statisticsHandler);
    }

}
