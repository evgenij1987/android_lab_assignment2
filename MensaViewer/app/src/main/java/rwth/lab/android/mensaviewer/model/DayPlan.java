package rwth.lab.android.mensaviewer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class DayPlan implements Serializable {

    private String header;
    private String note;
    private List<Menu> menues;
    private List<Extra> extras;
    private boolean mensaOpen = true;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isMensaOpen() {
        return mensaOpen;
    }

    public void setMensaOpen(boolean mensaOpen) {
        this.mensaOpen = mensaOpen;
    }

    public List<Menu> getMenues() {
        return this.menues;
    }

    public List<IMenuItem> getMenuItems() {
        List<IMenuItem> menuItems = new ArrayList<IMenuItem>();
        menuItems.addAll(this.menues);
        menuItems.addAll(this.extras);
        return menuItems;
    }

    public void setMenues(List<Menu> menues) {
        this.menues = menues;
    }

    public List<Extra> getExtras() {
        return this.extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}