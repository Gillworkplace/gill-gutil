package com.gill.gutil.statistic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * CostTest
 *
 * @author gill
 * @version 2023/12/08
 **/
public class StatisticTest {

    @Test
    public void testNewStatistic() {
        String name = "Test Cost";
        Statistic result = Statistic.newStatistic(name);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getAvg());
        Assertions.assertEquals(Integer.MIN_VALUE, result.getMax());
        Assertions.assertEquals(Integer.MAX_VALUE, result.getMin());
        Assertions.assertEquals(0, result.getSampleSize());
    }

    @Test
    public void testMerge() {
        Statistic statistic1 = new Statistic("statistic1", 10, 5, 7.5d, 10);
        Statistic statistic2 = new Statistic("statistic2", 15, 5, 10.0d, 5);
        Statistic mergedCost = Statistic.newStatistic("merge");
        mergedCost.merge(statistic1).merge(statistic2);

        // Assert that mergedCost has the correct values
        Assertions.assertEquals(15, mergedCost.getMax());
        Assertions.assertEquals(5, mergedCost.getMin());
        Assertions.assertEquals(8.333333333333333333d, mergedCost.getAvg());
        Assertions.assertEquals(15, mergedCost.getSampleSize());
    }

    @Test
    public void testMerge_shouldUpdateMax() {
        Statistic statistic = Statistic.newStatistic("merge");
        double sample = 10.5;
        Statistic result = statistic.merge(sample);
        Assertions.assertEquals(sample, result.getMax(), 0.001);
    }

    @Test
    public void testMerge_shouldUpdateMin() {
        Statistic statistic = Statistic.newStatistic("merge");
        double sample = 10.5;
        Statistic result = statistic.merge(sample);
        Assertions.assertEquals(sample, result.getMin(), 0.001);
    }

    @Test
    public void testMerge_shouldUpdateAvg() {
        Statistic statistic = Statistic.newStatistic("merge");
        double sample1 = 10.5;
        double sample2 = 20.7;
        Statistic result = statistic.merge(sample1).merge(sample2);
        Assertions.assertEquals((sample1 + sample2) / 2, result.getAvg(), 0.001);
    }

    @Test
    public void testMerge_shouldIncrementSampleSize() {
        Statistic statistic = Statistic.newStatistic("merge");
        long initialSampleSize = statistic.getSampleSize();
        statistic.merge(10.5);
        Assertions.assertEquals(initialSampleSize + 1, statistic.getSampleSize());
    }

    @Test
    public void testMerge_shouldReturnSameInstance() {
        Statistic statistic = Statistic.newStatistic("merge");
        Statistic result = statistic.merge(10.5);
        Assertions.assertSame(statistic, result);
    }
}
