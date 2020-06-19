package com.zjx.controller;

import com.zjx.domain.Data;
import com.zjx.service.TestBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试
 *
 * @author Aaron
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private HttpServletRequest request;
    private HttpServletResponse servletResponse;

    @Resource
    private TestBizService testBizService;

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse servletResponse) {
        this.request = request;
        this.servletResponse = servletResponse;
    }


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

}
