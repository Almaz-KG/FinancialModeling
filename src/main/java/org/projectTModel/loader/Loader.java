package org.projectTModel.loader;

import org.projectTModel.entities.Bar;
import org.projectTModel.entities.StockHistory;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * User: Almaz
 * Date: 23.08.14
 * Time: 17:22
 */
public class Loader {
    private static Scanner sc;
    public static List<StockHistory> load(File directory){
        if(directory.isDirectory()){
            File[] files = directory.listFiles();
            List<StockHistory> histories = new ArrayList<>();
            for (File f: files){
                if(f.isFile()){
                    StockHistory history = loadHistory(f);
                    if(history != null)
                        histories.add(history);
                }
            }
            return histories;
        }
        return  null;
    }

    private static StockHistory loadHistory(File f){
        try{
            sc = new Scanner(f);
            String name = f.getName();
            List<String> lines = new ArrayList<>();

            while (sc.hasNext()) {
                lines.add(sc.next());
            }
            return buildStockHistory(name, lines);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    private static StockHistory buildStockHistory(String name, List<String> lines) {
        StockHistory history = new StockHistory();
        List<Bar> bars = new ArrayList<>();
        history.setName(name);

        for (String line: lines){
            String[] data = line.split(",");

            if(data.length == 7){
                try{
                    Date date = new SimpleDateFormat("yyyy.MM.dd").parse(data[0]);
                    String[] sHour = data[1].split(":");
                    int hour =  Integer.parseInt(sHour[0]);
                    double openPrice = Double.parseDouble(data[2]);
                    double highPrice = Double.parseDouble(data[3]);
                    double lowPrice = Double.parseDouble(data[4]);
                    double closePrice = Double.parseDouble(data[5]);
                    int volume = Integer.parseInt(data[6]);

                    bars.add(new Bar(date, hour, openPrice,highPrice, lowPrice,closePrice,volume));
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }
        }
        history.setBars(bars.toArray(new Bar[bars.size()]));
        return history;
    }
}
