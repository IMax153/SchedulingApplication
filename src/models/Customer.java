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
    private final String name;

    /**
     * Whether or not the customer is currently active.
     */
    private final boolean active;

    /**
     * The address of the customer.
     */
    private final Address address;

    public Customer(
            int id,
            String name,
            boolean active,
            Address address,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.name = name;
        this.active = active;
        this.address = address;
    }

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
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the address of the customer.
     *
     * @return The address of the customer.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Print a pretty string of the customer.
     *
     * @return A string representation of the customer.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Customer[");

        sb.append("name: ");
        sb.append(name);
        sb.append(", ");

        sb.append("active: ");
        sb.append(active);
        sb.append(", ");

        sb.append("address: ");
        sb.append(address.toString());
        sb.append(", ");

        sb.append(super.toString());

        sb.append("]");

        return sb.toString();
    }

}
