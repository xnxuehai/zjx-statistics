package com.park.service.impl;

import com.park.service.BizService;
import org.springframework.stereotype.Service;

/**
 * @author Aaron
 * @date 2020/6/10 3:37 下午
 */
@Service
public class BizServiceImpl implements BizService {

    @Override
    public void doServiceStatistics(String name) {
        System.out.println("通过代理执行业务逻辑...... param is:" + name);
    }

    @Override
    public void doService(String name) {
        System.out.println("不通过代理执行业务逻辑...... param is:" + name);
    }
}
