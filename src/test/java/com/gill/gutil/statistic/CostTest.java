package com.gill.gutil.statistic;

import java.util.function.Supplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * CostTest
 *
 * @author gill
 * @version 2023/12/08
 **/
public class CostTest {

    @Test
    public void testNewStatistic() {
        String name = "Test Cost";
        Cost result = Cost.newStatistic(name);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getAvg());
        Assertions.assertEquals(Integer.MIN_VALUE, result.getMax());
        Assertions.assertEquals(Integer.MAX_VALUE, result.getMin());
        Assertions.assertEquals(0, result.getSampleSize());
    }

    @Test
    public void testCost_retCost() {
        // 创建一个测试用的Supplier
        Supplier<Long> testFunc = () -> System.currentTimeMillis();

        // 调用cost方法进行测试
        Long result = Cost.cost(testFunc, "获取当前时间");

        // 验证返回结果是否符合预期
        Assertions.assertNotNull(result);
    }

    @Test
    public void testCos_retVoid() {
        // Prepare

        // Execute
        String test = Cost.cost(() -> "test", "name");

        // Verify
        Assertions.assertEquals("test", test);
    }

    @Test
    public void testMerge() {
        Cost cost1 = new Cost("cost1", 10, 5, 7.5d, 10);
        Cost cost2 = new Cost("cost2", 15, 5, 10.0d, 5);
        Cost mergedCost = Cost.newStatistic("merge");
        mergedCost.merge(cost1);
        mergedCost.merge(cost2);

        // Assert that mergedCost has the correct values
        Assertions.assertEquals(15, mergedCost.getMax());
        Assertions.assertEquals(5, mergedCost.getMin());
        Assertions.assertEquals(8.333333333333333333d, mergedCost.getAvg());
        Assertions.assertEquals(15, mergedCost.getSampleSize());
    }

    @Test
    public void testMerge_shouldUpdateMax() {
        Cost cost = Cost.newStatistic("merge");
        double sample = 10.5;
        Cost result = cost.merge(sample);
        Assertions.assertEquals(sample, result.getMax(), 0.001);
    }

    @Test
    public void testMerge_shouldUpdateMin() {
        Cost cost = Cost.newStatistic("merge");
        double sample = 10.5;
        Cost result = cost.merge(sample);
        Assertions.assertEquals(sample, result.getMin(), 0.001);
    }

    @Test
    public void testMerge_shouldUpdateAvg() {
        Cost cost = Cost.newStatistic("merge");
        double sample1 = 10.5;
        double sample2 = 20.7;
        Cost result = cost.merge(sample1).merge(sample2);
        Assertions.assertEquals((sample1 + sample2) / 2, result.getAvg(), 0.001);
    }

    @Test
    public void testMerge_shouldIncrementSampleSize() {
        Cost cost = Cost.newStatistic("merge");
        long initialSampleSize = cost.getSampleSize();
        cost.merge(10.5);
        Assertions.assertEquals(initialSampleSize + 1, cost.getSampleSize());
    }

    @Test
    public void testMerge_shouldReturnSameInstance() {
        Cost cost = Cost.newStatistic("merge");
        Cost result = cost.merge(10.5);
        Assertions.assertSame(cost, result);
    }
}
