package org.projectTModel.entities;

import java.util.Date;

/**
 * User: Almaz
 * Date: 23.08.14
 * Time: 18:46
 */
public class Trade {
    private double openPrice;
    private double closePrice;
    private Time time;
    private Date date;
    private TRADE_TYPE type;
    private double stopLoss;
    private double takeProfit;

    public Trade(Date date, Time time, TRADE_TYPE type, double openPrice,
                 double closePrice, double stopLoss, double takeProfit) {
        this(date, time, type, openPrice, closePrice, stopLoss);
        this.takeProfit = takeProfit;
    }
    public Trade(Date date, Time time, TRADE_TYPE type, double openPrice, double closePrice, double stopLoss) {
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.time = time;
        this.type = type;
        this.date = date;
        this.stopLoss = stopLoss;
    }

    public double getResult(){
        if(type == TRADE_TYPE.BUY)
            return closePrice - openPrice;
        if(type == TRADE_TYPE.SELL)
            return openPrice - closePrice;

        return 0;
    }

    public double getTakeProfit() {
        return takeProfit;
    }
    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }
    public double getStopLoss() {
        return stopLoss;
    }
    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public TRADE_TYPE getType() {
        return type;
    }
    public void setType(TRADE_TYPE type) {
        this.type = type;
    }
    public double getClosePrice() {
        return closePrice;
    }
    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getOpenPrice() {
        return openPrice;
    }
    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }
}
