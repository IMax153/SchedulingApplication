/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.table;

import controls.dialog.customer.CustomerDialog;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.Customer;

/**
 * The {@link Skin} for the {@link CustomerTable}.
 *
 * @author maxbrown
 */
public class CustomerTableSkin extends SkinBase<CustomerTable> {

    private final CustomerTable view;

    public CustomerTableSkin(CustomerTable view) {
        super(view);
        this.view = view;

        // Create the root pane for the view
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("container");

        // Create the title for the view
        Label title = new Label("Customers");
        title.getStyleClass().add("title");

        // Set the label to the top of the pane
        pane.setTop(title);

        // Create the customer table 
        TableView<Customer> customerTable = new TableView<>();
        customerTable.getStyleClass().add("customer-table");
        customerTable.setMinSize(0, 0);
        customerTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Add the customers to the table 
        customerTable.setItems(view.getCustomers());

        // Create a column for the customer name
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getName()));
        nameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Create a column indicating whether the customer is active
        TableColumn<Customer, Boolean> activeColumn = new TableColumn<>("Active");
        activeColumn.setCellValueFactory(item -> new ReadOnlyBooleanWrapper(item.getValue().isActive()));
        activeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Create a column for the customer address
        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getAddress().getAddress()));
        addressColumn.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Create a column for the customer address 2
        TableColumn<Customer, String> address2Column = new TableColumn<>("Address 2");
        address2Column.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getAddress().getAddress2()));
        address2Column.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Create a column for the customer address postal code
        TableColumn<Customer, String> postalCodeColumn = new TableColumn<>("Postal Code");
        postalCodeColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getAddress().getPostalCode()));
        postalCodeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Create a column for the customer phone
        TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getAddress().getPhone()));
        phoneColumn.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Create a column for the customer city name
        TableColumn<Customer, String> cityNameColumn = new TableColumn<>("City");
        cityNameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getAddress().getCity().getName()));
        cityNameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Create a column for the customer city name
        TableColumn<Customer, String> countryNameColumn = new TableColumn<>("Country");
        countryNameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getAddress().getCity().getCountry().getName()));
        countryNameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);

        // Set the table columns
        customerTable.getColumns().addAll(
                nameColumn,
                activeColumn,
                addressColumn,
                address2Column,
                postalCodeColumn,
                phoneColumn,
                cityNameColumn,
                countryNameColumn
        );

        // Set the table to the center of the pane
        pane.setCenter(customerTable);

        // Create the table view buttons container 
        HBox buttonContainer = new HBox();
        buttonContainer.getStyleClass().add("buttons");
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.setSpacing(30);

        // Create the table view buttons
        Button addCustomerButton = new Button("Add Customer");
        Button updateCustomerButton = new Button("Update Customer");
        Button deleteCustomerButton = new Button("Delete Customer");

        // Add the button handlers
        addCustomerButton.setOnAction(e -> {
            // Create a new customer dialog
            CustomerDialog customerDialog = new CustomerDialog();

            // Add customer on save
            customerDialog.setOnFormSubmit(view::addCustomer);

            // Show the dialog
            customerDialog.showAndWait();
        });

        updateCustomerButton.setOnAction(e -> {
            // Get a reference to the selected customer in the table
            Customer customer = customerTable.getSelectionModel().getSelectedItem();

            if (customer != null) {
                // Create a new customer dialog
                CustomerDialog customerDialog = new CustomerDialog(customer);

                // Update customer on save
                customerDialog.setOnFormSubmit(view::updateCustomer);

                // Show the dialog
                customerDialog.showAndWait();
            } else {
                // Show alert on invalid operation
                showInvalidOperationAlert("You must select a customer to update");
            }
        });

        deleteCustomerButton.setOnAction(e -> {
            // Get a reference to the selected customer in the table
            Customer customer = customerTable.getSelectionModel().getSelectedItem();

            // Only open the confirmation alert if a customer is selected
            if (customer != null) {
                // Create a confirmation alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Customer");
                alert.setHeaderText("Confirm deletion");
                alert.setContentText("Please confirm deletion of the customer with name " + customer.getName());

                // Show the alert
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> view.deleteCustomer(customer));
            } else {
                // Show alert on invalid operation
                showInvalidOperationAlert("You must select a customer to delete");
            }
        });

        // Add the buttons to the container
        buttonContainer.getChildren().addAll(
                addCustomerButton,
                updateCustomerButton,
                deleteCustomerButton
        );

        // Set the button container to the botton of the pane
        pane.setBottom(buttonContainer);

        // Add the pane to the view
        getChildren().add(pane);
    }

    private void showInvalidOperationAlert(String message) {
        // Warn the user that they cannot update a customer if none are selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid operation");
        alert.setContentText(message);

        alert.showAndWait();
    }

}
