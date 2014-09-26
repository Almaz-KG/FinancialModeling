package org.projectTModel.strategies.SimpleStrategyLarriVilyams;

import org.projectTModel.entities.StockHistory;
import org.projectTModel.loader.Loader;

import java.io.File;
import java.util.List;

/**
 * Created by almu0214 on 26.09.2014.
 *
 * Простая стратегия описанная в книге Ларри Вильямса
 *
 * Покупать если цена в понедельник открылась ниже минимума прошлой
 * пятницы. Закрывать в на закрытии рынка в пн.
 *
 */
public class StrategyTester {
    public static void main(String[] args) {
        /*
            File gold = new File("D:\\gold.csv");

            StockHistory history = Loader.loadHistory(gold);
            StrategyResult result = new StrategyAnalyzer().analyze(history);

            new ResultBuilder().printToConsole(result);
        */
        File gold = new File("D:\\ETF\\DAY");

        List<StockHistory> histories = Loader.load(gold);
        new ResultBuilder().printToConsole(new StrategyAnalyzer().analyzeHistories(histories));
    }
}
