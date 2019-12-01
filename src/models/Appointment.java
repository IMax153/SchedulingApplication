/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;
import java.util.Comparator;

/**
 * Class representing an appointment within the application.
 *
 * @author mab90
 */
public class Appointment extends Entity implements Comparator<Appointment>, Comparable<Interval> {

    private String title;

    private String description;

    private String location;

    private String contact;

    private String type;

    private String url;

    private Interval interval;

    private Customer customer;

    private User user;

    public Appointment(
            int id,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String url,
            Interval interval,
            Customer customer,
            User user,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.interval = interval;
        this.customer = customer;
        this.user = user;
    }

    /**
     * Returns the value of {@link Appointment#title}.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of {@link Appointment#title}.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the value of {@link Appointment#description}.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of {@link Appointment#description}.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the value of {@link Appointment#location}.
     *
     * @return The location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of {@link Appointment#location}.
     *
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the value of {@link Appointment#contact}.
     *
     * @return The contact.
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the value of {@link Appointment#contact}.
     *
     * @param contact The contact to set.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Returns the value of {@link Appointment#type}.
     *
     * @return The type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of {@link Appointment#type}.
     *
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the value of {@link Appointment#url}.
     *
     * @return The URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of {@link Appointment#url}.
     *
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the value of {@link Appointment#interval}.
     *
     * @return The interval.
     */
    public Interval getInterval() {
        return interval;
    }

    /**
     * Sets the value of {@link Appointment#interval}.
     *
     * @param interval The interval to set.
     */
    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    /**
     * Returns the value of {@link Appointment#customer}.
     *
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the value of {@link Appointment#customer}.
     *
     * @param customer The customer to set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the value of {@link Appointment#user}.
     *
     * @return The user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of {@link Appointment#user}.
     *
     * @param user The user to set.
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
        sb.append("Appointment[");

        sb.append("title: ");
        sb.append(title);
        sb.append(", ");

        sb.append("description: ");
        sb.append(description);
        sb.append(", ");

        sb.append("location: ");
        sb.append(location);
        sb.append(", ");

        sb.append("contact: ");
        sb.append(contact);
        sb.append(", ");

        sb.append("type: ");
        sb.append(type);
        sb.append(", ");

        sb.append("url: ");
        sb.append(url);
        sb.append(", ");

        sb.append("interval: ");
        sb.append(interval.toString());
        sb.append(", ");

        sb.append("createdBy: ");
        sb.append(createdBy);
        sb.append(", ");

        sb.append("updatedBy: ");
        sb.append(updatedBy);
        sb.append(", ");

        sb.append("createdAt: ");
        sb.append(createdAt);
        sb.append(", ");

        sb.append("updatedAt: ");
        sb.append(updatedAt);
        sb.append(", ");

        sb.append("customer: ");
        sb.append(customer.toString());
        sb.append(", ");

        sb.append("user: ");
        sb.append(user.toString());

        sb.append("]");

        return sb.toString();
    }

    /**
     * The {@link Comparator} implementation for the {@link Appointment} class.
     * Uses the {@link Appointment#interval} to compare the two objects.
     *
     * @param a1 The first appointment.
     * @param a2 The second appointment.
     * @return The comparison.
     */
    @Override
    public int compare(Appointment a1, Appointment a2) {
        return a1.interval.compare(a1.interval, a2.interval);
    }

    /**
     * The {@link Comparable} implementation for the {@link Appointment} class.
     *
     * @param appointment The appointment to compare.
     * @return The comparison.
     */
    @Override
    public int compareTo(Interval appointment) {
        return this.interval.compareTo(interval);
    }

}
