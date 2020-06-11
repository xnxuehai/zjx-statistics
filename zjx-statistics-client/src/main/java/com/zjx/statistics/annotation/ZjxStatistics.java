package com.zjx.statistics.annotation;

import java.lang.annotation.*;

/**
 * @author Aaron
 * @date 2020/6/10 10:57 上午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZjxStatistics {
    /**
     * 统计的唯一标识（目前是 userId）
     *
     * @return
     */
    String key();

    /**
     * 模块（需要统计的模块名称）
     *
     * @return
     */
    String module();

    /**
     * 插入一条记录后是否统计
     *
     * @return
     */
    boolean isCountSelf() default false;

    /**
     * 方法参数的属性。
     * 注意：paramField 的值是与 tableField 的值 一一对应的关系，使用时，顺序必须保证一致。
     *
     * @return
     */
    String[] paramField() default "";

    /**
     * 方法参数对应的数据库字段。
     * 注意：paramField 的值是与 tableField 的值 一一对应的关系，使用时，顺序必须保证一致。
     *
     * @return
     */
    String[] tableField() default "";

    /**
     * 是否根据状态统计
     * <p>
     * 注意：openStatus 与 openStatus、statusValue 必须一一对应。
     * 例如：
     * status1 的值为 1，2，3
     * status2 的值为 4，5，6
     * 则：@ZjxStatistics（openStatus={"status1param","status2param"}）@ZjxStatistics（openStatus={"status1table","status2table"}）
     *
     * @return
     */
    String[] openStatus() default "";

    /**
     * 方法参数对应的数据库字段
     * <p>
     * 注意：openStatus 与 statusValue 必须一一对应。
     * 例如：
     * status1 的值为 1，2，3
     * status2 的值为 4，5，6
     * 则：@ZjxStatistics（openStatus={"status1param","status2param"}）@ZjxStatistics（openStatus={"status1table","status2table"}）
     *
     * @return
     */
    String[] tableStatus() default "";
}
