package com.zjx.statistics.handler;

import com.alibaba.fastjson.JSON;
import com.zjx.statistics.cache.CacheStore;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import com.zjx.statistics.transport.TransDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据校验器
 *
 * @author Aaron
 * @date 2020/6/30 15:42
 */
@Slf4j
@Component
public class ValidateHandler {
    @Resource(name = "producer")
    private DefaultMQProducer producer;

    /**
     * 验证数据是否合法
     *
     * @param transDTO transDTO
     * @return
     */
    public boolean validate(TransDTO transDTO) {
        // 数据校验
        StatisticsFieldDTO statisticsFieldDTO = CacheStore.getInstance().getHashFile(transDTO.getModule());
        if (statisticsFieldDTO == null) {
            log.error("没有找到指定的统计数据! key:{}", transDTO.getModule());
            SendResult sendResult = null;
            try {
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("moduleName", transDTO.getModule());
                errorMap.put("desc", "未找到对应的 field key");
                Message msg = new Message("error_log_topic", "error", JSON.toJSONString(errorMap).getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 发送消息到一个Broker
                sendResult = producer.send(msg);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                // 通过sendResult返回消息是否成功送达
                log.info("sendResult:{}", sendResult);
            }
            return false;
        }
        return true;
    }
}
