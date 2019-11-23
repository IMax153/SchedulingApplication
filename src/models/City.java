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
    private final String name;

    /**
     * The country in which the city is located.
     */
    private final Country country;

    public City(
            int id,
            String name,
            Country country,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.name = name;
        this.country = country;
    }

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

    /**
     * Print a pretty string of the city.
     *
     * @return A string representation of the city.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("City[");

        sb.append("name: ");
        sb.append(name);
        sb.append(", ");

        sb.append("country: ");
        sb.append(country.toString());
        sb.append(", ");

        sb.append(super.toString());

        sb.append("]");

        return sb.toString();
    }

}
