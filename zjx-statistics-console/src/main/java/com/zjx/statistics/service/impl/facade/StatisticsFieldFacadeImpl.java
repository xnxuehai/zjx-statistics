package com.zjx.statistics.service.impl.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.zjx.statistics.domain.StatisticsField;
import com.zjx.statistics.domain.StatisticsFieldFollow;
import com.zjx.statistics.domain.StatisticsRule;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import com.zjx.statistics.facade.StatisticsFieldFacade;
import com.zjx.statistics.service.IStatisticsFieldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Aaron
 * @date 2020/6/29 11:03
 */
@Slf4j
@Service
public class StatisticsFieldFacadeImpl implements StatisticsFieldFacade {
    @Resource
    private IStatisticsFieldService statisticsFieldService;

    @Override
    public List<StatisticsFieldDTO> getStatisticsFieldAll() {
        return parseField();
    }

    @Override
    public StatisticsFieldDTO getStatisticsFieldByCacheField(String cacheField) {
        log.info("getStatisticsFieldByCacheField param:{}", cacheField);
        StatisticsFieldDTO statisticsFieldDTO = null;

        StatisticsField statisticsField = statisticsFieldService.selectByCacheField(cacheField);
        if (statisticsField != null) {
            statisticsFieldDTO = new StatisticsFieldDTO();
            // 解析计算规则
            Map<Integer, String> ruleMap = parseRule();

            BeanUtils.copyProperties(statisticsField, statisticsFieldDTO);

            List<StatisticsFieldHashRuleDTO> hashFieldAndRule = getHashFieldAndRule(statisticsField.getId(), ruleMap);
            statisticsFieldDTO.setHashFieldList(hashFieldAndRule);
        }
        log.info("statisticsFieldDTO:{}", statisticsFieldDTO);
        return statisticsFieldDTO;
    }


    /**
     * 解析 field 数据
     *
     * @return
     */
    private List<StatisticsFieldDTO> parseField() {
        // 实例化数据容器
        List<StatisticsFieldDTO> list = new ArrayList<>();

        // 解析计算规则
        Map<Integer, String> ruleMap = parseRule();

        // 获取所有需要统计的属性
        List<StatisticsField> allField = statisticsFieldService.findAllField();

        StatisticsFieldDTO statisticsFieldDTO;
        for (StatisticsField statisticsField : allField) {
            statisticsFieldDTO = new StatisticsFieldDTO();
            BeanUtils.copyProperties(statisticsField, statisticsFieldDTO);

            List<StatisticsFieldHashRuleDTO> hashFieldAndRule = getHashFieldAndRule(statisticsField.getId(), ruleMap);
            statisticsFieldDTO.setHashFieldList(hashFieldAndRule);

            list.add(statisticsFieldDTO);
        }
        log.info("list:{}", list);
        return list;
    }

    /**
     * 解析属性
     *
     * @param id      id
     * @param ruleMap ruleMap
     * @return List<StatisticsFieldHashRuleDTO>
     */
    private List<StatisticsFieldHashRuleDTO> getHashFieldAndRule(Integer id, Map<Integer, String> ruleMap) {
        // 根据统计属性获取 hash field 和 计算规则
        List<StatisticsFieldFollow> hashFieldAndRule = statisticsFieldService.findByFieldId(id);
        List<StatisticsFieldHashRuleDTO> hashList = new ArrayList<>();
        StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO;
        for (StatisticsFieldFollow statisticsFieldFollow : hashFieldAndRule) {
            statisticsFieldHashRuleDTO = new StatisticsFieldHashRuleDTO();
            statisticsFieldHashRuleDTO.setHashField(statisticsFieldFollow.getHashField());
            statisticsFieldHashRuleDTO.setRuleEngine(ruleMap.get(statisticsFieldFollow.getRuleId()));
            hashList.add(statisticsFieldHashRuleDTO);
        }
        return hashList;
    }

    /**
     * 解析计算规则
     *
     * @return Map<Integer, String>
     */
    private Map<Integer, String> parseRule() {
        Map<Integer, String> rule = new HashMap<>(5);
        // 获取所有计算的规则
        List<StatisticsRule> allRule = statisticsFieldService.findAllRule();
        // 转换为 map
        allRule.forEach(statisticsRule -> {
            rule.put(statisticsRule.getId(), statisticsRule.getRuleEngine());
        });
        log.info("rule:{}", rule);
        return rule;
    }
}
