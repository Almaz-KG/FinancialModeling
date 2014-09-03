package org.projectTModel.strategies.strategy_1;

import org.projectTModel.entities.*;
import org.projectTModel.loader.Loader;
import org.projectTModel.result_builder.HtmlResultBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Almaz
 * Date: 23.08.14
 * Time: 17:19
 *
 * buy:
 *  Если цена открытия больше чем цена закрытия предыдущей торговой сессии
 *  (гэп вверх) - ожидаем  закрытия часового бара открытия: если данный бар
 *  закрылся в плюсе открываем длинную позицию. Если же цена открылась с
 *  гэпом вверх, но бар закрылся вниз - пропускаем сигнал.
 *  Позицию закрываем в конце текущей торговой сессии.
 *
 * sell:
 * Если цена открытия меньше чем цена закрытия предыдущей торговой сессии
 *  (гэп вниз) - ожидаем  закрытия часового бара открытия: если данный бар
 *  закрылся в минусе открываем короткую позицию. Если же цена открылась с
 *  гэпом вниз, но бар закрылся вверх - пропускаем сигнал.
 *  Позицию закрываем в конце текущей торговой сессии.
 *
 */
public class Strategy1_SellOnly {
    public static void main(String[] args) throws IOException {
        int openHour = 9;
        int closeHour = 16;

        File file = new File("P:\\Archive\\RU-Stocks");
        List<StockHistory> histories = Loader.load(file);
        List<StrategyResultReport> resultReports = new ArrayList<>();

        for (StockHistory history: histories){
            StrategyResultReport report = new StrategyResultReport(history.getName());

            Bar[] bars = history.getBars();
            report.setBarsLength(bars.length);

            Trade trade = null;
            for (int i = 1; i < bars.length; i++){
                if(bars[i].getHour() == openHour){
                    if(bars[i].getOpenPrice() > bars[i - 1].getClosePrice() &&
                            bars[i].getOpenPrice() < bars[i].getClosePrice()){
                        Bar bar = bars[i];
                        trade = new Trade(bar.getDate(), bar.getHour(), TRADE_TYPE.SELL,
                                bar.getClosePrice(), 0, bar.getOpenPrice());
                    }
                }
                if(bars[i].getHour() == closeHour && trade != null) {
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
