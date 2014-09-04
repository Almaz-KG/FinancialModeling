package org.projectTModel.strategies.strategy_2;

import org.projectTModel.entities.*;
import org.projectTModel.loader.Loader;
import org.projectTModel.result_builder.HtmlResultBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Strategy2_BuyAndSell {
    private static final double SIGNAL_BAR_SIZE_FACTOR = 0.11;
    private static final int OPEN_HOUR = 16;
    private static final int CLOSE_HOUR = 22;

    private static boolean isSignalBar(Bar bar){
        double d = bar.getHighPrice() - bar.getLowPrice();
        double s = bar.getOpenPrice() - bar.getClosePrice();
        if(s < 0)
            s *= -1;

        return s >= d * SIGNAL_BAR_SIZE_FACTOR;
    }
    public static void main(String[] args) throws IOException {
        File file = new File("P:\\Archive\\US-Stocks");
        List<StockHistory> histories = Loader.load(file);
        List<StrategyResultReport> resultReports = new ArrayList<>();

        for (StockHistory history: histories){
            StrategyResultReport report = new StrategyResultReport(history.getName());

            Bar[] bars = history.getBars();
            report.setBarsLength(bars.length);

            Trade trade = null;
            for (int i = 1; i < bars.length; i++){
                if(trade != null){
                    Bar b = bars[i];
                    double stopLoss = trade.getStopLoss();
                    if(trade.getType() == TRADE_TYPE.BUY && b.getLowPrice() <= stopLoss){
                        trade.setClosePrice(stopLoss);
                        report.addTrade(trade);
                        trade = null;
                    }  else if(trade.getType() == TRADE_TYPE.SELL && b.getHighPrice() >= stopLoss){
                        trade.setClosePrice(stopLoss);
                        report.addTrade(trade);
                        trade = null;
                    }
                }
                if(bars[i].getTime().getHour() == OPEN_HOUR){
                    if(bars[i].getOpenPrice() > bars[i - 1].getClosePrice() &&
                            bars[i].getOpenPrice() < bars[i].getClosePrice() &&
                            isSignalBar(bars[i])){
                        Bar bar = bars[i];
                        trade = new Trade(bar.getDate(), bar.getTime(), TRADE_TYPE.BUY,
                                bar.getOpenPrice() + (bar.getClosePrice() - bar.getOpenPrice()) / 2,
                                0, Double.MIN_VALUE);
                    }

                    if(bars[i].getOpenPrice() < bars[i - 1].getClosePrice() &&
                            bars[i].getOpenPrice() > bars[i].getClosePrice() &&
                            isSignalBar(bars[i])){
                        Bar bar = bars[i];
                        trade = new Trade(bar.getDate(), bar.getTime(), TRADE_TYPE.SELL,
                                bar.getOpenPrice() - (bar.getOpenPrice() - bar.getClosePrice()) / 2,
                                0, bar.getOpenPrice());
                    }
                    if(bars[i].getTime().getHour() == CLOSE_HOUR && trade != null) {
                        trade.setClosePrice(bars[i].getClosePrice());
                        report.addTrade(trade);
                        trade = null;
                    }
                }
                if(bars[i].getTime().getHour() == CLOSE_HOUR && trade != null) {
                    trade.setClosePrice(bars[i].getClosePrice());
                    report.addTrade(trade);
                    trade = null;
                }
            }
            if(trade != null){
                trade.setClosePrice(bars[bars.length - 1].getClosePrice());
                report.addTrade(trade);
            }
            resultReports.add(report);
        }

        File result = new File("D:\\strategy_result.html");
        HtmlResultBuilder resultBuilder = new HtmlResultBuilder("Simple stategy");
        resultBuilder.save(result, resultReports);
    }
}
