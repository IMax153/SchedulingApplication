/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.dialog.customer;

import controls.form.customer.CustomerForm;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
import models.Customer;

/**
 * A {@link Dialog} control used to create, view, and edit {@link Customer}s via
 * a {@link CustomerForm}.
 *
 * @author maxbrown
 */
public class CustomerDialog extends Dialog<Object> {

    private final CustomerForm form = new CustomerForm();

    private final DialogPane pane;

    public CustomerDialog() {
        this.pane = getDialogPane();

        String stylesheet = getClass().getResource("customer-dialog.css").toExternalForm();
        pane.getStylesheets().add(stylesheet);

        // Set the minimum size of the dialog pane 
        pane.setMinWidth(600);

        // Initialize the dialog
        initialize();
    }

    public CustomerDialog(Customer customer) {
        this();
        form.setInitialValues(customer);
    }

    private void initialize() {
        // Set the style of the dialog
        initStyle(StageStyle.UTILITY);

        // Set the title of the dialog
        setTitle("Customer");

        // Set the dialog pane content to the form
        pane.setContent(form);

        // Add dialog buttons
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.LEFT);
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(ButtonType.CLOSE, deleteButtonType, saveButtonType, cancelButtonType);

        // Hide the close button
        Button closeButton = (Button) pane.lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        // Get references to the dialog buttons
        Button deleteButton = (Button) pane.lookupButton(deleteButtonType);
        Button saveButton = (Button) pane.lookupButton(saveButtonType);
        Button cancelButton = (Button) pane.lookupButton(cancelButtonType);

        // Set the dialog button actions
        saveButton.setOnAction(e -> form.submit());
        deleteButton.setOnAction(e -> form.submit());

        // Hide the dialog on cancel
        cancelButton.setOnAction(e -> hide());

        // Only hide the dialog on save and delete actions if the form is valid
        saveButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!form.isValid()) {
                event.consume();
            } else {
                hide();
            }
        });

        deleteButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!form.isValid()) {
                event.consume();
            } else {
                hide();
            }
        });

        setResultConverter(dialogButton -> null);
    }

    /**
     * A property containing the {@link Consumer} that will be called on form
     * submission.
     *
     * @return The on submit property.
     */
    public final ObjectProperty<Consumer<Customer>> onFormSubmitProperty() {
        return form.onSubmitProperty();
    }

    /**
     * Returns the value of the {@link CustomerForm#onSubmitProperty()}.
     *
     * @return The on submit consumer.
     */
    public final Consumer<Customer> getOnFormSubmit() {
        return form.onSubmitProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#onSubmitProperty()}.
     *
     * @param consumer The on submit consumer to set.
     */
    public final void setOnFormSubmit(Consumer<Customer> consumer) {
        requireNonNull(consumer);
        form.onSubmitProperty().set(consumer);
    }

}
