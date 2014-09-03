package org.projectTModel.strategies.strategy_2;

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
    private static final File RESEARCH_RESULT = new File("D:\\research_result_2.html");

    private static boolean isSignalBar(Bar bar, double signalFactor){
        double d = bar.getHighPrice() - bar.getLowPrice();
        double s = bar.getOpenPrice() - bar.getClosePrice();
        if(s < 0)
            s *= -1;

        return s >= d * signalFactor;
    }
    public static void main(String[] args) throws IOException {
        List<CustomContainer> triplets = new ArrayList<>();

        for (double sizeFactor = 0.0; sizeFactor <= 1; sizeFactor += 0.01){
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
                        if(bars[i].getOpenPrice() > bars[i - 1].getClosePrice() &&
                                bars[i].getOpenPrice() < bars[i].getClosePrice() &&
                                isSignalBar(bars[i], sizeFactor)){
                            Bar bar = bars[i];
                            trade = new Trade(bar.getDate(), bar.getHour(), TRADE_TYPE.BUY,
                                    bar.getOpenPrice() + (bar.getClosePrice() - bar.getOpenPrice()) / 2,
                                    0, Double.MIN_VALUE);
                        }

                        if(bars[i].getOpenPrice() < bars[i - 1].getClosePrice() &&
                                bars[i].getOpenPrice() > bars[i].getClosePrice() &&
                                isSignalBar(bars[i], sizeFactor)){
                            Bar bar = bars[i];
                            trade = new Trade(bar.getDate(), bar.getHour(), TRADE_TYPE.SELL,
                                    bar.getOpenPrice() - (bar.getOpenPrice() - bar.getClosePrice()) / 2, 0,
                                    Double.MAX_VALUE);
                        }
                    }
                    if(bars[i].getHour() == CLOSE_HOUR && trade != null) {
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
            triplets.add(new CustomContainer(sizeFactor, SimpleAnalyzer.getProfitFactor(resultReports),
                    SimpleAnalyzer.getTradeCount(resultReports),
                    SimpleAnalyzer.getTotalNet(resultReports)));
        }

        saveResult(triplets);
    }

    private static void saveResult(List<CustomContainer> customTriplets) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(RESEARCH_RESULT))){
            bw.write("<html><body><table>");
            bw.write("<tr align=center bgcolor=\"#C0C0C0\">" +
                    "<td colspan=4><b>Search profit factor<br></b></td></tr>");
            bw.write("<tr><td>SBSF</td><td>PF</td><td>Trades</td><td>Net</td></tr>");
            for (CustomContainer container : customTriplets){
                bw.write("<tr><td>" + String.format("%.2f",
                        container.getSignalBarSizeFactor())+ "</td>");
                bw.write("<td>" + String.format("%.2f",
                        container.getTotalProfitFactor())+ "</td>");
                bw.write("<td>" + container.getTrades()+ "</td>");
                bw.write("<td>" + String.format("%.2f", container.getTotalNet())+ "</td></tr>");
            }
            bw.write("</table></body></html>");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static class CustomContainer {
        private double signalBarSizeFactor;
        private double totalProfitFactor;
        private double totalNet;
        private int trades;

        private CustomContainer(double signalBarSizeFactor, double totalProfitFactor, int trades, double totalNet) {
            this.signalBarSizeFactor = signalBarSizeFactor;
            this.totalProfitFactor = totalProfitFactor;
            this.totalNet = totalNet;
            this.trades = trades;
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
        private double getSignalBarSizeFactor() {
            return signalBarSizeFactor;
        }
        private void setSignalBarSizeFactor(double signalBarSizeFactor) {
            this.signalBarSizeFactor = signalBarSizeFactor;
        }
        private double getTotalProfitFactor() {
            return totalProfitFactor;
        }
        private void setTotalProfitFactor(double totalProfitFactor) {
            this.totalProfitFactor = totalProfitFactor;
        }
    }

}
