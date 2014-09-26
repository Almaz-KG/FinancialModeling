package org.projectTModel.strategies.SimpleStrategyLarriVilyams;

import org.projectTModel.entities.StockHistory;
import org.projectTModel.entities.Trade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almu0214 on 26.09.2014.
 */
public class StrategyResult {
    private List<Trade> trades;
    private StockHistory history;

    public StrategyResult() {
        trades = new ArrayList<>();
    }

    public List<Trade> getTrades() {
        return trades;
    }
    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }
    public void addTrade(Trade tr){
        trades.add(tr);
    }
    public StockHistory getHistory() {
        return history;
    }
    public void setHistory(StockHistory history) {
        this.history = history;
    }

}
