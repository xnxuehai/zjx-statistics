package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/18 10:57
 */
@Data
public class StatisticsOfDay {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer usersId;
    /**
     * TODO 以下字段注释 确定后再补
     */
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
