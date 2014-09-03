package org.projectTModel.entities;

/**
 * User: Almaz
 * Date: 23.08.14
 * Time: 17:22
 */
public class StockHistory {
    private String name;
    private Bar[] bars;

    public StockHistory() {
        name = "";
        bars = new Bar[1];
    }

    public StockHistory(String name, Bar[] bars) {
        this.name = name;
        this.bars = bars;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Bar[] getBars() {
        return bars;
    }
    public void setBars(Bar[] bars) {
        this.bars = bars;
    }
}
