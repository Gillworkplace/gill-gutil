package com.gill.gutil.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.gill.gutil.log.ILogger;
import com.gill.gutil.log.LoggerFactory;

/**
 * PoolUtil
 *
 * @author gill
 * @version 2023/12/08
 **/
public class PoolUtil {

    private static final ILogger log = LoggerFactory.getLogger(PoolUtil.class);

    /**
     * 等待线程池关闭
     *
     * @param executorService 线程池
     * @param poolName 线程池名
     */
    public static void awaitTermination(ExecutorService executorService, String poolName) {
        long start = System.currentTimeMillis();
        try {
            while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                log.debug("waiting {} termination, duration: {}", poolName, System.currentTimeMillis() - start);
            }
            log.debug("{} is terminated, duration: {}", poolName, System.currentTimeMillis() - start);
        } catch (InterruptedException e) {
            log.warn("{} awaitTermination is interrupted, e: {}", poolName, e);
        }
    }
}
