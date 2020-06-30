package com.zjx.statistics.handler;

import com.alibaba.fastjson.JSON;
import com.zjx.statistics.transport.TransDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 解析数据处理器
 *
 * @author Aaron
 * @date 2020/6/30 15:41
 */
@Slf4j
@Component
public class ParseHandler {
    /**
     * 将 json 解析为 实体
     *
     * @param json json
     * @return TransDTO
     */
    public TransDTO parse(String json) {
        TransDTO transDTO = JSON.toJavaObject(JSON.parseObject(json), TransDTO.class);
        log.info("transDTO:{}", transDTO.toString());
        return transDTO;
    }
}
