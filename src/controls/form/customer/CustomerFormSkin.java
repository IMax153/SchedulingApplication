/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.form.customer;

import application.SchedulingApplication;
import controls.icon.AccountIcon;
import controls.icon.BellRingIcon;
import controls.icon.CityIcon;
import controls.icon.DeskPhoneIcon;
import controls.icon.GlobeIcon;
import controls.icon.MailBoxIcon;
import java.time.Instant;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import models.City;
import models.Country;

/**
 * The {@link Skin} for the {@link CustomerForm}.
 *
 * @author maxbrown
 */
public class CustomerFormSkin extends SkinBase<CustomerForm> {

    private final CustomerForm form;

    private final GridPane grid;

    public CustomerFormSkin(CustomerForm form) {
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

        // Create the name label
        grid.add(createLabel("Name", new AccountIcon()), column, row++);

        // Create the active label
        grid.add(createLabel("Active", new BellRingIcon()), column, row++);

        // Create the address label
        grid.add(createLabel("Address", new MailBoxIcon()), column, row++);

        // Create the address 2 label
        grid.add(createLabel("Address 2", new MailBoxIcon()), column, row++);

        // Create the postal code label
        grid.add(createLabel("Postal Code", new MailBoxIcon()), column, row++);

        // Create the phone number label
        grid.add(createLabel("Phone Number", new DeskPhoneIcon()), column, row++);

        // Create the city label
        grid.add(createLabel("City", new CityIcon()), column, row++);

        // Create the country label
        grid.add(createLabel("Country", new GlobeIcon()), column, row++);

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

        // Create the name text field
        grid.add(createTextField("Enter name", form.nameProperty(), true), column, row++);

        // Create the active checkbox
        grid.add(createCheckBox("Is the customer active?", form.activeProperty()), column, row++);

        // Create the address text field
        grid.add(createTextField("Enter address", form.addressProperty()), column, row++);

        // Create the address 2 text field
        grid.add(createTextField("Enter additional address information", form.address2Property()), column, row++);

        // Create the postal code text field
        grid.add(createPostalCodeField("Enter postal code", form.postalCodeProperty()), column, row++);

        // Create the phone number text field
        grid.add(createPhoneNumberField("Enter phone number", form.phoneProperty()), column, row++);

        // Create the city select
        grid.add(createCitySelect(), column, row++);

        // Create the country select
        grid.add(createCountrySelect(), column, row++);
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

    private CheckBox createCheckBox(String label, BooleanProperty booleanProperty) {
        CheckBox checkbox = new CheckBox(label);

        // Bind the check box to the control
        checkbox.selectedProperty().bindBidirectional(booleanProperty);

        return checkbox;
    }

    private TextField createPostalCodeField(String placeholder, StringProperty postalCodeProperty) {
        TextField textField = new TextField();

        // Add placeholder for text field
        textField.setPromptText(placeholder);

        // Bind the text field to the control
        textField.textProperty().bindBidirectional(postalCodeProperty);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (!change.isContentChange()) {
                return change;
            }

            int maxlength = 5;

            if (change.getControlNewText().length() > maxlength || change.getText().matches("\\D+")) {
                return null;
            }

            return change;
        };

        textField.setTextFormatter(new TextFormatter(filter));

        return textField;
    }

    private TextField createPhoneNumberField(String placeholder, StringProperty phoneProperty) {
        /**
         * https://stackoverflow.com/questions/37751922/how-to-format-a-string-in-a-textfield-without-changing-its-value-with-javafx
         */
        TextField textField = new TextField();

        // Add placeholder for text field
        textField.setPromptText(placeholder);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (!change.isContentChange()) {
                return change;
            }

            int maxlength = 14;
            if (change.getControlText().indexOf('(') == -1) {
                maxlength = 10;
            }

            if (change.getControlNewText().length() > maxlength || change.getText().matches("\\D+")) {
                return null;
            }

            return change;
        };

        StringConverter<String> converter = new StringConverter<String>() {
            @Override
            public String toString(String committedText) {
                if (committedText == null) {
                    return textField.getText();
                }

                if (committedText.matches("(\\d{3}) \\d{3}-\\{4}")) {
                    return committedText;
                }

                if (committedText.length() == 10 && !committedText.matches("\\D+")) {
                    return String.format(
                            "(%s) %s-%s",
                            committedText.substring(0, 3),
                            committedText.substring(3, 6),
                            committedText.substring(6, 10)
                    );
                } else {
                    throw new IllegalStateException("Unexpected or incomplete phone number value: " + committedText);
                }
            }

            @Override
            public String fromString(String displayedText) {
                displayedText = displayedText.replaceAll("\\p{Punct}", "");
                displayedText = displayedText.replaceAll("\\p{Blank}", "");

                if (displayedText.length() != 10) {
                    return null;
                }

                return displayedText;
            }
        };

        TextFormatter<String> formatter = new TextFormatter<>(converter, "0000000000", filter);

        textField.setTextFormatter(formatter);
        textField.textProperty().bindBidirectional(phoneProperty);

        return textField;
    }

    private ComboBox createCountrySelect() {
        ComboBox<Country> select = new ComboBox<>();

        // Make the select editable
        select.setEditable(true);

        // Add placeholder for the select
        select.setPromptText("Select country");

        // Add the list of countries
        select.setItems(form.getCountries());

        // Bind the select to the control
        select.valueProperty().bindBidirectional(form.countryProperty());

        // Use a string converter to display the country names in the select
        // but return the country on selection
        select.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country != null ? country.getName() : null;
            }

            @Override
            public Country fromString(String name) {
                return select.getItems().stream().filter(c -> {
                    return c.getName().equals(name);
                }).findFirst().orElse(
                        new Country(
                                -1,
                                name,
                                SchedulingApplication.USER.getUserName(),
                                SchedulingApplication.USER.getUserName(),
                                Instant.now(),
                                Instant.now()
                        )
                );
            }
        });

        return select;
    }

    private ComboBox createCitySelect() {
        ComboBox<City> select = new ComboBox<>();

        // Make the select editable
        select.setEditable(true);

        // Add placeholder for the select
        select.setPromptText("Select city");

        // Add the list of cities
        select.setItems(form.getCities());

        // Bind the select to the control
        select.valueProperty().bindBidirectional(form.cityProperty());

        // Use a string converter to display the city names in the select
        // but return the city on selection
        select.setConverter(new StringConverter<City>() {
            @Override
            public String toString(City city) {
                return city != null ? city.getName() : null;
            }

            @Override
            public City fromString(String name) {
                return select.getItems().stream().filter(c -> {
                    return c.getName().equals(name);
                }).findFirst().orElse(
                        new City(
                                -1,
                                name,
                                SchedulingApplication.USER.getUserName(),
                                SchedulingApplication.USER.getUserName(),
                                Instant.now(),
                                Instant.now()
                        )
                );
            }
        });

        return select;
    }

    private BorderPane createLabel(String content) {
        return createLabel(content, null);
    }

    private BorderPane createLabel(String content, Node icon) {
        // Create the label container
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("form-label");

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

}
