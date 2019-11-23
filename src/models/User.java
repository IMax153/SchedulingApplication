/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing a user within the application.
 *
 * @author mab90
 */
public class User extends Entity {

    /**
     * The userName of the user.
     */
    private final String userName;

    /**
     * The password of the user.
     */
    private final String password;

    /**
     * Whether or not the user is currently active.
     */
    private final boolean active;

    public User(
            int id,
            String userName,
            String password,
            boolean active,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.userName = userName;
        this.password = password;
        this.active = active;
    }

    /**
     * Gets the userName of the user.
     *
     * @return The userName of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets whether or not the user is currently active.
     *
     * @return True if the user is active, otherwise false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Print a pretty string of the user.
     *
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        sb.setLength(0);
        sb.append("User[");

        sb.append("id: ");
        sb.append(id);
        sb.append(", ");

        sb.append("userName: ");
        sb.append(userName);
        sb.append(", ");

        sb.append("password: ");
        sb.append(password);
        sb.append(", ");

        sb.append("active: ");
        sb.append(active);
        sb.append(", ");

        sb.append(super.toString());

        sb.append("]");

        return sb.toString();
    }

}
