/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.form.appointment;

import controls.icon.CategoryIcon;
import controls.icon.ClockIcon;
import controls.icon.ContactIcon;
import controls.icon.GuestsIcon;
import controls.icon.LinkIcon;
import controls.icon.LocationIcon;
import controls.icon.TextIcon;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import models.Customer;

/**
 * The {@link Skin} for the {@link AppoinmentForm}.
 *
 * @author maxbrown
 */
public class AppointmentFormSkin extends SkinBase<AppointmentForm> {

    private final AppointmentForm form;

    private final GridPane grid;

    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    public AppointmentFormSkin(AppointmentForm form) {
        super(form);

        this.form = form;

        // Create the grid to contain the form fields
        this.grid = new GridPane();
        grid.getStyleClass().add("container");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMinSize(0, 0);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Create the label column constraints
        ColumnConstraints labelColumnConstraints = new ColumnConstraints();
        labelColumnConstraints.setHgrow(Priority.NEVER);

        // Create the field column constraints
        ColumnConstraints fieldColumnConstraints = new ColumnConstraints();
        fieldColumnConstraints.setHalignment(HPos.LEFT);
        fieldColumnConstraints.setHgrow(Priority.ALWAYS);

        // Add column constraints to the grid
        grid.getColumnConstraints().addAll(labelColumnConstraints, fieldColumnConstraints);

        // Create form labels and fields
        createFormLabels();
        createFormFields();

        // Add the grid to visual tree
        getChildren().add(grid);
    }

    private void createFormLabels() {
        int column = 0;
        int row = 0;

        // Create the title label
        BorderPane titleLabel = createLabel("Title");
        titleLabel.getStyleClass().add("title");
        grid.add(titleLabel, column, row++);

        // Create the type label
        grid.add(createLabel("Type", new CategoryIcon()), column, row++);

        // Create the interval label
        grid.add(createLabel("Time", new ClockIcon()), column, row++);

        // Create the customer label
        grid.add(createLabel("Customer", new GuestsIcon()), column, row++);

        // Create the location label
        grid.add(createLabel("Location", new LocationIcon()), column, row++);

        // Create the description label
        grid.add(createLabel("Description", new TextIcon()), column, row++);

        // Create the contact label
        grid.add(createLabel("Contact", new ContactIcon()), column, row++);

        // Create the url label
        grid.add(createLabel("URL", new LinkIcon()), column, row++);

        // Create the error label
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error");
        errorLabel.setWrapText(true);
        GridPane.setHgrow(errorLabel, Priority.ALWAYS);
        GridPane.setHalignment(errorLabel, HPos.LEFT);

        // Bind the error label to the control
        errorLabel.textProperty().bind(form.errorProperty());

        grid.add(errorLabel, column + 1, row++, 2, 1);
    }

    private void createFormFields() {
        int column = 1;
        int row = 0;

        // Create the title field
        TextField titleField = createTextField("Add Title", form.titleProperty(), true);
        titleField.getStyleClass().add("title");
        grid.add(titleField, column, row++);

        // Create the type field
        TextField typeField = createTextField("Add appointment type", form.typeProperty());
        grid.add(typeField, column, row++);

        // Create the date and time pickers
        grid.add(createDateTimePicker(), column, row++);

        // Create the customer select
        grid.add(createCustomerSelect(), column, row++);

        // Create the location field
        TextField locationField = createTextField("Add location", form.locationProperty());
        grid.add(locationField, column, row++);

        // Create the description field
        TextField descriptionField = createTextField("Add description", form.descriptionProperty());
        grid.add(descriptionField, column, row++);

        // Create the contact field
        TextField contactField = createTextField("Add contact", form.contactProperty());
        grid.add(contactField, column, row++);

        // Create the url field
        TextField urlField = createTextField("Add url", form.urlProperty());
        grid.add(urlField, column, row++);
    }

    private BorderPane createLabel(String content) {
        return createLabel(content, null);
    }

    private BorderPane createLabel(String content, Node icon) {
        // Create the label container
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("label-container");

        // Create the label
        Label label = new Label(content);

        // Set the label to the right
        pane.setRight(label);

        // Set the label icon to the left if necessary
        if (icon != null) {
            pane.setLeft(icon);
            BorderPane.setMargin(icon, new Insets(0, 10, 0, 0));
        }

        return pane;
    }

    private TextField createTextField(String placeholder, StringProperty textProperty) {
        return createTextField(placeholder, textProperty, false);
    }

    private TextField createTextField(String placeholder, StringProperty textProperty, boolean requestFocus) {
        TextField textField = new TextField();

        // Add placeholder for text field
        textField.setPromptText(placeholder);

        // Bind the text field text to the control
        textField.textProperty().bindBidirectional(textProperty);

        // Focus the text field if necessary
        if (requestFocus) {
            Platform.runLater(() -> textField.requestFocus());
        }

        return textField;
    }

    private ComboBox createCustomerSelect() {
        ComboBox<Customer> select = new ComboBox<>();

        // Add placeholder for the select
        select.setPromptText("Add customer");

        // Add the customers to the combo box
        select.setItems(form.getCustomers());

        // Bind the selected value to the control
        select.valueProperty().bindBidirectional(form.customerProperty());

        // Use a string converter to display the customer names in the select
        // but return the customer on selection
        select.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer.getName();
            }

            @Override
            public Customer fromString(String name) {
                return select.getItems().stream().filter(c -> {
                    return c.getName().equals(name);
                }).findFirst().orElse(null);
            }
        });

        return select;
    }

    private HBox createDateTimePicker() {
        // Create the container for the date time picker
        HBox group = new HBox();

        // Create a new date picker
        DatePicker datePicker = new DatePicker();

        // Bind the date picker to the control
        datePicker.valueProperty().bindBidirectional(form.dateProperty());

        // Add the date picker and the start and end time selects to the container
        group.getChildren().addAll(
                datePicker,
                createTimeSelect("Start time", form.startTimeProperty()),
                createTimeSelect("End time", form.endTimeProperty())
        );

        return group;
    }

    private ComboBox createTimeSelect(String placeholder, ObjectProperty<LocalTime> timeProperty) {
        ComboBox<LocalTime> select = new ComboBox<>();

        // Restrict the time select to show only 5 rows at a time
        select.setVisibleRowCount(5);

        // Add placeholder for the select
        select.setPromptText(placeholder);

        // Get a reference to the start and end of business hours
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        // Add all times to the select in 15 minute increments
        for (LocalTime start = startTime; !start.isAfter(endTime); start = start.plusMinutes(15)) {
            select.getItems().add(start);
        }

        // Bind the selected value to the control
        select.valueProperty().bindBidirectional(timeProperty);

        // Use a string converter to display the times in the select
        // but return the local time object on selection
        select.setConverter(new StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime time) {
                return time.format(timeDTF);
            }

            @Override
            public LocalTime fromString(String time) {
                return select.getItems().stream().filter(t -> {
                    return t.equals(LocalTime.parse(time, timeDTF));
                }).findFirst().orElse(null);
            }
        });

        return select;
    }

}
