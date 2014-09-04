package org.projectTModel.entities;

/**
 * Created by almu0214 on 04.09.2014.
 */
public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        setHour(hour);
        setMinute(minute);
    }

    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public int getMinute() {
        return minute;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }


    @Override
    public String toString(){
        return hour +" : " + minute;
    }

}
