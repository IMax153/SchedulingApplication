/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.form.login;

import controls.icon.AccountIcon;
import controls.icon.LockIcon;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * The {@link Skin} for the {@link LoginForm}.
 *
 * @author maxbrown
 */
public class LoginFormSkin extends SkinBase<LoginForm> {

    private final LoginForm form;

    private final GridPane grid;

    public LoginFormSkin(LoginForm form) {
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

        // Create the form title
        Label title = new Label(form.getMessages().getString("title"));
        title.getStyleClass().add("title");

        // Create form labels and fields
        createFormLabels();
        createFormFields();

        // Create the login pane
        BorderPane pane = new BorderPane();

        // Add the label and grid to the pane;
        pane.setTop(title);
        pane.setCenter(grid);

        // Add the pane to visual tree
        getChildren().add(pane);
    }

    private void createFormLabels() {
        int column = 0;
        int row = 0;

        // Create the userName label
        grid.add(createLabel(form.getMessages().getString("username"), new AccountIcon()), column, row++);

        // Create the password label
        grid.add(createLabel(form.getMessages().getString("password"), new LockIcon()), column, row++);

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

        // Create the userName field
        TextField titleField = createTextField(form.getMessages().getString("username_placeholder"), form.userNameProperty(), true);
        titleField.getStyleClass().add("title");
        grid.add(titleField, column, row++);

        // Create the password field
        TextField typeField = createTextField(form.getMessages().getString("password_placeholder"), form.passwordProperty());
        grid.add(typeField, column, row++);

        // Create the login button
        Button loginButton = new Button(form.getMessages().getString("login_button"));
        loginButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(loginButton, Priority.ALWAYS);

        loginButton.setOnAction(e -> {
            form.submit();
        });

        grid.add(loginButton, column, row + 2, 2, 1);
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

}
