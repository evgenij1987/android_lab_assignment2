package com.lab.android.comsys.evgenijavstein.mensaviewer.model;

import java.util.ArrayList;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class WeekPlan {

    private String mensa;
    private ArrayList<DayPlan> dayList;
    private String addtitives;
    private String priceNote;

    public ArrayList<DayPlan> getDayList() {
        return dayList;
    }

    public void setDayList(ArrayList<DayPlan> dayList) {
        this.dayList = dayList;
    }

    public String getAddtitives() {
        return addtitives;
    }

    public void setAddtitives(String addtitives) {
        this.addtitives = addtitives;
    }

    public String getPriceNote() {
        return priceNote;
    }

    public void setPriceNote(String priceNote) {
        this.priceNote = priceNote;
    }

    public String getMensa() {
        return mensa;
    }

    public void setMensa(String mensa) {
        this.mensa = mensa;
    }
}
