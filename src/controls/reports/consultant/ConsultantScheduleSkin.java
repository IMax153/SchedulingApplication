/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.consultant;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import models.Appointment;
import models.Customer;

/**
 * The {@link Skin} for the {@link ConsultantSchedule} control.
 *
 * @author maxbrown
 */
public class ConsultantScheduleSkin extends SkinBase<ConsultantSchedule> {

    public ConsultantScheduleSkin(ConsultantSchedule schedule) {
        super(schedule);

        // Create the root pane for the schedule
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("container");

        // Create the title for the schedule
        Label title = new Label("Consultant: " + schedule.getConsultant().getUserName());
        title.getStyleClass().add("title");

        // Set the label to the top of the pane
        pane.setTop(title);

        // Create the appointments table 
        TableView<Appointment> appointmentsTable = new TableView<>();
        appointmentsTable.getStyleClass().add("customer-table");
        appointmentsTable.setMinSize(0, 0);
        appointmentsTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        appointmentsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Add the consultant appointments to the table 
        appointmentsTable.setItems(schedule.getConsultantAppointments());

        // Create a column for the appointment title
        TableColumn<Appointment, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getTitle()));
        titleColumn.setMaxWidth(1f * Integer.MAX_VALUE * 16.666);

        // Create a column for the appointment type
        TableColumn<Appointment, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getType()));
        typeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 16.666);

        // Create a column for the appointment customer
        TableColumn<Appointment, Customer> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(item -> new ReadOnlyObjectWrapper<>(item.getValue().getCustomer()));
        customerColumn.setCellFactory(customer -> createCustomerCell());
        customerColumn.setMaxWidth(1f * Integer.MAX_VALUE * 16.666);

        // Create a column for the appointment location
        TableColumn<Appointment, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getLocation()));
        locationColumn.setMaxWidth(1f * Integer.MAX_VALUE * 16.666);

        // Create a column for the appointment start date and time
        TableColumn<Appointment, ZonedDateTime> startColumn = new TableColumn<>("Start");
        startColumn.setCellValueFactory(item -> new ReadOnlyObjectWrapper<>(item.getValue().getInterval().getZonedStartDateTime()));
        startColumn.setCellFactory(col -> createDateCell());
        startColumn.setMaxWidth(1f * Integer.MAX_VALUE * 16.666);

        // Create a column for the appointment end date and time
        TableColumn<Appointment, ZonedDateTime> endColumn = new TableColumn<>("End");
        endColumn.setCellValueFactory(item -> new ReadOnlyObjectWrapper<>(item.getValue().getInterval().getZonedEndDateTime()));
        endColumn.setCellFactory(col -> createDateCell());
        endColumn.setMaxWidth(1f * Integer.MAX_VALUE * 16.666);

        // Add the columns to the table
        appointmentsTable.getColumns().addAll(
                titleColumn,
                typeColumn,
                customerColumn,
                locationColumn,
                startColumn,
                endColumn
        );

        // Add the appointments table to the center of the pane
        pane.setCenter(appointmentsTable);

        // Add the pane to the view
        getChildren().add(pane);
    }

    private TableCell<Appointment, ZonedDateTime> createDateCell() {
        return new TableCell<Appointment, ZonedDateTime>() {
            @Override
            protected void updateItem(ZonedDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(String.format(
                            item.format(
                                    DateTimeFormatter.ofPattern("MM dd, yyyy hh:mm a", Locale.getDefault())
                            ))
                    );
                }
            }
        };
    }

    private TableCell<Appointment, Customer> createCustomerCell() {
        return new TableCell<Appointment, Customer>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        };
    }

}
