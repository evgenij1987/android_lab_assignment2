package rwth.lab.android.mensaviewer.model;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class Menu implements IMenuItem {
    private String category;
    private String menu;
    private String price;

    @Override
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getDish() {
        return this.menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @Override
    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
