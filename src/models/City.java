/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing a city within the application.
 *
 * @author mab90
 */
public class City extends Entity {

    /**
     * The name of the city.
     */
    private String name;

    /**
     * The country in which the city is located.
     */
    private Country country;

    public City(int id, String name, String createdBy, String updatedBy, Instant createdAt, Instant updatedAt, Country country) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.name = name;
        this.country = country;
    }

    // Getters
    /**
     * Gets the name of the city.
     *
     * @return The name of the city.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the country in which this city is located.
     *
     * @return The country in which the city is located.
     */
    public Country getCountry() {
        return country;
    }

    // Setters
    /**
     * Sets the name of the city.
     *
     * @param name The name of the city.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the country for this city.
     *
     * @param country The country in which this city is located.
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Print a pretty string of the city.
     *
     * @return A string representation of the city.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("City ");

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

        sb.append("\tcountry: ");
        sb.append(country.toString());
        sb.append(System.getProperty("line.separator"));

        sb.append("}");

        return sb.toString();
    }

}
