package org.projectTModel.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Almaz
 * Date: 23.08.14
 * Time: 18:43
 */
public class StrategyResultReport {
    private String stockName;
    private int barsLength;
    private List<Trade> orders;

    public StrategyResultReport() {
        orders = new ArrayList<>();
    }
    public StrategyResultReport(String stockName){
        this.stockName = stockName;
        orders = new ArrayList<>();
    }


    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    public void setOrders(List<Trade> orders) {
        this.orders = orders;
    }
    public void addTrade(Trade trade){
        this.orders.add(trade);
    }
    public List<Trade> getOrders() {
        return orders;
    }
    public int getBarsLength() {
        return barsLength;
    }
    public void setBarsLength(int barsLength) {
        this.barsLength = barsLength;
    }

    public double getProfitResult() {
        double result = 0.0;

        for (Trade tr: orders)
            result += tr.getResult();
        return result;
    }
}
