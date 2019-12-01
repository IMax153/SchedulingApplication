/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.month;

import controls.calendar.Calendar;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Displays the {@link Calendar} as a full month.
 *
 * @author maxbrown
 */
public class Month extends Control {

    private final Calendar calendar;

    public Month(Calendar calendar) {
        this.calendar = calendar;

        String stylesheet = getClass().getResource("month.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    /**
     * Returns the parent {@link Calendar} for the {@link Month}.
     *
     * @return The parent calendar.
     */
    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MonthSkin(this);
    }

}
