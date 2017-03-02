package com.formation.cdb.entity;

public interface Entity {

    /**
     * Return the string representation of an entity.
     * @return the representation of the entity
     */
    String toString();

    /**
     * Calculate hash code of an entity.
     * @return an int to represent the entity.
     */
    int hashCode();

    /**
     * Operation to compare entities.
     * @param o The other object to compare the current instance
     * @return a boolean.
     */
    boolean equals(Object o);
}
