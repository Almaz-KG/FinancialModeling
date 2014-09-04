package org.projectTModel.result_builder;

import org.projectTModel.analyzer.SimpleAnalyzer;
import org.projectTModel.entities.StrategyResultReport;
import org.projectTModel.entities.Trade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * User: Almaz
 * Date: 23.08.14
 * Time: 19:22
 */
public class HtmlResultBuilder {
    private String strategyName;
    private StringBuffer buffer;

    public HtmlResultBuilder(String strategyName){
        this.strategyName = strategyName;
        this.buffer = new StringBuffer();
    }

    public void save(File result, List<StrategyResultReport> reports) throws IOException {
        if(!result.exists()){
            result.createNewFile();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(result))){
            printHeader(bw);
            printBody(bw);
            for (StrategyResultReport resultReport: reports){
                printResult(resultReport, bw);
            }
            printFinalResult(reports, bw);
            bw.flush();
        }
    }

    private void printResult(StrategyResultReport report, BufferedWriter bw) throws IOException{
        buffer.delete(0, buffer.length());
        buffer.append("<tr align=left><td colspan=9><b>"+ report.getStockName() +"</b></td></tr>");
        List<Trade> trades = report.getOrders();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (Trade tr: trades){
            buffer.append("<tr align=center>\n" +
                    "<td colspan=2>"+formatter.format(tr.getDate())+"</td>" +
                    "<td>"+tr.getTime()+":00</td>" +
                    "<td>"+String.format("%.2f", tr.getOpenPrice()) +"</td>" +
                    "<td>"+String.format("%.2f", tr.getClosePrice())+"</td>" +
                    "<td>"+String.format("%.2f", tr.getResult())+"</td>" +
                    "</tr>");
        }
        buffer.append("<tr align=center> "+
                "<td colspan=2><b>Trades: </b></td>" +
                "<td>" + report.getOrders().size()+" </td>"+
                "<td colspan=2><b>Total profit: </b></td>" +
                "<td>" + String.format("%.2f", report.getProfitResult())+ "</td>");

        bw.write(buffer.toString());
    }
    private void printFinalResult(List<StrategyResultReport> reports, BufferedWriter bw) throws IOException {
        buffer.append("<tr align=center bgcolor=\"#C0C0C0\"><td colspan=8><b>Detailed result</b></td></tr>");

        double grossProfit = SimpleAnalyzer.getGrossProfit(reports);
        double grossLoss = SimpleAnalyzer.getGrossLoss(reports);
        double totalNet = SimpleAnalyzer.getTotalNet(reports);
        buffer.append("<tr align=center> "+
                "<td align=left><b>Gross profit: </b></td>" +
                "<td>" + String.format("%.2f", grossProfit) +" </td>"+
                "<td align=left><b>Gross loss: </b></td>" +
                "<td>" + String.format("%.2f", grossLoss)+ "</td>"+
                "<td><b>Total net: </b></td>" +
                "<td>" + String.format("%.2f", totalNet)+ "</td></tr>");

        int totalTrades = SimpleAnalyzer.getTradeCount(reports);
        double profitFactor = SimpleAnalyzer.getProfitFactor(reports);

        double expectedPayoff = SimpleAnalyzer.getExpectedPayoff(reports);
        buffer.append("<tr align=center> "+
                "<td align=left><b>Profit factor: </b></td>" +
                "<td>" + String.format("%.2f", profitFactor) +" </td>"+
                "<td align=center><b>Expected payoff: </b></td>" +
                "<td>" + String.format("%.2f", expectedPayoff)+ "</td></tr>");

        int longPosition = SimpleAnalyzer.getLongPositions(reports);
        int shortPosition = SimpleAnalyzer.getShortPositions(reports);
        buffer.append("<tr align=center> "+
                "<td><b>Total trades: </b></td>" +
                "<td>" + totalTrades +" </td>"+
                "<td align=left><b>Short positions: </b></td>" +
                "<td>" + shortPosition+ "</td>"+
                "<td align=left><b>Long positions: </b></td>" +
                "<td>" + longPosition + "</td></tr>");

        int countProfitTrades = SimpleAnalyzer.getCountProfitTrades(reports);
        int countLossTrades = SimpleAnalyzer.getCountLossTrades(reports);
        buffer.append("<tr align=center> "+
                "<td colspan=2></td>"+
                "<td align=left><b>Profit trades: </b></td>" +
                "<td>" + countProfitTrades+ "</td>"+
                "<td align=left><b>Loss trades: </b></td>" +
                "<td>" + countLossTrades + "</td></tr>");

        double largestProfit = SimpleAnalyzer.getLargestProfit(reports);
        double largestLoss = SimpleAnalyzer.getLargestLoss(reports);
        buffer.append("<tr align=center> "+
                "<td align=left><b>Largest</b></td>" +
                "<td></td>"+
                "<td align=left><b>Profit trade: </b></td>" +
                "<td>" + String.format("%.2f", largestProfit)+ "</td>"+
                "<td align=left><b>Loss trade: </b></td>" +
                "<td>" + String.format("%.2f", largestLoss) + "</td></tr>");

        double averageProfit = SimpleAnalyzer.getAverageProfit(reports);
        double averageLoss = SimpleAnalyzer.getAverageLoss(reports);
        buffer.append("<tr align=center> "+
                "<td align=left><b>Average</b></td>" +
                "<td></td>"+
                "<td align=left><b>Profit trade: </b></td>" +
                "<td>" + String.format("%.2f", averageProfit)+ "</td>"+
                "<td align=left><b>Loss trade: </b></td>" +
                "<td>" + String.format("%.2f", averageLoss) + "</td></tr>");
        bw.write(buffer.toString());
    }

    private void printBody(BufferedWriter bw) throws IOException{
        buffer.append("<body topmargin=1 marginheight=1>" +
                "<div align=center>" +
                "<div style=\"font: 20pt Times New Roman\"><b>" + strategyName + "</b></div>");
        buffer.append("<table cellspacing=1 cellpadding=3 border=0>" +
                "<tr align=left>" +
                "<td colspan=2><b>Account: 00</b></td>" +
                "<td colspan=7><b>Name: Murzabekov Almaz</b></td>\n" +
                "<tr align=center bgcolor=\"#C0C0C0\">\n" +
                "<td colspan=2>Date</td>" +
                "<td>Hour</td>" +
                "<td>Open Price</td>" +
                "<td>Close Price</td>" +
                "<td>Profit</td>" +
                "</tr>");
        bw.write(buffer.toString());
    }
    private void printHeader(BufferedWriter bw) throws IOException{
        buffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
        buffer.append("<html>\n\t<head>");
        buffer.append("<title>Statement: " + strategyName + "</title>");
        buffer.append("<style type=\"text/css\" media=\"screen\">\n" +
                "    <!--\n" +
                "    td { font: 8pt Tahoma,Arial; }\n" +
                "    //-->\n" +
                "    </style>\n" +
                "    <style type=\"text/css\" media=\"print\">\n" +
                "    <!--\n" +
                "    td { font: 7pt Tahoma,Arial; }\n" +
                "    //-->\n" +
                "    </style>\n" +
                "    <style type=\"text/css\">\n" +
                "    <!--\n" +
                "    .msdate { mso-number-format:\"General Date\"; }\n" +
                "    .mspt   { mso-number-format:\\#\\,\\#\\#0\\.00;  }\n" +
                "    //-->\n" +
                "    </style>");
        buffer.append("</head>");
        bw.write(buffer.toString());
    }
}
