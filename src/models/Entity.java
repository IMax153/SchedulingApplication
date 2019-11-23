/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.Instant;

/**
 * Class representing a database entity.
 *
 * All database entities have a unique identifier, a field indicating the user
 * who created the entity, a field indicating the user who last updated the
 * entity, and timestamps indicating the instant the entity was created and last
 * updated.
 *
 * @author mab90
 */
public abstract class Entity {

    /**
     * The unique identifier of the entity.
     */
    protected final int id;

    /**
     * The userName of the user who created this entity.
     */
    protected final String createdBy;

    /**
     * The userName of the user who last updated this entity.
     */
    protected final String updatedBy;

    /**
     * The instant this entity was created.
     */
    protected final Instant createdAt;

    /**
     * The instant this entity was last updated.
     */
    protected final Instant updatedAt;

    /**
     * A StringBuilder used when overriding an entity's `toString()` method to
     * pretty print the entity.
     */
    protected StringBuilder sb = new StringBuilder();

    public Entity(int id, String createdBy, String updatedBy, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the unique identifier of the entity.
     *
     * @return The unique identifier of the entity.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the userName of the user who created this entity.
     *
     * @return The userName of the user who created this entity.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the userName of the user who last updated this entity.
     *
     * @return The userName of the user who last updated this entity.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Gets the instant this entity was created.
     *
     * @return The instant this entity was created.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the instant this entity was last updated.
     *
     * @return The instant this entity was last updated.
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

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

        return sb.toString();
    }

}
