package com.zjx.statistics.annotation.parser;

import com.zjx.statistics.annotation.StatisticsAnnotationParser;
import com.zjx.statistics.annotation.ZjxStatistics;
import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import com.zjx.statistics.interceptor.operation.ZjxStatisticsOperation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 注解解析器
 *
 * @author Aaron
 * @date 2020/6/10 11:58 上午
 */
public class ZjxStatisticsAnnotationParser implements StatisticsAnnotationParser, Serializable {


    private static final Set<Class<? extends Annotation>> STATISTICS_OPERATION_ANNOTATIONS = new LinkedHashSet<>(1);

    static {
        // 初始化可解析的注解
        STATISTICS_OPERATION_ANNOTATIONS.add(ZjxStatistics.class);
    }


    @Override
    public Collection<AbstractStatisticsOperation> parseStatisticsAnnotations(Class<?> clazz) {
        return parseStatisticsAnnotationsReal(clazz);
    }

    @Override
    public Collection<AbstractStatisticsOperation> parseStatisticsAnnotations(Method method) {
        return parseStatisticsAnnotationsReal(method);
    }

    @Nullable
    private Collection<AbstractStatisticsOperation> parseStatisticsAnnotationsReal(AnnotatedElement ae) {
        Collection<AbstractStatisticsOperation> ops = parseCacheAnnotations(ae, false);
        if (ops != null && ops.size() > 1) {
            Collection<AbstractStatisticsOperation> localOps = parseCacheAnnotations(ae, true);
            if (localOps != null) {
                return localOps;
            }
        }
        return ops;
    }


    @Nullable
    private Collection<AbstractStatisticsOperation> parseCacheAnnotations(AnnotatedElement ae, boolean localOnly) {

        Collection<? extends Annotation> anns = (localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(ae, STATISTICS_OPERATION_ANNOTATIONS) :
                AnnotatedElementUtils.findAllMergedAnnotations(ae, STATISTICS_OPERATION_ANNOTATIONS));

        if (anns.isEmpty()) {
            return null;
        }

        final Collection<AbstractStatisticsOperation> ops = new ArrayList<>(1);

        anns.stream().filter(ann -> ann instanceof ZjxStatistics).forEach(ann -> ops.add(parseZjxStatisticsAnnotation(ae, (ZjxStatistics) ann)));

        return ops;
    }

    /**
     * 将 {@link ZjxStatistics} 注解中的信息 保存到 {@link AbstractStatisticsOperation} 中
     *
     * @param ae            AnnotatedElement
     * @param zjxStatistics ZjxStatistics
     * @return
     */
    private AbstractStatisticsOperation parseZjxStatisticsAnnotation(AnnotatedElement ae, ZjxStatistics zjxStatistics) {

        AbstractStatisticsOperation.Builder builder = new ZjxStatisticsOperation.Builder();

        builder.setName(ae.toString());

        builder.setKey(zjxStatistics.key());
        builder.setModule(zjxStatistics.module());
        builder.setCountSelf(zjxStatistics.isCountSelf());
        builder.setParamField(zjxStatistics.paramField());
        builder.setTableField(zjxStatistics.tableField());
        builder.setOpenStatus(zjxStatistics.openStatus());
        builder.setTableStatus(zjxStatistics.tableStatus());

        AbstractStatisticsOperation op = builder.build();

        return op;
    }

}
