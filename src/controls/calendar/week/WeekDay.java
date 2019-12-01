/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.week;

import java.time.LocalDate;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Displays a single day of the {@link Week} on the {@link Calendar}.
 *
 * @author maxbrown
 */
public class WeekDay extends Control {

    private final Week week;

    private final LocalDate date;

    public WeekDay(Week week, LocalDate date) {
        this.week = week;
        this.date = date;

        String stylesheet = getClass().getResource("week-day.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    /**
     * Returns the value of {@link WeekDay#week}.
     *
     * @return The week.
     */
    public Week getWeek() {
        return week;
    }

    /**
     * Returns the value of {@link WeekDay#date}.
     *
     * @return The date.
     */
    public LocalDate getDate() {
        return date;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new WeekDaySkin(this);
    }
}
