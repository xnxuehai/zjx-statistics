package com.zjx.statistics.service.impl;

import com.zjx.statistics.domain.ErrorLog;
import com.zjx.statistics.mapper.ErrorLogMapper;
import com.zjx.statistics.service.IErrorLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Aaron
 * @date 2020/7/1 9:41
 */
@Service
public class ErrorLogServiceImpl implements IErrorLogService {
    @Resource
    private ErrorLogMapper errorLogMapper;

    @Override
    public void save(String moduleName, String desc) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setModuleName(moduleName);
        errorLog.setDescription(desc);
        errorLogMapper.save(errorLog);
    }
}
