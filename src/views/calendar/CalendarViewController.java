/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.calendar;

import dao.AppointmentDao;
import java.net.URL;
import java.time.*;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import models.*;
import utilities.DateUtils;

/**
 * FXML Controller class
 *
 * @author mab90
 */
public class CalendarViewController implements Initializable {

    /**
     * The values representing the type of view that the calendar should
     * display.
     */
    private enum CalendarViewType {
        WEEK,
        MONTH
    }

    /**
     * The type of view that the calendar should display.
     */
    private CalendarViewType calendarViewType;

    /**
     * The calendar being displayed by the CalendarView.
     */
    private Calendar calendar;

    /**
     * The {@link GridPane} used to display the days of the CalendarView.
     */
    private GridPane gridPane;

    /**
     * The pane used to display the CalendarView.
     */
    @FXML
    private Pane calendarPane;

    /**
     * Toggles the CalendarView to display by week.
     */
    @FXML
    private ToggleButton weekToggleButton;

    /**
     * Toggles the CalendarView to display by month.
     */
    @FXML
    private ToggleButton monthToggleButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.calendar = new Calendar("Appointments");
        this.calendarViewType = CalendarViewType.MONTH;

        AppointmentDao appointmentDao = new AppointmentDao();

        List<Optional<Appointment>> optAppts = appointmentDao.findAll();

        optAppts.forEach(optAppt -> {
            optAppt.ifPresent(a -> calendar.addAppointment(a));
        });

        ToggleGroup calendarViewToggle = new ToggleGroup();
        monthToggleButton.setToggleGroup(calendarViewToggle);
        weekToggleButton.setToggleGroup(calendarViewToggle);

        monthToggleButton.setSelected(true);

        monthToggleButton.setOnAction(e -> {
            this.calendarViewType = CalendarViewType.MONTH;
            updateView();
        });
        weekToggleButton.setOnAction(e -> {
            this.calendarViewType = CalendarViewType.WEEK;
            updateView();
        });

        updateView();
    }

    /**
     * Displays the CalendarView by month.
     */
    private void updateView() {
        setGrid();
        setGridHeader();
        setGridContent();
        calendarPane.getChildren().add(gridPane);
    }

    /**
     * Sets the CalendarView grid based upon the date stored in the
     * {@link CalendarViewController#calendar} and the {@link CalendarViewType}
     * stored in the {@link CalendarViewController#calendarViewType}.
     */
    private void setGrid() {
        // Remove the previous grid pane from the calendar
        calendarPane.getChildren().remove(gridPane);

        // Construct a new grid pane for the calendar
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setMinSize(0, 0);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gridPane.setPrefSize(calendarPane.getPrefWidth(), calendarPane.getPrefHeight());
        gridPane.setStyle("-fx-background-radius: 10; -fx-border-radius-bottom-left: 10;");

        // The column constraints do not change between month and week views
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100d / 7d);

        gridPane.getColumnConstraints().addAll(
                columnConstraints,
                columnConstraints,
                columnConstraints,
                columnConstraints,
                columnConstraints,
                columnConstraints,
                columnConstraints
        );

        // The header row also does not change between month and week views
        RowConstraints calendarHeaderRowConstraints = new RowConstraints();
        calendarHeaderRowConstraints.setPercentHeight(5);

        RowConstraints weekDayHeaderRowConstraints = new RowConstraints();
        weekDayHeaderRowConstraints.setPercentHeight(5);

        // Obtain the correct row constraints for the selected view
        switch (calendarViewType) {
            case WEEK: {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPercentHeight(100);
                rowConstraints.setValignment(VPos.TOP);

                gridPane.getRowConstraints().addAll(
                        calendarHeaderRowConstraints,
                        weekDayHeaderRowConstraints,
                        rowConstraints
                );
                break;
            }
            case MONTH: {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPercentHeight(90d / 6d);
                rowConstraints.setValignment(VPos.TOP);

                gridPane.getRowConstraints().addAll(
                        calendarHeaderRowConstraints,
                        weekDayHeaderRowConstraints,
                        rowConstraints,
                        rowConstraints,
                        rowConstraints,
                        rowConstraints,
                        rowConstraints,
                        rowConstraints
                );
                break;
            }
        }
    }

    /**
     * Sets the CalendarView grid based upon the date stored in the
     * {@link CalendarViewController#calendar}.
     */
    private void setGridHeader() {
        // Create the root header element
        HBox headerRoot = new HBox();
        headerRoot.setAlignment(Pos.CENTER);
        headerRoot.setPadding(new Insets(5));

        // Create the header content containers
        VBox labels = new VBox();
        labels.setAlignment(Pos.CENTER);

        VBox previousButton = new VBox(getPreviousButton());
        previousButton.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(previousButton, Priority.ALWAYS);

        VBox nextButton = new VBox(getNextButton());
        nextButton.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(nextButton, Priority.ALWAYS);

        // Get the calendar name
        Label calendarLabel = new Label(calendar.getCalendarName() + " Calendar");
        calendarLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // Get the month name
        Label monthLabel = new Label();
        monthLabel.textProperty().bind(calendar.monthProperty());
        monthLabel.setStyle("-fx-font-size: 14;");

        // Add the labels to their container
        labels.getChildren().addAll(calendarLabel, monthLabel);

        // Add the header elements to the root container
        headerRoot.getChildren().addAll(previousButton, labels, nextButton);

        // Ensure that the header spans the full row
        GridPane.setColumnSpan(headerRoot, 7);
        gridPane.add(headerRoot, 0, 0);

        // Get the first day of the first week of the month
        LocalDate date = DateUtils.getFirstDayOfWeek(DateUtils.getFirstDayOfMonth(calendar.getDate()));

        // Populate the CalendarView headers to be the days of the week
        for (int day = 0; day < 7; day++) {
            Label dayOfMonthLabel = new Label(date.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()));

            dayOfMonthLabel.setPadding(new Insets(2));

            GridPane.setHalignment(dayOfMonthLabel, HPos.CENTER);
            GridPane.setValignment(dayOfMonthLabel, VPos.CENTER);

            gridPane.add(dayOfMonthLabel, day, 1);
            date = date.plusDays(1);
        }
    }

    /**
     * Sets the CalendarView grid content based upon the date stored in the
     * {@link CalendarViewController#calendar} and the {@link CalendarViewType}
     * stored in the {@link CalendarViewController#calendarViewType}.
     */
    private void setGridContent() {
        switch (calendarViewType) {
            case WEEK: {
                // Get the first day of the current week
                LocalDate date = DateUtils.getFirstDayOfWeek(calendar.getDate());

                for (int day = 0; day < 7; day++) {
                    CalendarDayView calendarDayView = new CalendarDayView(date, calendar.getAppointmentsFor(date));
                    gridPane.add(calendarDayView.getView(), day, 2);
                    date = date.plusDays(1);
                }

                break;
            }
            case MONTH: {
                // Get the first day of the first week of the current month
                LocalDate date = DateUtils.getFirstDayOfWeek(DateUtils.getFirstDayOfMonth(calendar.getDate()));

                for (int week = 0; week < 6; week++) {
                    for (int day = 0; day < 7; day++) {
                        CalendarDayView calendarDayView = new CalendarDayView(date, calendar.getAppointmentsFor(date));
                        gridPane.add(calendarDayView.getView(), day, week + 2);
                        date = date.plusDays(1);
                    }
                }

                break;
            }
        }
    }

    /**
     * Creates the next button to move the CalendarView foward in time.
     *
     * @return The next button.
     */
    private Button getNextButton() {
        Button button = new Button("Next ");

        // Right arrow
        SVGPath svg = new SVGPath();

        svg.setContent("M26 20.006L40 32 26 44.006");
        svg.fillProperty().set(Color.TRANSPARENT);
        svg.strokeProperty().set(Paint.valueOf("#202020"));
        svg.strokeLineCapProperty().set(StrokeLineCap.ROUND);
        svg.strokeLineJoinProperty().set(StrokeLineJoin.ROUND);
        svg.strokeMiterLimitProperty().set(10);
        svg.strokeWidthProperty().set(2);
        svg.setScaleX(0.5f);
        svg.setScaleY(0.5f);

        button.setGraphic(svg);
        button.setContentDisplay(ContentDisplay.RIGHT);

        button.setOnAction(e -> {
            getNextCalendarView();
        });

        return button;
    }

    /**
     * Creates the previous button to move the CalendarView backward in time.
     *
     * @return The previous button.
     */
    private Button getPreviousButton() {
        Button button = new Button("Previous ");

        // Right arrow
        SVGPath svg = new SVGPath();

        svg.setContent("M26 20.006L40 32 26 44.006");
        svg.fillProperty().set(Color.TRANSPARENT);
        svg.strokeProperty().set(Paint.valueOf("#202020"));
        svg.strokeLineCapProperty().set(StrokeLineCap.ROUND);
        svg.strokeLineJoinProperty().set(StrokeLineJoin.ROUND);
        svg.strokeMiterLimitProperty().set(10);
        svg.strokeWidthProperty().set(2);
        svg.setScaleX(-0.5f);
        svg.setScaleY(0.5f);

        button.setGraphic(svg);
        button.setContentDisplay(ContentDisplay.LEFT);

        button.setOnAction(e -> {
            getPreviousCalendarView();
        });

        return button;
    }

    /**
     * Moves the CalendarView grid backward in time based upon the current
     * {@link CalendarViewType}.
     */
    private void getPreviousCalendarView() {
        switch (calendarViewType) {
            case WEEK:
                calendar.setDate(calendar.getDate().minusWeeks(1));
                updateView();
                break;
            case MONTH:
                calendar.setDate(calendar.getDate().minusMonths(1));
                updateView();
                break;
        }
    }

    /**
     * Moves the CalendarView grid forward in time based upon the current
     * {@link CalendarViewType}.
     */
    private void getNextCalendarView() {
        switch (calendarViewType) {
            case WEEK:
                calendar.setDate(calendar.getDate().plusWeeks(1));
                updateView();
                break;
            case MONTH:
                calendar.setDate(calendar.getDate().plusMonths(1));
                updateView();
                break;
        }
    }

//    private void registerDragHandlers(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
//        timeSlot.getView().setOnDragDetected(event -> {
//            mouseAnchor.set(timeSlot);
//            timeSlot.getView().startFullDrag();
//            timeSlots.forEach(slot
//                    -> slot.setSelected(slot == timeSlot));
//        });
//
//        timeSlot.getView().setOnMouseDragEntered(event -> {
//            TimeSlot startSlot = mouseAnchor.get();
//            timeSlots.forEach(slot
//                    -> slot.setSelected(isBetween(slot, startSlot, timeSlot)));
//        });
//
//        timeSlot.getView().setOnMouseReleased(event -> mouseAnchor.set(null));
//    }
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
    public static final class TimeSlot {

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
