package org.projectTModel.research.price_action.pinbar_research;

import org.projectTModel.entities.Bar;
import org.projectTModel.entities.StockHistory;
import org.projectTModel.loader.Loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Almaz on 06.09.2014.
 *
 *  Качество пинбара
 *   1. Хвост пинбара четко выражает направление, т.е. хвост с одной стороны бара
 *      много-больше хвоста с другой строны
 *   2. Тело пинбара находится внутри диапазона предыдущего бара
 *   3. Тело пинбара должен быть маленьким
 *
 *  Открытие позиции
 *   1. Отложенный ордер:
 *   sell: чуть ниже Low пинбара, SL чуть выше High пинбара
 *   buy: чуть выше High пинбара, SL чуть ниже Low пинбара
 */
public class PinbarResearch {
    private static final double PINBAR_TILE_SIZE_FACTOR = 1.33;
    private static final double PINBAR_BODY_SIZE_FACTOR = 0.25;

    public static void main(String[] args) throws IOException{
        File source = new File(new File("").getAbsolutePath() + "\\src\\main\\resources\\Archive\\Forex\\Temp");
        File out = new File("D:\\Pinbar research result.html");

        List<StockHistory> histories = Loader.load(source);
        List<ResearchResult> researchResults = new ArrayList<>();

        for (StockHistory history : histories){

            List<Bar> pinBars = new ArrayList<>();
            Bar[] bars = history.getBars();
            for (int i = 1; i < bars.length; i++){
                Bar bar = bars[i];
                if(isPinbar(bar, bars[i - 1])){
                    pinBars.add(bar);
                }
            }

            researchResults.add(new ResearchResult(history.getName(), pinBars));
        }

        new HtmlResultBuilder().save(researchResults, out);
    }
    private static boolean isPinbar(Bar bar, Bar previousBar){
        double highTail = -1;
        double lowTail = -1;
        double barSize = -1;
        double range = bar.getHighPrice() - bar.getLowPrice();

        if(bar.isUpBar()){
            highTail = bar.getHighPrice() - bar.getClosePrice();
            lowTail = bar.getOpenPrice() - bar.getLowPrice();
            barSize = bar.getClosePrice() - bar.getOpenPrice();

        }
        if(bar.isDownBar()) {
            highTail = bar.getHighPrice() - bar.getOpenPrice();
            lowTail = bar.getLowPrice() - bar.getClosePrice();
            barSize = bar.getOpenPrice() - bar.getClosePrice();
        }

        /*
        *   1. Хвост пинбара четко выражает направление, т.е. хвост с одной стороны бара
        *      много-больше хвоста с другой строны
        *   2. Тело пинбара находится внутри диапазона предыдущего бара
        *   3. Тело пинбара должен быть маленьким
        */

        if((barSize < range * PINBAR_BODY_SIZE_FACTOR) && (highTail > lowTail * PINBAR_TILE_SIZE_FACTOR)){
            if(bar.isUpBar()){
                return bar.getOpenPrice() >= previousBar.getLowPrice() && bar.getClosePrice() <= previousBar.getHighPrice();
            } else if(bar.isDownBar()){
                return bar.getOpenPrice() <= previousBar.getHighPrice() && bar.getClosePrice() >= previousBar.getLowPrice();
            }
        }
        return false;
    }
}















