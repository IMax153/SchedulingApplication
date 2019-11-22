/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing a customer within the application.
 *
 * @author mab90
 */
public class Customer extends Entity {

    /**
     * The name of the customer.
     */
    private String name;

    /**
     * Whether or not the customer is currently active.
     */
    private boolean isActive;

    /**
     * The address of the customer.
     */
    private Address address;

    public Customer(
            int id,
            String name,
            boolean isActive,
            Address address,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.name = name;
        this.isActive = isActive;
        this.address = address;
    }

    // Getters
    /**
     * Gets the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets whether or not the user is currently active.
     *
     * @return True if the user is active, otherwise false.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Gets the address of the customer.
     *
     * @return The address of the customer.
     */
    public Address getAddress() {
        return address;
    }

    // Setters
    /**
     * Sets the name of the customer.
     *
     * @param name The name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets whether or not the customer is currently active.
     *
     * @param isActive True if the customer is active, otherwise false.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address The address of the customer.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Print a pretty string of the customer.
     *
     * @return A string representation of the customer.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Customer ");

        sb.append("{");
        sb.append(System.getProperty("line.separator"));

        sb.append("\tid: ");
        sb.append(id);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tisActive: ");
        sb.append(isActive);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tcreatedBy: ");
        sb.append(createdBy);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tupdatedBy: ");
        sb.append(updatedBy);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tcreatedAt: ");
        sb.append(createdAt);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tupdatedAt: ");
        sb.append(updatedAt);
        sb.append(System.getProperty("line.separator"));

        sb.append("\taddress: ");
        sb.append(address.toString());
        sb.append(System.getProperty("line.separator"));

        sb.append("}");

        return sb.toString();
    }

}
