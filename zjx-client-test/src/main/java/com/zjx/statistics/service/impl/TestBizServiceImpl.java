package com.zjx.statistics.service.impl;

import com.zjx.statistics.domain.Data;
import com.zjx.statistics.service.TestBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Aaron
 * @date 2020/6/10 3:37 下午
 */
@Service
@Slf4j
public class TestBizServiceImpl implements TestBizService {

    @Override
    public Data registerUser(Data data) {
        log.info("{} -- registerUser 入参:{}", Thread.currentThread().getName(), data);
        log.info("{} -- registerUser 业务逻辑持久化操作...", Thread.currentThread().getName());
        return data;
    }

    @Override
    public void access(String userId) {
        log.info("{} -- access 入参:{}", Thread.currentThread().getName(), userId);
        log.info("{} -- access 业务逻辑持久化操作...", Thread.currentThread().getName());
    }
}
