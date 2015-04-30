package com.lab.android.comsys.evgenijavstein.mensaviewer.model;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class Menu {
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String category;
    String menu;
    String price;

}
