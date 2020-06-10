package com.zjx.statistics.interceptor.operation.source;

import com.zjx.statistics.annotation.StatisticsAnnotationParser;
import com.zjx.statistics.annotation.parser.ZjxStatisticsAnnotationParser;
import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 统计操作源
 *
 * @author Aaron
 * @date 2020/6/10 11:52 上午
 */
public class AnnotationStatisticsOperationSource extends AbstractStatisticsOperationSource {

    private final Set<StatisticsAnnotationParser> annotationParsers;

    public AnnotationStatisticsOperationSource() {
        this.annotationParsers = Collections.singleton(new ZjxStatisticsAnnotationParser());
    }

    public AnnotationStatisticsOperationSource(StatisticsAnnotationParser annotationParser) {
        Assert.notNull(annotationParser, "StatisticsAnnotationParser must not be null");
        this.annotationParsers = Collections.singleton(annotationParser);
    }

    public AnnotationStatisticsOperationSource(StatisticsAnnotationParser... annotationParsers) {
        Assert.notEmpty(annotationParsers, "At least one StatisticsAnnotationParser needs to be specified");
        this.annotationParsers = new LinkedHashSet<>(Arrays.asList(annotationParsers));
    }

    public AnnotationStatisticsOperationSource(Set<StatisticsAnnotationParser> annotationParsers) {
        Assert.notEmpty(annotationParsers, "At least one StatisticsAnnotationParser needs to be specified");
        this.annotationParsers = annotationParsers;
    }

    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        for (StatisticsAnnotationParser parser : this.annotationParsers) {
            if (parser.isCandidateClass(targetClass)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Collection<AbstractStatisticsOperation> findCacheOperations(Class<?> clazz) {
        return determineCacheOperations(parser -> parser.parseCacheAnnotations(clazz));
    }

    @Override
    protected Collection<AbstractStatisticsOperation> findCacheOperations(Method method) {
        return determineCacheOperations(parser -> parser.parseCacheAnnotations(method));
    }

    @Nullable
    protected Collection<AbstractStatisticsOperation> determineCacheOperations(StatisticsOperationProvider provider) {
        Collection<AbstractStatisticsOperation> ops = null;
        for (StatisticsAnnotationParser parser : this.annotationParsers) {
            Collection<AbstractStatisticsOperation> annOps = provider.getStatisticsOperations(parser);
            if (annOps != null) {
                if (ops == null) {
                    ops = annOps;
                } else {
                    Collection<AbstractStatisticsOperation> combined = new ArrayList<>(ops.size() + annOps.size());
                    combined.addAll(ops);
                    combined.addAll(annOps);
                    ops = combined;
                }
            }
        }
        return ops;
    }


    @FunctionalInterface
    protected interface StatisticsOperationProvider {

        @Nullable
        Collection<AbstractStatisticsOperation> getStatisticsOperations(StatisticsAnnotationParser parser);
    }
}
