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
     * The part of the address containing the street name.
     */
    private final String street;

    /**
     * The part of the address containing additional information. For example,
     * an apartment number.
     */
    private final String additionalInformation;

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
            String street,
            String additionalInformation,
            String postalCode,
            String phone,
            City city,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.street = street;
        this.additionalInformation = additionalInformation;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
    }

    /**
     * Gets the street name and number of the address.
     *
     * @return The street name and number of the address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Gets any additional information for the address.
     *
     * @return Any additional information for the address.
     */
    public String getAdditionalInformation() {
        return additionalInformation;
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

        sb.append("street: ");
        sb.append(street);
        sb.append(", ");

        sb.append("additionalInformation: ");
        sb.append(additionalInformation);
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
