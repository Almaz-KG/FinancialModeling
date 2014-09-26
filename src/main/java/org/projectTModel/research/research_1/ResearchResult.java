package org.projectTModel.research.research_1;

import org.projectTModel.entities.Bar;

/**
 * User: Almaz
 * Date: 01.09.14
 * Time: 21:07
 */
public class ResearchResult {
    private String stockName;
    private double avgSize;
    private Bar[] bars;


    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    public Bar[] getBars() {
        return bars;
    }
    public void setBars(Bar[] bars) {
        this.bars = bars;
    }
    public double getAvgSize() {
        return avgSize;
    }
    public void setAvgSize(double avgSize) {
        this.avgSize = avgSize;
    }
}
