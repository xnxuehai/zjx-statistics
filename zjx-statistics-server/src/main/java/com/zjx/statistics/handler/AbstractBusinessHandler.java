package com.zjx.statistics.handler;

import com.zjx.statistics.constant.Constant;
import com.zjx.statistics.transport.TransDTO;
import com.zjx.statistics.util.DateUtil;

/**
 * @author Aaron
 * @date 2020/6/30 15:50
 */
public abstract class AbstractBusinessHandler {

    /**
     * 业务处理
     *
     * @param json
     */
    public void process(String json) {
        // 解析数据
        TransDTO transDTO = parse(json);

        // 验证数据
        if (!validate(transDTO)) {
            return;
        }

        // 处理 key
        String key = processKey(transDTO.getModule(), transDTO.getKey().toString());

        // 进行计算 和 存储
        computeAndSave(transDTO, key);
    }

    private String processKey(String key, String value) {
        // TODO 将要匹配的正则当成一个动态的配置

        String regDate = "\\$date\\$";
        String reUserId = "\\$userId\\$";

        key = key.replaceAll(regDate, DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR));
        key = key.replaceAll(reUserId, value);
        
        return key;
    }

    /**
     * 解析
     *
     * @param json json
     * @return TransDTO
     */
    abstract TransDTO parse(String json);

    /**
     * 验证
     *
     * @param transDTO transDTO
     * @return
     */
    abstract boolean validate(TransDTO transDTO);

    /**
     * 计算
     *
     * @param transDTO
     * @param key
     */
    abstract void computeAndSave(TransDTO transDTO, String key);

}
