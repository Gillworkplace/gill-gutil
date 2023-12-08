package com.gill.gutil.statistic;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.gill.gutil.log.ILogger;
import com.gill.gutil.log.LoggerFactory;

/**
 * CostStatistic
 *
 * @author gill
 * @version 2023/11/23
 **/
public class Cost {

    private static final ILogger log = LoggerFactory.getLogger(Cost.class);

    private static final int DEFAULT_LOG_THRESHOLD = 10;

    /**
     * 耗时计算
     * 
     * @param func 方法
     * @param funcName 方法名
     * @return 返回结果
     * @param <T> 类型
     */
    public static <T> T cost(Supplier<T> func, String funcName) {
        return cost(func, funcName, DEFAULT_LOG_THRESHOLD);
    }

    /**
     * 耗时计算
     *
     * @param func 方法
     * @param funcName 方法名
     */
    public static void cost(Runnable func, String funcName) {
        cost(func, funcName, DEFAULT_LOG_THRESHOLD);
    }

    /**
     * 耗时计算
     *
     * @param func 方法
     * @param funcName 方法名
     * @param logThreshold 打印日志的阈值时间
     * @return 返回结果
     * @param <T> 类型
     */
    public static <T> T cost(Supplier<T> func, String funcName, long logThreshold) {
        return cost(func, () -> funcName, logThreshold);
    }

    /**
     * 耗时计算
     *
     * @param func 方法
     * @param funcName 方法名
     * @param logThreshold 打印日志的阈值时间
     */
    public static void cost(Runnable func, String funcName, long logThreshold) {
        cost(func, () -> funcName, logThreshold);
    }

    /**
     * 耗时计算
     *
     * @param func 方法
     * @param taskNameSupplier 方法名
     * @param logThreshold 打印日志的阈值时间
     * @return 返回结果
     * @param <T> 类型
     */
    public static <T> T cost(Supplier<T> func, Supplier<String> taskNameSupplier, long logThreshold) {
        long start = System.nanoTime();
        T ret = func.get();
        long end = System.nanoTime();
        long diff = TimeUnit.NANOSECONDS.toMillis(end - start);
        if (diff > logThreshold) {
            log.warn("{} cost {}ms", taskNameSupplier.get(), diff);
        }
        return ret;
    }

    /**
     * 耗时计算
     *
     * @param func 方法
     * @param taskNameSupplier 方法名
     * @param logThreshold 打印日志的阈值时间
     */
    public static void cost(Runnable func, Supplier<String> taskNameSupplier, long logThreshold) {
        long start = System.nanoTime();
        func.run();
        long end = System.nanoTime();
        long diff = TimeUnit.NANOSECONDS.toMillis(end - start);
        if (diff > logThreshold) {
            log.warn("{} cost {}ms", taskNameSupplier.get(), diff);
        }
    }

    /**
     * 耗时计算
     *
     * @param func 方法
     * @param statistic 统计数据
     * @return 返回结果
     * @param <T> 类型
     */
    public static <T> T costMerge(Supplier<T> func, Statistic statistic) {
        long start = System.nanoTime();
        T ret = func.get();
        long end = System.nanoTime();
        long sample = TimeUnit.NANOSECONDS.toMillis(end - start);
        statistic.merge(sample);
        return ret;
    }

    /**
     * 耗时计算
     *
     * @param func 方法
     * @param statistic 统计数据
     */
    public static void costMerge(Runnable func, Statistic statistic) {
        long start = System.nanoTime();
        func.run();
        long end = System.nanoTime();
        long sample = TimeUnit.NANOSECONDS.toMillis(end - start);
        statistic.merge(sample);
    }
}
