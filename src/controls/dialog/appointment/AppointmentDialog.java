/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.dialog.appointment;

import controls.form.appointment.AppointmentForm;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import models.Appointment;

/**
 * A {@link Dialog} control used to create, view, and edit {@link Appointment}s.
 *
 * @author maxbrown
 */
public class AppointmentDialog extends Dialog<Pair<AppointmentForm.Mode, Appointment>> {
    
    private final AppointmentForm form;
    
    public AppointmentDialog() {
        this.form = new AppointmentForm();
        initialize();
    }
    
    public AppointmentDialog(Appointment appointment) {
        this.form = new AppointmentForm(appointment);
        initialize();
    }
    
    private void initialize() {
        // Set the style of the dialog
        initStyle(StageStyle.UTILITY);

        // Set the title of the dialog
        setTitle("Appointment");

        // Get a reference to the dialog pane
        final DialogPane dialogPane = getDialogPane();

        // Load the stylesheet
        dialogPane.getStylesheets().add(getClass().getResource("appointment-dialog.css").toExternalForm());

        // Set the minimum size of the dialog pane 
        dialogPane.setMinWidth(600);

        // Add dialog buttons
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.LEFT);
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogPane.getButtonTypes().addAll(ButtonType.CLOSE, deleteButtonType, saveButtonType, cancelButtonType);

        // Hide the close button
        Node closeButton = dialogPane.lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        // Close the dialog on successful form actions
        Button deleteButton = (Button) dialogPane.lookupButton(deleteButtonType);
        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        Button cancelButton = (Button) dialogPane.lookupButton(cancelButtonType);
        
        saveButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!form.isValid()) {
                event.consume();
            }
        });
        
        deleteButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!form.isValid()) {
                event.consume();
            }
        });

        // Hide the delete button if the form is not in edit or delete mode
        if (!(form.getMode().equals(AppointmentForm.Mode.EDIT) || form.getMode().equals(AppointmentForm.Mode.EDIT))) {
            deleteButton.setVisible(false);
        }
        
        cancelButton.setOnAction(e -> hide());

        // Set the dialog pane content to the grid
        dialogPane.setContent(form);
        
        setResultConverter(dialogButton
                -> dialogButton == saveButtonType
                        ? form.submit()
                        : dialogButton == deleteButtonType ? form.delete()
                                : null);
    }
    
}
