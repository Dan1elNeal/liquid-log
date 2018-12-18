package ru.naumen.sd40.log.parser.Render;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;

public class RenderTimeData {
    private double min;
    private double max;
    private double mean;
    private double stddev;
    private double percent50;
    private double percent95;
    private double percent99;
    private double percent999;
    private long count;

    boolean isNan = true;

    ArrayList<Integer> times = new ArrayList<>();

    public void calculate() {
        DescriptiveStatistics ds = new DescriptiveStatistics();
        times.forEach(t -> ds.addValue(t));
        min = ds.getMin();
        mean = ds.getMean();
        stddev = ds.getStandardDeviation();
        percent50 = ds.getPercentile(50.0);
        percent95 = ds.getPercentile(95.0);
        percent99 = ds.getPercentile(99.0);
        percent999 = ds.getPercentile(99.9);
        max = ds.getMax();
        count = ds.getN();
        isNan = count == 0;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMean() {
        return mean;
    }

    public double getStddev() {
        return stddev;
    }

    public double getPercent50() {
        return percent50;
    }

    public double getPercent95() {
        return percent95;
    }

    public double getPercent99() {
        return percent99;
    }

    public double getPercent999() {
        return percent999;
    }

    public long getCount() {
        return count;
    }

    public boolean isNan() {
        return isNan;
    }

    public void addTime(int time) {
        times.add(time);
    }
}
