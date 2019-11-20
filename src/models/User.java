/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing a user of the application.
 *
 * @author mab90
 */
public class User {

    /**
     * The unique identifier for the user.
     */
    private int id;

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

    /**
     * The userName of the user who created this user.
     */
    private String createdBy;

    /**
     * The userName of the user who last updated this user.
     */
    private String updatedBy;

    /**
     * The instant this user was created.
     */
    private Instant createdAt;

    /**
     * The instant this user was last updated.
     */
    private Instant updatedAt;

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
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user's userName.
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user's userName.
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets whether or not the user is currently active in the database.
     *
     * @return isActive
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets whether or not the user is currently active in the database.
     *
     * @param isActive
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the userName of the user who created this user.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the userName of the user who created this user.
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the userName of the user who last updated this user.
     *
     * @return updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the userName of the user who last updated this user.
     *
     * @param updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Gets the instant this user was created.
     *
     * @return
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the instant this user was created.
     *
     * @param createdAt
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the instant this user was last updated.
     *
     * @return updatedAt
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the instant this user was last updated.
     *
     * @param updatedAt
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User ");

        sb.append("{");
        sb.append(System.getProperty("line.separator"));

        sb.append("  id: ");
        sb.append(id);
        sb.append(System.getProperty("line.separator"));

        sb.append("  userName: ");
        sb.append(userName);
        sb.append(System.getProperty("line.separator"));

        sb.append("  password: ");
        sb.append(password);
        sb.append(System.getProperty("line.separator"));

        sb.append("  isActive: ");
        sb.append(isActive);
        sb.append(System.getProperty("line.separator"));

        sb.append("  createdBy: ");
        sb.append(createdBy);
        sb.append(System.getProperty("line.separator"));

        sb.append("  updatedBy: ");
        sb.append(updatedBy);
        sb.append(System.getProperty("line.separator"));

        sb.append("  createdAt: ");
        sb.append(createdAt);
        sb.append(System.getProperty("line.separator"));

        sb.append("  updatedAt: ");
        sb.append(updatedAt);
        sb.append(System.getProperty("line.separator"));

        sb.append("}");
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }

}
