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
    private String userName;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * Determines if the user is currently active in the database.
     */
    private boolean isActive;

    public User(
            int id,
            String userName,
            String password,
            boolean isActive,
            String createdBy,
            String updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(id, createdBy, updatedBy, createdAt, updatedAt);
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
    }

    // Getters
    /**
     * Gets the user's userName.
     *
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the user's password.
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets whether or not the user is currently active in the database.
     *
     * @return Boolean
     */
    public boolean getIsActive() {
        return isActive;
    }

    // Setters
    /**
     * Sets the userName for the user.
     *
     * @param userName - The userName for the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the password for the user.
     *
     * @param password - The password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets whether or not the user is currently active in the database.
     *
     * @param isActive - A flag indicating whether or not the user is active.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Print a pretty string of the user.
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User ");

        sb.append("{");
        sb.append(System.getProperty("line.separator"));

        sb.append("\tid: ");
        sb.append(id);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tuserName: ");
        sb.append(userName);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tpassword: ");
        sb.append(password);
        sb.append(System.getProperty("line.separator"));

        sb.append("\tisActive: ");
        sb.append(isActive);
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
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }

}
