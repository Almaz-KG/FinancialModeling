package org.projectTModel.strategies.SimpleStrategyLarriVilyams;

import org.projectTModel.entities.Trade;

import java.util.List;

/**
 * Created by almu0214 on 26.09.2014.
 */
public class ResultBuilder {
    public void printToConsole(StrategyResult result){
        System.out.println(result.getHistory().getName() + " trade count: " + result.getTrades().size());

        double sum = 0.0;
        for (int i = 0; i < result.getTrades().size(); i++) {
            Trade trade = result.getTrades().get(i);
            sum += trade.getResult();
        }

        System.out.println("Total: " + String.format("%.2f", sum));
    }

    public void printToConsole(List<StrategyResult> results){
        for (StrategyResult result : results)
            printToConsole(result);
    }
}
