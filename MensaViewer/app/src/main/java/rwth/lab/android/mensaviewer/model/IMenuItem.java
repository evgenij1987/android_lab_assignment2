package rwth.lab.android.mensaviewer.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 03.05.2015.
 */
public interface IMenuItem extends Serializable {

    /**
     * Method for getting a category (vegetarian, etc.) of a dish
     *
     * @return a dish category as a string
     */
    String getCategory();

    /**
     * Method for getting a name of a dish
     *
     * @return a dish name as a string
     */
    String getDish();

    /**
     * Method for getting a price of a dish
     *
     * @return a dish price as a string containing euro symbol
     */
    String getPrice();
}
