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
    private String street;

    /**
     * The part of the address containing additional information. For example,
     * an apartment number.
     */
    private String additionalInformation;

    /**
     * The part of the address containing the postal code.
     */
    private String postalCode;

    /**
     * The phone number for the address.
     */
    private String phone;

    /**
     * The city of the address.
     */
    private City city;

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

    // Getters
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

    // Setters
    /**
     * Sets the street name and number of the address.
     *
     * @param street The street name and number.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Sets any additional information for the address.
     *
     * @param additionalInformation Any additional information for the address.
     */
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    /**
     * Sets the postal code of the address.
     *
     * @param postalCode The postal code of the address.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets the phone number of the address.
     *
     * @param phone The phone number of the address.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the city of the address.
     *
     * @param city The city of the address.
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Print a pretty string of the address.
     *
     * @return A string representation of the address.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Address ");

        sb.append("{");
        sb.append(System.getProperty("line.separator"));

        sb.append("\tstreet: ");
        sb.append(street);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tadditionalInformation: ");
        sb.append(additionalInformation);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tpostalCode: ");
        sb.append(postalCode);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tphone: ");
        sb.append(phone);
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

        sb.append("\tcity: ");
        sb.append(city.toString());
        sb.append(System.getProperty("line.separator"));

        sb.append("}");

        return sb.toString();
    }

}
