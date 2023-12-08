package com.gill.gutil.statistic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.gill.gutil.log.ILogger;
import com.gill.gutil.log.LoggerFactory;

/**
 * CostTest
 *
 * @author gill
 * @version 2023/12/08
 **/
public class CostTest {

    private static ILogger mockLogger;

    @BeforeAll
    public static void init() {
        MockedStatic<LoggerFactory> factoryMock = Mockito.mockStatic(LoggerFactory.class);
        mockLogger = Mockito.mock(ILogger.class);
        factoryMock.when(() -> LoggerFactory.getLogger(Cost.class)).thenReturn(mockLogger);
    }

    @AfterAll
    public static void after() {
        Mockito.verify(mockLogger, Mockito.times(2)).warn(Mockito.anyString(), Mockito.any(Object[].class));
    }

    @Test
    public void testCost_retCost() {
        Assertions.assertEquals("test", Cost.cost(() -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
            return "test";
        }, "test"));
        Assertions.assertEquals("test", Cost.cost(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
            return "test";
        }, "test"));
    }

    @Test
    public void testCost_retVoid() {
        Cost.cost(() -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
        }, "test");
        Cost.cost(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }, "test");
    }

    @Test
    public void testCostMerge_retAny() {
        Statistic statistic = Statistic.newStatistic("test");
        String ret = Cost.costMerge(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            return "test";
        }, statistic);
        Assertions.assertEquals("test", ret);
        Assertions.assertEquals(1, statistic.getSampleSize());
        Assertions.assertTrue(800 < statistic.getMax() && statistic.getAvg() < 1200);
        Assertions.assertTrue(800 < statistic.getMin() && statistic.getAvg() < 1200);
        Assertions.assertTrue(800 < statistic.getAvg() && statistic.getAvg() < 1200);
    }

    @Test
    public void testCostMerge_retVoid() {
        Statistic statistic = Statistic.newStatistic("test");
        Cost.costMerge(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }, statistic);
        Assertions.assertEquals(1, statistic.getSampleSize());
        Assertions.assertTrue(800 < statistic.getMax() && statistic.getAvg() < 1200);
        Assertions.assertTrue(800 < statistic.getMin() && statistic.getAvg() < 1200);
        Assertions.assertTrue(800 < statistic.getAvg() && statistic.getAvg() < 1200);
    }
}
