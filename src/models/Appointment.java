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

    /**
     * The title of the appointment.
     */
    private final String title;

    /**
     * The description of the appointment.
     */
    private final String description;

    /**
     * The location of the appointment.
     */
    private final String location;

    /**
     * The contact for the appointment.
     */
    private final String contact;

    /**
     * The type of the appointment.
     */
    private final String type;

    /**
     * The URL for the appointment.
     */
    private final String url;

    /**
     * The interval for the appointment.
     */
    private final Interval interval;

    /**
     * The customer who is a part of the appointment.
     */
    private final Customer customer;

    /**
     * The user who is a part of the appointment.
     */
    private final User user;

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
     * Gets the interval for the appointment.
     *
     * @return The interval for the appointment.
     */
    public Interval getInterval() {
        return interval;
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

    /**
     * Print a pretty string of the appointment.
     *
     * @return A string representation of the appointment.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Appointment[");

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
