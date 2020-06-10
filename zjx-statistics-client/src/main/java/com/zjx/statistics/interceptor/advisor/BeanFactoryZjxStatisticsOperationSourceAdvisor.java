package com.zjx.statistics.interceptor.advisor;

import com.zjx.statistics.interceptor.StatisticsOperationSource;
import com.zjx.statistics.interceptor.pointcut.StatisticsOperationSourcePointcut;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.Nullable;

/**
 * 切面
 *
 * @author Aaron
 * @date 2020/6/10 10:37 上午
 */
public class BeanFactoryZjxStatisticsOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    @Nullable
    private StatisticsOperationSource statisticsOperationSource;

    /**
     * 初始化切点
     */
    private final StatisticsOperationSourcePointcut pointcut = new StatisticsOperationSourcePointcut() {

        @Override
        @Nullable
        protected StatisticsOperationSource getStatisticsOperationSource() {
            return statisticsOperationSource;
        }
    };

    /**
     * 设置一个统计操作源
     *
     * @param statisticsOperationSource
     */
    public void setStatisticsOperationSource(@Nullable StatisticsOperationSource statisticsOperationSource) {
        this.statisticsOperationSource = statisticsOperationSource;
    }

    public void setClassFilter(ClassFilter classFilter) {
        this.pointcut.setClassFilter(classFilter);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

}



