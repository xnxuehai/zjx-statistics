package com.zjx.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zjx.domain.Data;
import com.zjx.service.TestBizService;
import com.zjx.statistics.facade.ReceiveInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 测试
 *
 * @author Aaron
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestBizService testBizService;

    @Reference
    private ReceiveInfo receiveInfo;

    /**
     * 一般统计数据测试
     *
     * @param data 测试数据
     * @return
     */
    @PostMapping("/mockCountData")
    public Data mockCountData(@RequestBody Data data) {
        log.info("paramMap:{}", data);
        Data res = new Data();
        switch (data.getFlag()) {
            case 1:
                res = testBizService.usersStudyRecord(data);
                break;
            case 2:
                res = testBizService.userStudyWikiVideo(data);
                break;
            case 3:
                res = testBizService.userDubVideoRecord(data);
                break;
            case 4:
                res = testBizService.chapterUserDub(data);
                break;
            case 5:
                res = testBizService.usersQuestionRecord(data);
                break;
            case 6:
                res = testBizService.userDub(data);
                break;
            case 7:
                res = testBizService.messageTask(data);
                break;
            default:
                log.info("没有对应的操作....");
                break;
        }

        return res;
    }


    /**
     * 用户排名测试
     *
     * @param userId     用户id
     * @param experience 经验值
     * @param medal      奖章
     * @return
     */
    @GetMapping("/mockUserSort/{userId}/{experience}/{medal}")
    public String mockUserSort(@PathVariable("userId") Integer userId
            , @PathVariable("experience") Integer experience
            , @PathVariable("medal") Integer medal) {
        log.info("接收数据 userId:{},experience:{},medal:{}", userId, experience, medal);
        receiveInfo.receiveInfo(userId, experience, medal);

        return "SUCCESS";
    }
}
