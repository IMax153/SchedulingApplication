/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing an address within the application.
 *
 * @author mab90
 */
public class Address extends Entity {

    /**
     * The part of the address containing the address name.
     */
    private final String address;

    /**
     * The part of the address containing additional information. For example,
     * an apartment number.
     */
    private final String address2;

    /**
     * The part of the address containing the postal code.
     */
    private final String postalCode;

    /**
     * The phone number for the address.
     */
    private final String phone;

    /**
     * The city of the address.
     */
    private final City city;

    public Address(
            int id,
            String address,
            String address2,
            String postalCode,
            String phone,
            City city,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.address = address;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
    }

    /**
     * Gets the address name and number of the address.
     *
     * @return The address name and number of the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets any additional information for the address.
     *
     * @return Any additional information for the address.
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Gets the postal code of the address.
     *
     * @return The postal code of the address.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the phone number of the address.
     *
     * @return The phone number of the address.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the city of the address.
     *
     * @return The city of the address.
     */
    public City getCity() {
        return city;
    }

    /**
     * Print a pretty string of the address.
     *
     * @return A string representation of the address.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Address[");

        sb.append("address: ");
        sb.append(address);
        sb.append(", ");

        sb.append("address2: ");
        sb.append(address2);
        sb.append(", ");

        sb.append("postalCode: ");
        sb.append(postalCode);
        sb.append(", ");

        sb.append("phone: ");
        sb.append(phone);
        sb.append(", ");

        sb.append("city: ");
        sb.append(city.toString());
        sb.append(", ");

        sb.append(super.toString());

        sb.append("]");

        return sb.toString();
    }

}
