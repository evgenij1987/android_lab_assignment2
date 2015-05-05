package rwth.lab.android.mensaviewer.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class MensaListItem implements Serializable {
    private final String mensaName;
    private final String mensaURL;

    public MensaListItem(String mensaName, String mensaURL) {
        this.mensaName = mensaName;
        this.mensaURL = mensaURL;
    }

    public String getMensaName() {
        return this.mensaName;
    }

    public String getMensaURL() {
        return this.mensaURL;
    }
}
