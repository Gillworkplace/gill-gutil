package com.gill.gutil.statistic;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * StatisticTest
 *
 * @author gill
 * @version 2023/11/30
 **/
public class CounterTest {

    @Test
    public void testCounter_shouldBeAutoRelease() throws Exception {
        Counter test = Counter.newCounter("test");

        Map<String, Long> all = Counter.getAll();
        Assertions.assertTrue(all.containsKey("test"));
        Assertions.assertEquals(0, all.get("test"));

        // 引用断开后自动回收counter对象
        test = null;
        System.gc();
        Thread.sleep(1000);
        all = Counter.getAll();
        Assertions.assertFalse(all.containsKey("test"));
    }

}
