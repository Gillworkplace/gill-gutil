package com.gill.gutil.thread;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * PoolUtilTest
 *
 * @author gill
 * @version 2023/12/08
 **/
public class PoolUtilTest {

    @Test
    public void testScheduleServiceAwaitTermination() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("test"));
        executor.schedule(() -> System.out.println("Test"), 100, java.util.concurrent.TimeUnit.MILLISECONDS);
        executor.shutdownNow();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(1000),
            () -> PoolUtil.awaitTermination(executor, "Test Pool"));
        Assertions.assertTrue(executor.isTerminated());
    }

    @Test
    public void testThreadPoolAwaitTermination() {
        ExecutorService executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("test"));
        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        executor.shutdownNow();
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(1000),
            () -> PoolUtil.awaitTermination(executor, "Test Pool"));
        Assertions.assertTrue(executor.isTerminated());
    }
}
