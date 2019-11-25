/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.calendar;

import dao.AppointmentDao;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import models.Appointment;
import utilities.DateUtils;

/**
 * FXML Controller class
 *
 * @author mab90
 */
public class CalendarViewController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label month;

    @FXML
    private Label year;

    @FXML
    private Label appointmentTitle;

    @FXML
    private Label appointmentType;

    @FXML
    private Label appointmentLocation;

    @FXML
    private Label appointmentDescription;

    @FXML
    private Label appointmentStart;

    @FXML
    private Label appointmentStop;

    @FXML
    private Label appointmentCustomer;

    @FXML
    private Label appointmentUrl;

    @FXML
    private Label appointmentContact;

    @FXML
    private GridPane calendarGrid;

    /**
     * The calendar.
     */
    private Calendar calendar;

    /**
     * The list of time slots on the calendar.
     */
    private final List<TimeSlot> timeSlots = new ArrayList<>();

    /**
     * The selected appointment.
     */
    private final ObjectProperty<Appointment> selectedAppointment = new SimpleObjectProperty();

    /**
     * Initializes the controller class.
     *
     * @param url - The {@link URL} for the file.
     * @param rb - The {@link ResourceBundle} for the file.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calendarGrid.getStyleClass().add("calendar-view");

        calendar = new Calendar("Appointments");

        AppointmentDao appointmentDao = new AppointmentDao();

        List<Optional<Appointment>> appointments = appointmentDao.findAll();

        appointments.forEach(appointment -> {
            appointment.ifPresent(calendar::addAppointment);
        });

        name.textProperty().bind(calendar.nameProperty());
        month.textProperty().bind(calendar.displayMonthProperty());
        year.textProperty().bind(calendar.displayYearProperty().asString());

        calendar.calendarTypeProperty().addListener((obs, oldValue, newValue) -> {
            updateCalendarGrid();
        });

        registerSelectedAppointmentListeners();

        setupCalendarGrid();
        updateCalendarGrid();
    }

    /**
     * Gets the property containing the selected {@link Appointment}.
     *
     * @return The selected appointment property.
     */
    public ObjectProperty<Appointment> selectedAppointmentProperty() {
        return selectedAppointment;
    }

    /**
     * Gets the selected {@link CalendarViewController#selectedAppointment}.
     *
     * @return The selected appointment.
     */
    public Appointment getSelectedAppointment() {
        return selectedAppointmentProperty().get();
    }

    /**
     * Sets the selected {@link CalendarViewController#selectedAppointment}.
     *
     * @param appointment The selected appointment.
     */
    public void setSelectedAppointment(Appointment appointment) {
        selectedAppointmentProperty().set(appointment);
    }

    @FXML
    private void onClickPrevious(ActionEvent event) {
        calendar.goBackward();
        updateCalendarGrid();
    }

    @FXML
    private void onClickNext(ActionEvent event) {
        calendar.goForward();
        updateCalendarGrid();
    }

    @FXML
    private void onSelectWeekView(ActionEvent event) {
        calendar.setCalendarType(Calendar.CalendarType.WEEKLY);
    }

    @FXML
    private void onSelectMonthView(ActionEvent event) {
        calendar.setCalendarType(Calendar.CalendarType.MONTHLY);
    }

    private void setupCalendarGrid() {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(5);

        calendarGrid.getRowConstraints().add(rowConstraints);

    }

    /**
     * Updates the {@link CalendarViewController#calendarGrid} using the
     * {@link CalendarViewController#calendar}.
     */
    private void updateCalendarGrid() {
        calendarGrid.getChildren().clear();
        setupCalendarGridHeader();
        setupCalendarGridContent();
    }

    /**
     * Sets up the header row for the
     * {@link CalendarViewController#calendarGrid}.
     */
    private void setupCalendarGridHeader() {
        // Get the name for each weekday
        List<String> weekDays = calendar.getDatesOfWeek().stream().map(date -> {
            String weekDay = date.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, DateUtils.DEFAULT_LOCALE);
            String day = Integer.toString(date.getDayOfMonth());

            return calendar.getCalendarType().equals(Calendar.CalendarType.WEEKLY) ? weekDay + " " + day : weekDay;
        }).collect(Collectors.toList());

        // Get the header cell node and add it to the grid
        for (int i = 0; i < weekDays.size(); i++) {
            Node node = getCalendarGridHeaderCell(weekDays.get(i));
            calendarGrid.add(node, i, 0);
        }
    }

    /**
     * Sets up the content rows for the
     * {@link CalendarViewController#calendarGrid}.
     */
    private void setupCalendarGridContent() {
        switch (calendar.getCalendarType()) {
            case WEEKLY: {
                getWeeklyCalendarView();
                break;
            }
            case MONTHLY: {
                getMonthlyCalendarView();
                break;
            }
        }
    }

    /**
     * Creates content for a {@link CalendarViewController#calendarGrid} header
     * cell.
     *
     * @param heading The text to display.
     * @return The content node.
     */
    private Node getCalendarGridHeaderCell(String heading) {
        // Create root container
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        // Create label for header
        Label label = new Label(heading);
        label.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 14));

        // Add label to container
        root.getChildren().add(label);

        // Return container
        return root;
    }

    /**
     * Gets the weekly view for the {@link CalendarViewController#calendarGrid}.
     */
    private void getWeeklyCalendarView() {
        timeSlots.clear();
        LocalDate date = calendar.getFirstDayOfWeek();

        for (int i = 0; i < 7; i++) {
            Node node = getWeeklyCalendarGridContentCell(date);
            calendarGrid.add(node, i, 1, 1, 6);
            date = date.plusDays(1);
        }
    }

    /**
     * Gets the monthly view for the
     * {@link CalendarViewController#calendarGrid}.
     */
    private void getMonthlyCalendarView() {
        // Get the first day of the first week of the current month
        LocalDate date = calendar.getFirstDayOfMonth();

        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                Node node = getMonthlyCalendarGridContentCell(date);
                calendarGrid.add(node, day, week + 1);
                date = date.plusDays(1);
            }
        }
    }

    /**
     * Gets the content for a {@link CalendarViewController#calendarGrid} weekly
     * grid cell.
     *
     * @param date The content date.
     * @return The content node.
     */
    private Node getWeeklyCalendarGridContentCell(LocalDate date) {
        // Create the content container
        VBox root = new VBox();
        root.getStyleClass().add("calendar-grid-content");
        root.setAlignment(Pos.CENTER);

        GridPane slotGrid = new GridPane();
        slotGrid.prefWidthProperty().bind(root.widthProperty());
        slotGrid.prefHeightProperty().bind(root.heightProperty());

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);

        slotGrid.getColumnConstraints().add(columnConstraints);

        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);

        int minutes = 15;
        int row = 0;
        for (LocalDateTime start = startOfDay; !start.isAfter(endOfDay); start = start.plusMinutes(minutes)) {
            ZonedDateTime slotDateTime = DateUtils.toZonedDateTime(start);
            TimeSlot slot = new TimeSlot(slotDateTime);
            List<Appointment> appointments = calendar.getAppointmentsFor(date);

            appointments.forEach(appointment -> {
                if (appointment.getInterval().contains(slotDateTime)) {
                    slot.setAppointment(appointment);
                }
            });

            timeSlots.add(slot);
            registerTimeSlotEventHandlers(slot);

            Node node = slot.getView();
            GridPane.setVgrow(node, Priority.ALWAYS);
            slotGrid.addRow(row, node);

            row++;
        }

        root.getChildren().add(slotGrid);

        return root;
    }

    /**
     * Gets the content for a {@link CalendarViewController#calendarGrid}
     * monthly grid cell.
     *
     * @param date The content date.
     * @return The content node.
     */
    private Node getMonthlyCalendarGridContentCell(LocalDate date) {
        // Create the content container
        VBox root = new VBox();
        root.getStyleClass().add("calendar-grid-content");
        root.setAlignment(Pos.CENTER);

        GridPane dayGrid = new GridPane();
        dayGrid.prefWidthProperty().bind(root.widthProperty());
        dayGrid.prefHeightProperty().bind(root.heightProperty());

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);

        dayGrid.getColumnConstraints().add(columnConstraints);

        Label label = new Label(Integer.toString(date.getDayOfMonth()));
        label.setPadding(new Insets(5));
        GridPane.setHalignment(label, HPos.LEFT);

        int row = 0;
        dayGrid.addRow(row++, label);

        List<Appointment> appointments = calendar.getAppointmentsFor(date);

        Collections.sort(appointments, (Appointment a1, Appointment a2) -> {
            return a1.compareTo(a2.getInterval());
        });

        for (Appointment appointment : appointments) {
            TimeSlot slot = new TimeSlot(appointment.getInterval().getZonedStartDateTime());
            slot.setAppointment(appointment);

            HBox slotView = slot.getView();
            slotView.getStyleClass().add("day-grid-content");
            slotView.setMinHeight(20);
            slotView.setPadding(new Insets(2, 0, 2, 0));

            Label time = new Label(appointment.getInterval().getStartTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            time.setPadding(new Insets(0, 2, 0, 2));
            time.setFont(Font.font(12));
            slotView.getChildren().add(time);

            registerTimeSlotEventHandlers(slot);

            timeSlots.add(slot);
            dayGrid.addRow(row++, slotView);
        }

        // Allow for scrolling the list of appointments
        ScrollPane scrollPane = new ScrollPane(dayGrid);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        root.getChildren().add(scrollPane);

        return root;
    }

    /**
     * Handles registration of selection and hover events for time slots.
     *
     * @param slot The slot to register.
     */
    private void registerTimeSlotEventHandlers(TimeSlot slot) {
        slot.getView().setOnMouseClicked(e -> {
            e.consume();

            if (slot.hasAppointment()) {
                slot.setSelected(true);

                Appointment slotAppt = slot.getAppointment();
                setSelectedAppointment(slotAppt);

                timeSlots.forEach(o -> {
                    o.setSelected(false);

                    if (o.hasAppointment()) {
                        Appointment otherAppt = o.getAppointment();

                        if (slotAppt.getId() == otherAppt.getId()) {
                            o.setSelected(true);
                        }
                    }
                });
            }
        });

        slot.getView().setOnMouseEntered(e -> {
            e.consume();

            if (slot.hasAppointment()) {
                slot.setHovered(true);

                Appointment slotAppt = slot.getAppointment();

                timeSlots.forEach(o -> {
                    o.setHovered(false);

                    if (o.hasAppointment()) {
                        Appointment otherAppt = o.getAppointment();

                        if (slotAppt.getId() == otherAppt.getId()) {
                            o.setHovered(true);
                        }
                    }
                });
            }
        });

        slot.getView().setOnMouseExited(e -> {
            e.consume();

            slot.setHovered(false);

            timeSlots.forEach(o -> {
                o.setHovered(false);
            });
        });
    }

    /**
     * Registers the selected appointment labels to listen to changes in the
     * {@link CalendarViewController#selectedAppointment}.
     */
    private void registerSelectedAppointmentListeners() {
        appointmentTitle.setText("");
        appointmentType.setText("");
        appointmentLocation.setText("");
        appointmentDescription.setText("");
        appointmentStart.setText("");
        appointmentStop.setText("");
        appointmentCustomer.setText("");
        appointmentUrl.setText("");
        appointmentContact.setText("");

        selectedAppointmentProperty().addListener((obs, oldAppt, appt) -> {
            if (appt != null) {
                appointmentTitle.setText(appt.getTitle());
                appointmentType.setText(appt.getType());
                appointmentLocation.setText(appt.getLocation());
                appointmentDescription.setText(appt.getDescription());
                appointmentStart.setText(appt.getInterval().getZonedStartDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy - HH:mm")));
                appointmentStop.setText(appt.getInterval().getZonedEndDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy - HH:mm")));
                appointmentCustomer.setText(appt.getCustomer().getName());
                appointmentUrl.setText(appt.getUrl());
                appointmentContact.setText(appt.getContact());
            }
        });
    }

}
