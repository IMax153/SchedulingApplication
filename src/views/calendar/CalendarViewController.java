/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.calendar;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author mab90
 */
public class CalendarViewController implements Initializable {

    private final LocalTime START_OF_DAY = LocalTime.of(0, 0);

    private final LocalTime END_OF_DAY = LocalTime.of(23, 59);

    private final Duration TIME_SLOT_DURATION = Duration.ofMinutes(15);

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    @FXML
    private ScrollPane gridViewScroller;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GridPane calendarGridView = new GridPane();

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            int timeSlotIndex = 1;

            for (LocalDateTime startTime = date.atTime(START_OF_DAY);
                    !startTime.isAfter(date.atTime(END_OF_DAY));
                    startTime = startTime.plus(TIME_SLOT_DURATION)) {
                TimeSlot timeSlot = new TimeSlot(startTime, TIME_SLOT_DURATION);

                timeSlots.add(timeSlot);

                calendarGridView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), timeSlotIndex);

                registerDragHandlers(timeSlot, mouseAnchor);

                timeSlotIndex++;
            }
        }

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");

        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            GridPane.setHalignment(label, HPos.CENTER);
            calendarGridView.add(label, date.getDayOfWeek().getValue(), 0);
        }

        int timeSlotIndex = 1;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (LocalDateTime startTime = today.atTime(START_OF_DAY);
                !startTime.isAfter(today.atTime(END_OF_DAY));
                startTime = startTime.plus(TIME_SLOT_DURATION)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            GridPane.setHalignment(label, HPos.RIGHT);
            calendarGridView.add(label, 0, timeSlotIndex);
            timeSlotIndex++;
        }

        gridViewScroller.setContent(calendarGridView);
    }

    private void registerDragHandlers(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
        timeSlot.getView().setOnDragDetected(event -> {
            mouseAnchor.set(timeSlot);
            timeSlot.getView().startFullDrag();
            timeSlots.forEach(slot
                    -> slot.setSelected(slot == timeSlot));
        });

        timeSlot.getView().setOnMouseDragEntered(event -> {
            TimeSlot startSlot = mouseAnchor.get();
            timeSlots.forEach(slot
                    -> slot.setSelected(isBetween(slot, startSlot, timeSlot)));
        });

        timeSlot.getView().setOnMouseReleased(event -> mouseAnchor.set(null));
    }

    private boolean isBetween(TimeSlot testSlot, TimeSlot startSlot, TimeSlot endSlot) {

        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek())
                * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek()) >= 0;

        boolean timesBetween = testSlot.getStartTime().compareTo(startSlot.getStartTime())
                * endSlot.getStartTime().compareTo(testSlot.getStartTime()) >= 0;

        return daysBetween && timesBetween;
    }

    /**
     * Represents a time slot on the calendar within the application.
     *
     * @author mab90
     */
    public static class TimeSlot {

        /**
         * The start time of the time slot.
         */
        private final LocalDateTime start;

        /**
         * The duration of the time slot.
         */
        private final Duration duration;

        /**
         * The GUI representation of the time slot.
         */
        private final Region view;

        /**
         * Whether or not the time slot is currently selected.
         */
        private final BooleanProperty selected = new SimpleBooleanProperty();

        /**
         * The CSS class that is updated whenever the time slot is selected or
         * deselected.
         */
        private final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;
            this.view = new Region();

            view.setMinSize(80, 20);
            view.getStyleClass().add("time-slot");
            view.getStylesheets().add(getClass().getResource("/views/calendar/calendar-view.css").toExternalForm());

            selectedProperty().addListener((o, wasSelected, isSelected)
                    -> view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected)
            );
        }

        /**
         * Gets the start date of the time slot as a {@link LocalDateTime}.
         *
         * @return The start date of the time slot as a {@link LocalDateTime}.
         */
        public LocalDateTime getStart() {
            return start;
        }

        /**
         * Gets the start date of the time slot as a {@link LocalTime};
         *
         * @return The start date of the time slot as a {@link LocalTime}.
         */
        public LocalTime getStartTime() {
            return start.toLocalTime();
        }

        /**
         * Gets the {@link DayOfWeek} that the time slot starts on.
         *
         * @return The {@link DayOfWeek} that the time slot starts on.
         */
        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        /**
         * Gets the {@link Duration} of the time slot.
         *
         * @return The {@link Duration} of the time slot.
         */
        public Duration getDuration() {
            return duration;
        }

        /**
         * Gets the {@link Region} GUI representation of the time slot.
         *
         * @return The {@link Region} GUI representation of the time slot.
         */
        public Region getView() {
            return view;
        }

        /**
         * Gets the selected property of the time slot.
         *
         * @return The selected property of the time slot.
         */
        public BooleanProperty selectedProperty() {
            return selected;
        }

        /**
         * Gets whether or not the time slot is currently selected.
         *
         * @return True if the time slot is currently selected, otherwise false.
         */
        public boolean isSelected() {
            return selectedProperty().get();
        }

        /**
         * Sets the selected property based upon whether or not the time slot is
         * currently selected.
         *
         * @param selected True if the time slot is currently selected,
         * otherwise false.
         */
        public void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

    }

}
