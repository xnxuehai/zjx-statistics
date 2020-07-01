package com.zjx.statistics.service;

/**
 * 添加异常日志
 *
 * @author Aaron
 * @date 2020/7/1 9:39
 */
public interface IErrorLogService {
    /**
     * 添加异常日志
     *
     * @param moduleName 模块名称
     * @param desc       描述信息
     */
    void save(String moduleName, String desc);
}
