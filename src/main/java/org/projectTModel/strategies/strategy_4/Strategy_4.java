package org.projectTModel.strategies.strategy_4;

import org.projectTModel.entities.*;
import org.projectTModel.loader.Loader;
import org.projectTModel.result_builder.HtmlResultBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Strategy_4 {
    private static final double SIGNAL_BAR_SIZE_FACTOR = 1;
    private static final double TAKE_PROFIT_FACTOR = 1;
    private static final int OPEN_HOUR = 16;
    private static final int CLOSE_HOUR = 22;

    public static void main(String[] args) throws IOException{
        File file = new File("P:\\Archive\\US-Stocks");
        List<StockHistory> histories = Loader.load(file);
        List<StrategyResultReport> resultReports = new ArrayList<>();

        for (StockHistory history: histories){
            StrategyResultReport report = new StrategyResultReport(history.getName());

            Bar[] bars = history.getBars();
            report.setBarsLength(bars.length);

            Trade trade = null;
            for (int i = 1; i < bars.length; i++){
                if(bars[i].getHour() == OPEN_HOUR){
                    if(bars[i].getOpenPrice() > bars[i - 1].getClosePrice()){
                        double gap = bars[i].getOpenPrice() - bars[i - 1].getClosePrice();
                        double barSize = bars[i].getHighPrice() - bars[i].getLowPrice();
                        if(gap >= barSize * SIGNAL_BAR_SIZE_FACTOR){
                            Bar bar = bars[i];
                            trade = new Trade(bar.getDate(), bar.getHour(), TRADE_TYPE.BUY,
                                    bar.getClosePrice(), 0,
                                    Double.MIN_VALUE,
                                    bar.getClosePrice() + (getBarSize(bars[i]) * TAKE_PROFIT_FACTOR));
                        }
                    } else if(bars[i].getOpenPrice() < bars[i - 1].getClosePrice()){
                        double gap = bars[i - 1].getClosePrice() - bars[i].getOpenPrice();
                        double barSize = bars[i].getHighPrice() - bars[i].getLowPrice();

                        if(gap >= barSize * SIGNAL_BAR_SIZE_FACTOR){
                            Bar bar = bars[i];
                            trade = new Trade(bar.getDate(), bar.getHour(), TRADE_TYPE.SELL,
                                    bar.getClosePrice(), 0, Double.MIN_VALUE,
                                    bar.getClosePrice() - (getBarSize(bars[i]) * TAKE_PROFIT_FACTOR));
                        }
                    }
                }
                if(bars[i].getHour() == CLOSE_HOUR && trade != null) {
                    trade.setClosePrice(bars[i].getClosePrice());
                    report.addTrade(trade);
                    trade = null;
                }

                if(trade != null){
                    if(trade.getType() == TRADE_TYPE.SELL){
                        if (trade.getTakeProfit() >= bars[i].getLowPrice()){
                            trade.setClosePrice(trade.getTakeProfit());
                            report.addTrade(trade);
                            trade = null;
                        }
                    } else if (trade.getType() == TRADE_TYPE.BUY){
                        if (trade.getTakeProfit() <= bars[i].getHighPrice()){
                            trade.setClosePrice(trade.getTakeProfit());
                            report.addTrade(trade);
                            trade = null;
                        }
                    }
                }
            }
            if(trade != null){
                trade.setClosePrice(bars[bars.length - 1].getClosePrice());
                report.addTrade(trade);
            }
            resultReports.add(report);
        }

        File result = new File("D:\\strategy_result_4.html");
        HtmlResultBuilder resultBuilder = new HtmlResultBuilder("Simple stategy");
        resultBuilder.save(result, resultReports);

    }
    private static double getBarSize(Bar bar){
        double barSize = bar.getClosePrice() - bar.getOpenPrice();

        return (barSize < 0) ? (barSize * -1) : barSize;
    }

}
