package com.gill.gutil.statistic;

import com.gill.gutil.log.ILogger;
import com.gill.gutil.log.LoggerFactory;

/**
 * Statistic
 *
 * @author gill
 * @version 2023/12/08
 **/
public class Statistic {

    private static final ILogger log = LoggerFactory.getLogger(Statistic.class);

    private final String name;

    private double max;

    private double min;

    private double avg;

    private long sampleSize;

    Statistic(String name, long max, long min, double avg, long sampleSize) {
        this.name = name;
        this.max = max;
        this.min = min;
        this.avg = avg;
        this.sampleSize = sampleSize;
    }

    public static Statistic newStatistic(String name) {
        return new Statistic(name, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
    }

    public synchronized Statistic merge(Statistic other) {
        this.max = Math.max(this.max, other.max);
        this.min = Math.min(this.max, other.min);
        this.avg = (this.avg * this.sampleSize + other.avg * other.sampleSize) / (this.sampleSize + other.sampleSize);
        this.sampleSize = this.sampleSize + other.sampleSize;
        return this;
    }

    public synchronized Statistic merge(double sample) {
        this.max = Math.max(this.max, sample);
        this.min = Math.min(this.min, sample);
        this.avg = (this.avg * this.sampleSize + sample) / (this.sampleSize + 1);
        this.sampleSize++;
        return this;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getAvg() {
        return avg;
    }

    public long getSampleSize() {
        return sampleSize;
    }

    public void println() {
        log.info("{} cost statistic => max: {}, min: {}, avg: {}, sample size: {}", name, this.max, this.min, this.avg,
            this.sampleSize);
    }
}
