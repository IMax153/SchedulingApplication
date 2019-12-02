/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.table;

import dao.CustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TableView;
import models.Customer;

/**
 * The {@link Control} for displaying {@link Customer} data in a
 * {@link TableView}.
 *
 * @author maxbrown
 */
public class CustomerTable extends Control {

    private final CustomerDao customerDao = new CustomerDao();

    public CustomerTable() {
        String stylesheet = getClass().getResource("customer-table.css").toExternalForm();
        getStylesheets().add(stylesheet);

        // Load all customers into the table
        refreshCustomers();
    }

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * Returns the value of {@link CustomerTable#customers}.
     *
     * @return The list of customers.
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Adds a {@link Customer} to the database.
     *
     * @param customer The customer to add.
     */
    public void addCustomer(Customer customer) {
        if (customerDao.add(customer)) {
            refreshCustomers();
        }
    }

    /**
     * Updates a {@link Customer} in the database.
     *
     * @param customer The customer to update.
     */
    public void updateCustomer(Customer customer) {
        if (customerDao.update(customer)) {
            refreshCustomers();
        }
    }

    /**
     * Deletes a {@link Customer} from the database.
     *
     * @param customer The customer to delete.
     */
    public void deleteCustomer(Customer customer) {
        if (customerDao.delete(customer.getId())) {
            refreshCustomers();
        }
    }

    private void refreshCustomers() {
        getCustomers().clear();

        customerDao.findAll().forEach(maybeCustomer
                -> maybeCustomer.ifPresent(customer -> getCustomers().add(customer))
        );
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomerTableSkin(this);
    }
}
