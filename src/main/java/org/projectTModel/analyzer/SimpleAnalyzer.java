package org.projectTModel.analyzer;

import org.projectTModel.entities.StrategyResultReport;
import org.projectTModel.entities.TRADE_TYPE;
import org.projectTModel.entities.Trade;

import java.util.List;

/**
 * User: Almaz
 * Date: 24.08.14
 * Time: 0:40
 */
public class SimpleAnalyzer {
    public SimpleAnalyzer(){}

    public static double getLargestLoss(List<StrategyResultReport> reports) {
        double result = reports.get(0).getOrders().get(0).getResult();
        for (StrategyResultReport report: reports){
            for(Trade tr : report.getOrders())
                if(tr.getResult() < result)
                    result = tr.getResult();
        }
        return result;
    }
    public static double getLargestProfit(List<StrategyResultReport> reports) {
        double result = reports.get(0).getOrders().get(0).getResult();
        for (StrategyResultReport report: reports){
            for(Trade tr : report.getOrders())
                if(tr.getResult() > result)
                    result = tr.getResult();
        }
        return result;
    }

    public static double getAverageProfit(List<StrategyResultReport> reports) {
        double profit = getGrossProfit(reports);
        int trades = getTradeCount(reports);
        return profit/trades;
    }
    public static double getAverageLoss(List<StrategyResultReport> reports) {
        double profit = getGrossLoss(reports);
        int trades = getTradeCount(reports);
        if(trades == 0)
            return 0;
        return (profit/trades) * -1;
    }

    public static int getLongPositions(List<StrategyResultReport> reports) {
        int result = 0;
        for (StrategyResultReport report: reports){
            for(Trade tr : report.getOrders())
                if(tr.getType() == TRADE_TYPE.BUY)
                    result++;
        }
        return result;
    }
    public static int getShortPositions(List<StrategyResultReport> reports) {
        int result = 0;
        for (StrategyResultReport report: reports){
            for(Trade tr : report.getOrders())
                if(tr.getType() == TRADE_TYPE.SELL)
                    result++;
        }
        return result;
    }

    public static int getTradeCount(List<StrategyResultReport> reports) {
        int result = 0;
        for (StrategyResultReport report: reports)
            result += report.getOrders().size();

        return result;
    }

    public static double getGrossLoss(List<StrategyResultReport> reports) {
        double result = 0;

        for (StrategyResultReport report: reports){
            for (Trade tr : report.getOrders())
                if(tr.getResult() < 0)
                    result -= tr.getResult();
        }
        return result;

    }
    public static double getGrossProfit(List<StrategyResultReport> reports) {
        double result = 0;

        for (StrategyResultReport report: reports){
            for (Trade tr : report.getOrders())
                if(tr.getResult() > 0)
                    result += tr.getResult();
        }
        return result;
    }

    public static double getProfitFactor(List<StrategyResultReport> reports) {
        return getGrossProfit(reports) / getGrossLoss(reports);
    }
    public static double getExpectedPayoff(List<StrategyResultReport> reports) {
        return  getTotalNet(reports) / getTradeCount(reports);
    }
    public static double getTotalNet(List<StrategyResultReport> reports) {
        return getGrossProfit(reports) - getGrossLoss(reports);
    }

    public static int getCountLossTrades(List<StrategyResultReport> reports) {
        int result = 0;
        for (StrategyResultReport report: reports){
            for (Trade tr: report.getOrders()){
                if(tr.getResult() < 0)
                    result++;
            }
        }
        return result;
    }
    public static int getCountProfitTrades(List<StrategyResultReport> reports) {
        int result = 0;
        for (StrategyResultReport report: reports){
            for (Trade tr: report.getOrders()){
                if(tr.getResult() > 0)
                    result++;
            }
        }
        return result;
    }
}
