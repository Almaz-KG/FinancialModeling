package org.projectTModel.research.price_action.pinbar_research;

import org.projectTModel.entities.Bar;

import java.util.List;

/**
 * Created by Almaz on 06.09.2014.
 */
public class ResearchResult {
    private String stockName;
    private List<Bar> pinBars;

    public ResearchResult(String stockName, List<Bar> pinBars) {
        this.stockName = stockName;
        this.pinBars = pinBars;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public List<Bar> getPinBars() {
        return pinBars;
    }

    public void setPinBars(List<Bar> pinBars) {
        this.pinBars = pinBars;
    }
}
