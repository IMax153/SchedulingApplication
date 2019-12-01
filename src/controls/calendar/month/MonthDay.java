/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.month;

import java.time.LocalDate;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Displays a single day of the {@link Month} on the {@link Calendar}.
 *
 * @author maxbrown
 */
public class MonthDay extends Control {

    private final Month month;

    private final LocalDate date;

    public MonthDay(Month month, LocalDate date) {
        this.month = month;
        this.date = date;

        String stylesheet = getClass().getResource("month-day.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    /**
     * Returns the parent {@link Month} for the {@link MonthDay}.
     *
     * @return The parent month.
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Returns the {@link LocalDate} for the {@link MonthDay}.
     *
     * @return The date.
     */
    public LocalDate getDate() {
        return date;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MonthDaySkin(this);
    }
}
