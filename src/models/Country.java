/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing a country within the application.
 *
 * @author mab90
 */
public class Country extends Entity {

    /**
     * The name of the country.
     */
    private final String name;

    public Country(int id, String name, String createdBy, String updatedBy, Instant createdAt, Instant updatedAt) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.name = name;
    }

    /**
     * Gets the name of the country.
     *
     * @return The name of the country.
     */
    public String getName() {
        return name;
    }

    /**
     * Print a pretty string representing the country.
     *
     * @return A string representation of the country.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Country[");

        sb.append("name: ");
        sb.append(name);
        sb.append(", ");

        sb.append(super.toString());

        sb.append("]");

        return sb.toString();
    }

}
