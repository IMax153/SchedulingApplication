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
    private String name;

    public Country(int id, String name, String createdBy, String updatedBy, Instant createdAt, Instant updatedAt) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.name = name;
    }

    // Getters
    /**
     * Gets the name of the country.
     *
     * @return The name of the country.
     */
    public String getName() {
        return name;
    }

    // Setters
    /**
     * Sets the name of the country.
     *
     * @param name The name of the country.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Print a pretty string representing the country.
     *
     * @return A string representation of the country.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("Country ");

        sb.append("{");
        sb.append(System.getProperty("line.separator"));

        sb.append("\tid: ");
        sb.append(id);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tname: ");
        sb.append(name);
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

        sb.append("}");

        return sb.toString();
    }

}
