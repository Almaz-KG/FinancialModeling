package org.projectTModel.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Almaz
 * Date: 23.08.14
 * Time: 17:25
 */
public class Bar {
    private double high;
    private double low;
    private double open;
    private double close;
    private int volume;
    private Date date;
    private int hour;

    public Bar(){}

    public Bar(Date date, int hour, double open, double high, double low, double close, int volume){
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.date = date;
        this.hour = hour;
    }

    public double getHighPrice() {
        return high;
    }
    public void setHighPrice(double high) {
        this.high = high;
    }
    public double getLowPrice() {
        return low;
    }
    public void setLowPrice(double low) {
        this.low = low;
    }
    public double getOpenPrice() {
        return open;
    }
    public void setOpenPrice(double open) {
        this.open = open;
    }
    public double getClosePrice() {
        return close;
    }
    public void setClosePrice(double close) {
        this.close = close;
    }
    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }

    public String toString(){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(this.date) + " " + hour + ":00 " + open + " " + high + " " + low + " " + close;
    }

}
