package com.zjx.statistics.handler;

import com.zjx.statistics.cache.CacheStore;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import com.zjx.statistics.transport.TransDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据校验器
 *
 * @author Aaron
 * @date 2020/6/30 15:42
 */
@Slf4j
@Component
public class ValidateHandler {

    /**
     * 验证数据是否合法
     *
     * @param transDTO
     * @return
     */
    public boolean validate(TransDTO transDTO) {
        // 数据校验
        StatisticsFieldDTO statisticsFieldDTO = CacheStore.getInstance().getHashFile(transDTO.getModule());
        List<StatisticsFieldHashRuleDTO> hashFieldList = statisticsFieldDTO.getHashFieldList();
        log.info("statisticsFieldDTO:{}", statisticsFieldDTO);

        if (hashFieldList.size() == 0) {
            // TODO 添加日志， 没有找到需要统计的数据
            log.error("没有找到指定的统计数据! key:{}", transDTO.getModule());
            return false;
        }
        return true;
    }
}
