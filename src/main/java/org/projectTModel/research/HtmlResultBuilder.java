package org.projectTModel.research;

import org.projectTModel.entities.Bar;
import org.projectTModel.entities.Trade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class HtmlResultBuilder {
    public static void build(List<ResearchResult> resultList, File output) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(output))){
            printHeader(bw);
            printBody(bw);
            for (ResearchResult resultReport: resultList){
                printResult(resultReport, bw);
            }
            printFooter(bw);
            bw.flush();
        }
    }

    private static void printHeader(BufferedWriter bw) throws IOException{
        bw.write("<html><head><title>");
        bw.write("Research result report");
        bw.write("</title></head>");
    }
    private static void printBody(BufferedWriter bw) throws IOException{
        bw.write("<body topmargin=1 marginheight=1>" +
                "<div align=center>" +
                "<div style=\"font: 20pt Times New Roman\"><b>"
                + "<h2>Research result report</h2>"+
                "</b></div>");
        bw.write("<table cellspacing=1 cellpadding=3 border=0>" +
                "<tr align=left bgcolor=\"#C0C0C0\">" +
                "<td colspan=2><b>Account: 00</b></td>" +
                "<td colspan=7><b>Name: Murzabekov Almaz</b></td></tr>" +
                "<tr align=center bgcolor=\"#C0C0C0\">" +
                "<td colspan=2>Date</td>" +
                "<td>Hour</td>" +
                "<td>Open Price</td>" +
                "<td>Close Price</td>" +
                "<td>High Price</td>" +
                "<td>Low Price</td>" +
                "</tr>");

    }
    private static void printResult(ResearchResult result, BufferedWriter bw) throws IOException{
        bw.write("<tr align=left><td colspan=9><b>"+ result.getStockName() +"</b></td></tr>");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Bar tr: result.getBars()){
            bw.write("<tr align=center>\n" +
                "<td colspan=2>"+formatter.format(tr.getDate())+"</td>" +
                "<td>"+tr.getTime().toString()+"</td>" +
                "<td>"+String.format("%.2f", tr.getOpenPrice()) +"</td>" +
                "<td>"+String.format("%.2f", tr.getClosePrice())+"</td>" +
                "<td>"+String.format("%.2f", tr.getHighPrice())+"</td>" +
                "<td>"+String.format("%.2f", tr.getLowPrice())+"</td>" +
                "</tr>");
        }
    }
    private static void printFooter(BufferedWriter bw) throws IOException{

        bw.write("</table></body></html>");
    }
}
