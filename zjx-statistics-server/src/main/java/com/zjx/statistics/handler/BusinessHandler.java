package com.zjx.statistics.handler;

import com.zjx.statistics.transport.TransDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 业务处理
 *
 * @author Aaron
 * @date 2020/6/30 16:01
 */
@Component
@Slf4j
public class BusinessHandler extends AbstractBusinessHandler {
    @Resource
    private ParseHandler parseHandler;
    @Resource
    private ValidateHandler validateHandler;
    @Resource
    private ComputeHandler computeHandler;

    @Override
    protected TransDTO parse(String json) {
        return parseHandler.parse(json);
    }

    @Override
    protected boolean validate(TransDTO transDTO) {
        return validateHandler.validate(transDTO);
    }

    @Override
    protected void computeAndSave(TransDTO transDTO, String key) {
        computeHandler.compute(transDTO, key);
    }
}
