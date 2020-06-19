package com.zjx.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/18 10:57
 */
@Data
public class StatisticsOfDay {
    private Integer id;
    private Integer usersId;
    private Integer usersStudyRecordCounter;
    private Integer userStudyWikiVideoCounter;
    private Integer userDubVideoRecordCounter;
    private Integer usersStudyRecordDuration;
    private Integer userStudyWikiVideoDuration;
    private Integer userDubVideoRecordDuration;
    private Integer chapterUserDubCounter;
    private Integer chapterUserDubScore;
    private Integer usersQuestionRecordCounter;
    private Integer usersQuestionRecord1;
    private Integer usersQuestionRecord0;
    private Integer userDubCounter;
    private Integer userDubThumbNo;
    private Integer messageTaskCounter;
    private Integer messageTask1;
    private Integer messageTask0;
    private String countDate;
    private Date createTime;
}