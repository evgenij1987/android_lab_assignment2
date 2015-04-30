package com.lab.android.comsys.evgenijavstein.mensaviewer.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class DayPlan {


    private String header;
    private ArrayList<Menu> menues;
    private ArrayList<Extra> extras;

    public ArrayList<Menu> getMenues() {
        return menues;
    }

    public void setMenues(ArrayList<Menu> menues) {
        this.menues = menues;
    }

    public ArrayList<Extra> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<Extra> extras) {
        this.extras = extras;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
