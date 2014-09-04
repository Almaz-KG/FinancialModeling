package org.projectTModel.strategies.strategy_4;

import org.projectTModel.analyzer.SimpleAnalyzer;
import org.projectTModel.entities.*;
import org.projectTModel.loader.Loader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchProfitFactor {
    private static final int OPEN_HOUR = 16;
    private static final int CLOSE_HOUR = 22;
    private static final File RESEARCH_RESULT = new File("D:\\research_result_4.html");

    public static void main(String[] args) throws IOException {
        List<CustomPair> pairs = new ArrayList<>();
        List<CustomContainer> containers = new ArrayList<>();

        for (double signalBarSizeFactor = 0.1; signalBarSizeFactor <= 1; signalBarSizeFactor += 0.1){
            for (double takeProfitFactor = 0.5; takeProfitFactor <= 10; takeProfitFactor += 0.5){
                File file = new File("P:\\Archive\\US-Stocks");
                List<StockHistory> histories = Loader.load(file);
                List<StrategyResultReport> resultReports = new ArrayList<>();

                for (StockHistory history: histories){
                    StrategyResultReport report = new StrategyResultReport(history.getName());

                    Bar[] bars = history.getBars();
                    report.setBarsLength(bars.length);

                    Trade trade = null;
                    for (int i = 1; i < bars.length; i++){
                        if(bars[i].getTime().getHour() == OPEN_HOUR){
                            if(bars[i].getOpenPrice() > bars[i - 1].getClosePrice()){
                                double gap = bars[i].getOpenPrice() - bars[i - 1].getClosePrice();
                                double barSize = bars[i].getHighPrice() - bars[i].getLowPrice();
                                if(gap >= barSize * signalBarSizeFactor){
                                    Bar bar = bars[i];
                                    trade = new Trade(bar.getDate(), bar.getTime(), TRADE_TYPE.BUY,
                                            bar.getClosePrice(), 0, Double.MIN_VALUE,
                                            bar.getClosePrice() + (getBarSize(bars[i]) * takeProfitFactor));
                                }
                            } else if(bars[i].getOpenPrice() < bars[i - 1].getClosePrice()){
                                double gap = bars[i - 1].getClosePrice() - bars[i].getOpenPrice();
                                double barSize = bars[i].getHighPrice() - bars[i].getLowPrice();

                                if(gap >= barSize * signalBarSizeFactor){
                                    Bar bar = bars[i];
                                    trade = new Trade(bar.getDate(), bar.getTime(), TRADE_TYPE.SELL,
                                            bar.getClosePrice(), 0, Double.MIN_VALUE,
                                            bar.getClosePrice() - (getBarSize(bars[i]) * takeProfitFactor));
                                }
                            }
                        }
                        if(bars[i].getTime().getHour() == CLOSE_HOUR && trade != null) {
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
                containers.add(new CustomContainer(takeProfitFactor, resultReports));
            }
            pairs.add(new CustomPair(containers, signalBarSizeFactor));
        }
        saveResult1(pairs);
    }
    private static void saveResult1(List<CustomPair> pairs) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(RESEARCH_RESULT))){
            bw.write("<html><body><table>");
            bw.write("<tr align=center bgcolor=\"#C0C0C0\">" +
                    "<td colspan=6><b>Search profit factor<br></b></td></tr>");
            bw.write("<tr>" +
                    "<td>SBSF</td>" +
                    "<td>TPF</td>" +
                    "<td>PF</td>" +
                    "<td>Trades</td>" +
                    "<td>Loss Trades</td>" +
                    "<td>Profit Trades</td>" +
                    "<td>Net</td>" +
                    "</tr>");

            for (CustomPair pair : pairs){
                for (CustomContainer container : pair.getContainers()){
                    bw.write("<tr><td>" + String.format("%.2f",
                            pair.getBarSize())+ "</td>");
                    bw.write("<td>" + String.format("%.2f",
                            container.getTakeProfitFactor())+ "</td>");
                    bw.write("<td>" + String.format("%.2f",
                            container.getTotalProfitFactor())+ "</td>");
                    bw.write("<td>" + container.getTrades()+ "</td>");
                    bw.write("<td>" + container.getLossTrades()+ "</td>");
                    bw.write("<td>" + container.getProfitTrades()+ "</td>");
                    bw.write("<td>" + String.format("%.2f", container.getTotalNet())+ "</td></tr>");
                }
            }
            bw.write("</table></body></html>");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void saveResult(List<CustomContainer> containers) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(RESEARCH_RESULT))){
            bw.write("<html><body><table>");
            bw.write("<tr align=center bgcolor=\"#C0C0C0\">" +
                    "<td colspan=6><b>Search profit factor<br></b></td></tr>");
            bw.write("<tr>" +
                        "<td>TPF</td>" +
                        "<td>PF</td>" +
                        "<td>Trades</td>" +
                        "<td>Loss Trades</td>" +
                        "<td>Profit Trades</td>" +
                        "<td>Net</td>" +
                    "</tr>");

            for (CustomContainer container : containers){
                bw.write("<tr><td>" + String.format("%.2f",
                        container.getTakeProfitFactor())+ "</td>");
                bw.write("<td>" + String.format("%.2f",
                        container.getTotalProfitFactor())+ "</td>");
                bw.write("<td>" + container.getTrades()+ "</td>");
                bw.write("<td>" + container.getLossTrades()+ "</td>");
                bw.write("<td>" + container.getProfitTrades()+ "</td>");
                bw.write("<td>" + String.format("%.2f", container.getTotalNet())+ "</td></tr>");
            }
            bw.write("</table></body></html>");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private static double getBarSize(Bar bar){
        double barSize = bar.getClosePrice() - bar.getOpenPrice();

        return (barSize < 0) ? (barSize * -1) : barSize;
    }
    private static class CustomContainer {
        private double takeProfitFactor;
        private double totalProfitFactor;
        private double totalNet;

        private int trades;
        private int lossTrades;
        private int profitTrades;

        private CustomContainer(double takeProfitFactor, List<StrategyResultReport> reports){
            this.takeProfitFactor = takeProfitFactor;
            this.totalProfitFactor = SimpleAnalyzer.getProfitFactor(reports);
            this.totalNet = SimpleAnalyzer.getTotalNet(reports);
            this.trades = SimpleAnalyzer.getTradeCount(reports);
            this.lossTrades = SimpleAnalyzer.getCountLossTrades(reports);
            this.profitTrades = SimpleAnalyzer.getCountProfitTrades(reports);
        }
        private CustomContainer(double takeProfitFactor, double totalProfitFactor, int trades, double totalNet) {
            this.takeProfitFactor = takeProfitFactor;
            this.totalProfitFactor = totalProfitFactor;
            this.totalNet = totalNet;
            this.trades = trades;
        }


        private int getLossTrades() {
            return lossTrades;
        }
        private void setLossTrades(int lossTrades) {
            this.lossTrades = lossTrades;
        }
        private int getProfitTrades() {
            return profitTrades;
        }
        private void setProfitTrades(int profitTrades) {
            this.profitTrades = profitTrades;
        }
        private double getTotalNet() {
            return totalNet;
        }
        private void setTotalNet(double totalNet) {
            this.totalNet = totalNet;
        }
        private int getTrades() {
            return trades;
        }
        private void setTrades(int trades) {
            this.trades = trades;
        }
        private double getTakeProfitFactor() {
            return takeProfitFactor;
        }
        private void setTakeProfitFactor(double takeProfitFactor) {
            this.takeProfitFactor = takeProfitFactor;
        }
        private double getTotalProfitFactor() {
            return totalProfitFactor;
        }
        private void setTotalProfitFactor(double totalProfitFactor) {
            this.totalProfitFactor = totalProfitFactor;
        }
    }
    private static class CustomPair{
        private List<CustomContainer> containers;
        private double barSize;

        private CustomPair(List<CustomContainer> containers, double barSize) {
            this.containers = containers;
            this.barSize = barSize;
        }

        private List<CustomContainer> getContainers() {
            return containers;
        }

        private void setContainers(List<CustomContainer> containers) {
            this.containers = containers;
        }

        private double getBarSize() {
            return barSize;
        }

        private void setBarSize(double barSize) {
            this.barSize = barSize;
        }
    }

}
