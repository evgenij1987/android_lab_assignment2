package rwth.lab.android.mensaviewer.model;

import java.util.List;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class WeekPlan {
    private String mensa;
    private List<DayPlan> dayList;
    private String addtitives;
    private String priceNote;

    public List<DayPlan> getDayList() {
        return this.dayList;
    }

    public void setDayList(List<DayPlan> dayList) {
        this.dayList = dayList;
    }

    public String getAddtitives() {
        return this.addtitives;
    }

    public void setAddtitives(String addtitives) {
        this.addtitives = addtitives;
    }

    public String getPriceNote() {
        return this.priceNote;
    }

    public void setPriceNote(String priceNote) {
        this.priceNote = priceNote;
    }

    public String getMensa() {
        return this.mensa;
    }

    public void setMensa(String mensa) {
        this.mensa = mensa;
    }
}