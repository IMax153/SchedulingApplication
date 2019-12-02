/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.appointment;

import application.SchedulingApplication;
import dao.AppointmentDao;
import java.time.YearMonth;
import java.util.Optional;
import javafx.util.Pair;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import utilities.DateUtils;

import static java.util.Objects.requireNonNull;

/**
 * The {@link Control} for displaying appointment data as a {@link BarChart} for
 * a specific {@link Year}.
 *
 * @author maxbrown
 */
public class AppointmentReport extends Control {

    private final AppointmentDao appointmentDao = new AppointmentDao();

    public AppointmentReport() {
        String stylesheet = getClass().getResource("appointment-report.css").toExternalForm();
        getStylesheets().add(stylesheet);

        refreshData();

        // Create an invalidation listener to keep our data up-to-date
        InvalidationListener listener = d -> refreshData();

        // Add the listener to our year and month properties
        yearMonth.addListener(listener);
    }

    private final ObjectProperty<YearMonth> yearMonth = new SimpleObjectProperty<>(this, "yearMonth", YearMonth.now(DateUtils.DEFAULT_ZONE_ID));

    /**
     * Returns the property containing the current {@link YearMonth} displayed
     * by the {@link AppointmentReport}.
     *
     * @return The year and month property.
     */
    public final ObjectProperty<YearMonth> yearMonthProperty() {
        return yearMonth;
    }

    /**
     * Returns the value of the {@link AppointmentReport#yearMonthProperty()}.
     *
     * @return The year and month to display.
     */
    public final YearMonth getYearMonth() {
        return yearMonthProperty().get();
    }

    /**
     * Sets the value of the {@link AppointmentReport#yearMonthProperty()}.
     *
     * @param yearMonth The year and month to set.
     */
    public final void setYearMonth(YearMonth yearMonth) {
        requireNonNull(yearMonth);
        yearMonthProperty().set(yearMonth);
    }

    /**
     * Move the {@link AppointmentReport} period forward by 1 month.
     */
    public final void goForward() {
        setYearMonth(getYearMonth().plusMonths(1));
    }

    /**
     * Move the {@link AppointmentReport} period to the current month.
     */
    public final void goCurrent() {
        setYearMonth(YearMonth.now(DateUtils.DEFAULT_ZONE_ID));
    }

    /**
     * Move the {@link AppointmentReport} period backward by 1 month.
     */
    public final void goBackward() {
        setYearMonth(getYearMonth().minusMonths(1));
    }

    private final ObservableList<Data<String, Number>> data = FXCollections.observableArrayList();

    /**
     * Returns the property containing the {@link ObservableList} of
     * {@link Data} for the {@link AppointmentReport}.
     *
     * @return The chart data property.
     */
    public final ObservableList<Data<String, Number>> getData() {
        return data;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AppointmentReportSkin(this);
    }

    private void refreshData() {
        getData().clear();

        // Functional programming allows for a more declarative mechanism for 
        // data manipulation. Similar to the method discussed in the Calendar class,
        // there are several consecutive manipulations necessary to convert from a
        // Optional<Pair<String, Integer>> to a Data<String, Number> and then add 
        // the converted data to our ObservableList<Data<String, Number>>. The list
        // of elements returned from the database is first converted to a stream to 
        // further simplify consecutive manipulations to the list. The list is then 
        // filtered to only include Optionals that are present, and then the list 
        // elements are extracted from their Optional container. The `createData`
        // method is then delegated to so that Pair<String, Integer> can be 
        // converted to Data<String, Number>. Using an imperative method of programming
        // to accomplish this task would result in extremely verbose code that would be
        // quite difficult to read and understand.
        appointmentDao.findTypesForUser(SchedulingApplication.USER, getYearMonth()).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::createData)
                .forEach(item -> getData().add(item));
    }

    private Data<String, Number> createData(Pair<String, Integer> pair) {
        return new Data<>(pair.getKey(), pair.getValue());
    }

}
