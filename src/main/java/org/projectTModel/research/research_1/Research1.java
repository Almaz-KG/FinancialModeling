package org.projectTModel.research.research_1;

import org.projectTModel.entities.Bar;
import org.projectTModel.entities.StockHistory;
import org.projectTModel.loader.Loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Almaz
 * Date: 01.09.14
 * Time: 21:03
 *
 *  Вычислить средний размер бара за всю исторю котировок = AVG_SIZE
 *   AVG_SIZE_HL = Sum(|H-L|) / n
 *   AVG_SIZE_OC = Sum(|O-C|) / n
 *   AVG_SIZE = (AVG_SIZE_HL + AVG_SIZE_OC) / 2
 *
 *   Вычислить кол-во баров когда  |O-C| = |H-L| * 0.05 && CURR_SIZE > AVG_SIZE * 0.85
 *   Определить % случаев появления этих баров
 *
 *   2. Найти бары, у которых |O-C| = |H-L| * 0.05 && CURR_SIZE > AVG_SIZE * 0.85,
 *   проанализировать предыдущие и последующие бары
 *
 *
 */
public class Research1 {
    private static final double BAR_TARGET_SIZE_FACTOR = 0.05;

    public static void main(String[] args) throws IOException{
        File file = new File("P:\\Archive\\Gold");
        File out = new File("D:\\research1_result.html");
        List<StockHistory> histories = Loader.load(file);

        List<ResearchResult> researchResults = new ArrayList<>();

        for (StockHistory history: histories){
            researchResults.add(analyze(history));
        }

        HtmlResultBuilder.build(researchResults, out);
    }

    private static ResearchResult analyze(StockHistory history){
        ResearchResult researchResult = new ResearchResult();
        researchResult.setStockName(history.getName());
        researchResult.setAvgSize((getAvgSizeHL(history) + getAvgSizeOC(history)) / 2);
        researchResult.setBars(getResearchBars(history));
        return researchResult;
    }
    private static Bar[] getResearchBars(StockHistory history) {
        List<Bar> result = new ArrayList<>();

        for (Bar bar : history.getBars()){
          /*  double oc = Math.abs(bar.getOpenPrice() - bar.getClosePrice());
            double hl = Math.abs(bar.getHighPrice() - bar.getLowPrice());

            if(oc <= hl * BAR_TARGET_SIZE_FACTOR)
                result.add(bar);*/


            if(Math.abs(bar.getOpenPrice() - bar.getClosePrice()) < 0.5 &&
                    Math.abs(bar.getHighPrice() - bar.getLowPrice()) < 3)
                result.add(bar);
        }

        return result.toArray(new Bar[result.size()]);
    }

    private static double getAvgSizeOC(StockHistory history) {
        double sum = 0.0;

        for (Bar bar : history.getBars()){
            sum += Math.abs(bar.getOpenPrice() - bar.getClosePrice());
        }
        return sum / history.getBars().length;
    }
    private static double getAvgSizeHL(StockHistory history) {
        double sum = 0.0;

        for (Bar bar : history.getBars()){
            sum += Math.abs(bar.getHighPrice() - bar.getLowPrice());
        }
        return sum / history.getBars().length;
    }
}
