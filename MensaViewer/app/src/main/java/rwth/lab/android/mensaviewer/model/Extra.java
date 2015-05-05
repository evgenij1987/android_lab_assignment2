package rwth.lab.android.mensaviewer.model;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class Extra implements IMenuItem {
    private String category;
    private String extra;

    @Override
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getDish() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String getPrice() {
        return "";
    }
}