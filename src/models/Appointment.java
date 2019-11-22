/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing an appointment within the application.
 *
 * @author mab90
 */
public class Appointment extends Entity {

    /**
     * The title of the appointment.
     */
    private String title;

    /**
     * The description of the appointment.
     */
    private String description;

    /**
     * The location of the appointment.
     */
    private String location;

    /**
     * The contact for the appointment.
     */
    private String contact;

    /**
     * The type of the appointment.
     */
    private String type;

    /**
     * The URL for the appointment.
     */
    private String url;

    /**
     * The instant that the appointment starts.
     */
    private Instant start;

    /**
     * The instant that the appointment ends.
     */
    private Instant end;

    /**
     * The customer who is a part of the appointment.
     */
    private Customer customer;

    /**
     * The user who is a part of the appointment.
     */
    private User user;

    public Appointment(
            int id,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String url,
            Instant start,
            Instant end,
            Customer customer,
            User user,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.user = user;
    }

    // Getters
    /**
     * Gets the title of the appointment.
     *
     * @return The title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the appointment.
     *
     * @return The description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the location of the appointment.
     *
     * @return The location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the contact for the appointment.
     *
     * @return The contact for the appointment.
     */
    public String getContact() {
        return contact;
    }

    /**
     * Gets the type of the appointment.
     *
     * @return The type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the URL for the appointment.
     *
     * @return The URL for the appointment.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the instant that the appointment starts.
     *
     * @return The instant that the appointment starts.
     */
    public Instant getStart() {
        return start;
    }

    /**
     * Gets the instant that the appointment ends.
     *
     * @return The instant that the appointment ends.
     */
    public Instant getEnd() {
        return end;
    }

    /**
     * Gets the customer who is a part of the appointment.
     *
     * @return The customer who is a part of the appointment.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the user who is a part of the appointment.
     *
     * @return The user who is a part of the appointment.
     */
    public User getUser() {
        return user;
    }

    // Setters
    /**
     * Sets the title of the appointment.
     *
     * @param title The title of the appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the appointment.
     *
     * @param description The description of the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the location of the appointment.
     *
     * @param location The location of the appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the contact for the appointment.
     *
     * @param contact The contact for the appointment.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Sets the type of the appointment.
     *
     * @param type The type of the appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the URL for the appointment.
     *
     * @param url The URL for the appointment.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the instant that the appointment starts.
     *
     * @param start The instant that the appointment starts.
     */
    public void setStart(Instant start) {
        this.start = start;
    }

    /**
     * Sets the instant that the appointment ends.
     *
     * @param end The instant that the appointment ends.
     */
    public void setEnd(Instant end) {
        this.end = end;
    }

    /**
     * Sets the customer who is a part of the appointment.
     *
     * @param customer The customer who is a part of the appointment.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets the user who is a part of the appointment.
     *
     * @param user The user who is a part of the appointment.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Print a pretty string of the appointment.
     *
     * @return A string representation of the appointment.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Appointment ");

        sb.append("{");
        sb.append(System.getProperty("line.separator"));

        sb.append("\tid: ");
        sb.append(id);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tdescription: ");
        sb.append(description);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tlocation: ");
        sb.append(location);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tcontact: ");
        sb.append(contact);
        sb.append(System.getProperty("line.separator"));

        sb.append("\ttype: ");
        sb.append(type);
        sb.append(System.getProperty("line.separator"));

        sb.append("\turl: ");
        sb.append(url);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tstart: ");
        sb.append(start);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tend: ");
        sb.append(end);
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

        sb.append("\tcustomer: ");
        sb.append(customer.toString());
        sb.append(System.getProperty("line.separator"));

        sb.append("\tuser: ");
        sb.append(user.toString());
        sb.append(System.getProperty("line.separator"));

        sb.append("}");

        return sb.toString();
    }

}
