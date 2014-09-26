package org.projectTModel.strategies.SimpleStrategyLarriVilyams;

import org.projectTModel.entities.Bar;
import org.projectTModel.entities.StockHistory;
import org.projectTModel.entities.TRADE_TYPE;
import org.projectTModel.entities.Trade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by almu0214 on 26.09.2014.
 */
public class StrategyAnalyzer {
    private static final int MONDAY = 2;
    private static final int FRIDAY = 6;


    public List<StrategyResult> analyzeHistories(List<StockHistory> histories){
        List<StrategyResult> results = new ArrayList<>();

        for (StockHistory history: histories){
            results.add(analyze(history));
        }

        return results;
    }

    public StrategyResult analyze(StockHistory history){
        StrategyResult result = new StrategyResult();
        result.setHistory(history);

        Bar[] bars = history.getBars();

        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < bars.length; i++) {
            Bar bar = bars[i];
            calendar.setTime(bar.getDate());
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            // Открываем позицию на покупку если цена открытия в понедельник ниже цены закрытия в пятницу.
            // Закрываем позицию на закрытии дня открытия
            // Monday = 2;
            // Friday = 6;

            if(day == MONDAY && i - 1 >= 0){
                // Получить пятничный бар
                Bar friday = bars[i - 1];
                Calendar cl = Calendar.getInstance();
                cl.setTime(friday.getDate());
                int d2 = cl.get(Calendar.DAY_OF_WEEK);
                if(d2 == FRIDAY){


                    if(bar.getOpenPrice() < friday.getClosePrice() * 0.98){
                        result.addTrade(new Trade(bar.getDate(), bar.getTime(), TRADE_TYPE.BUY, bar.getOpenPrice(), bar.getClosePrice(), 0, 0));
                    }
                }
            }
        }

        return result;
    }
}
