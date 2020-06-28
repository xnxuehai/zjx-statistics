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
    public Data usersStudyRecord(Data data) {
        log.info("{} -- usersStudyRecord 入参:{}", Thread.currentThread().getName(), data);
        log.info("{} -- usersStudyRecord 业务逻辑持久化操作...", Thread.currentThread().getName());
        return data;
    }

    @Override
    public Data userStudyWikiVideo(Data data) {
        log.info("{} -- userStudyWikiVideo 入参:{}", Thread.currentThread().getName(), data);
        log.info("{} -- userStudyWikiVideo 业务逻辑持久化操作...", Thread.currentThread().getName());
        return data;
    }

    @Override
    public Data userDubVideoRecord(Data data) {
        log.info("{} -- userDubVideoRecord 入参:{}", Thread.currentThread().getName(), data);
        log.info("{} -- userDubVideoRecord 业务逻辑持久化操作...", Thread.currentThread().getName());
        return data;
    }

    @Override
    public Data chapterUserDub(Data data) {
        log.info("{} -- chapterUserDub 入参:{}", Thread.currentThread().getName(), data);
        log.info("{} -- chapterUserDub 业务逻辑持久化操作...", Thread.currentThread().getName());
        return data;
    }

    @Override
    public Data usersQuestionRecord(Data data) {
        log.info("{} -- usersQuestionRecord 入参:{}", Thread.currentThread().getName(), data);
        log.info("{} -- usersQuestionRecord 业务逻辑持久化操作...", Thread.currentThread().getName());
        return data;
    }

    @Override
    public Data userDub(Data data) {
        log.info("{} -- userDub 入参:{}", data);
        log.info("{} -- userDub 业务逻辑持久化操作...", Thread.currentThread().getName(), Thread.currentThread().getName());
        return data;
    }

    @Override
    public Data messageTask(Data data) {
        log.info("{} -- messageTask 入参:{}", data);
        log.info("{} -- messageTask 业务逻辑持久化操作...", Thread.currentThread().getName(), Thread.currentThread().getName());
        return data;
    }
}
