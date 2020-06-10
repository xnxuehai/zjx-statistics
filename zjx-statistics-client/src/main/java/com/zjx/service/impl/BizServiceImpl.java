package com.zjx.service.impl;

import com.zjx.statistics.annotation.ZjxStatistics;
import com.zjx.service.BizService;
import org.springframework.stereotype.Service;

/**
 * @author Aaron
 * @date 2020/6/10 3:37 下午
 */
@Service
public class BizServiceImpl implements BizService {

    @Override
    @ZjxStatistics(module = "video", field = {"name", "pwd"})
    public void doService(String name) {
        System.out.println("执行业务逻辑...... param is:" + name);
    }
}
