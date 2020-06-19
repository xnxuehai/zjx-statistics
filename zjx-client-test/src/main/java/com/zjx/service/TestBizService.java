package com.zjx.service;

import com.zjx.domain.Data;
import com.zjx.statistics.annotation.ZjxStatistics;

/**
 * 模拟统计模块服务
 *
 * @author Aaron
 * @date 2020/6/10 3:35 下午
 */
public interface TestBizService {
    /**
     * 小节视频
     *
     * @param data data
     * @return
     */
    @ZjxStatistics(key = "userId", isCountSelf = true, module = "users_study_record", paramField = "playProgress", tableField = "play_progress")
    Data usersStudyRecord(Data data);

    /**
     * 百科视频
     *
     * @param data
     * @return
     */
    @ZjxStatistics(key = "userId", isCountSelf = true, module = "user_study_wiki_video", paramField = "playProgress", tableField = "play_progress")
    Data userStudyWikiVideo(Data data);

    /**
     * 配音视频
     *
     * @param data
     * @return
     */
    @ZjxStatistics(key = "userId", isCountSelf = true, module = "user_dub_video_record", paramField = "playProgress", tableField = "play_progress")
    Data userDubVideoRecord(Data data);

    /**
     * 跟读测评
     *
     * @param data
     * @return
     */
    @ZjxStatistics(key = "userId", isCountSelf = true, module = "chapter_user_dub", paramField = "score", tableField = "score")
    Data chapterUserDub(Data data);

    /**
     * 课后练习
     *
     * @param data
     * @return
     */
    @ZjxStatistics(key = "userId", isCountSelf = true, module = "users_question_record", openStatus = "rightStatus", tableStatus = "right_status")
    Data usersQuestionRecord(Data data);

    /**
     * 趣味配音
     *
     * @param data
     * @return
     */
    @ZjxStatistics(key = "userId", isCountSelf = true, module = "user_dub", paramField = "thumbNo", tableField = "thumb_no")
    Data userDub(Data data);

    /**
     * 学习任务
     *
     * @param data
     * @return
     */
    @ZjxStatistics(key = "userId", isCountSelf = true, module = "message_task", openStatus = "status", tableStatus = "status")
    Data messageTask(Data data);
}
