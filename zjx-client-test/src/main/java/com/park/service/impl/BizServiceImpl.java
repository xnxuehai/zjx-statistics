package com.park.service.impl;

import com.park.domain.Data;
import com.park.service.BizService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Aaron
 * @date 2020/6/10 3:37 下午
 */
@Service
public class BizServiceImpl implements BizService {

    @Override
    public String doServiceStatistics(Map<String, String> paramMap, Integer userId) {
        System.out.println("通过代理执行业务逻辑...... paramMap is:" + paramMap + "userId is:" + userId);
        return "SUCCESS";
    }

    @Override
    public Data doService(String name) {
        System.out.println("不通过代理执行业务逻辑...... param is:" + name);
        return new Data();
    }

    @Override
    public Data doService(String userId, Data data, String str) {
        System.out.println("通过代理执行业务逻辑...... param is:" + data + " str:" + str + "userId:" + userId);
        return new Data("Aaron", 20, 174, 1);
    }
}
