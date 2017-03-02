package com.formation.cdb.entity;

public abstract class Entity {

    /**
     * Return the string representation of an entity.
     * @return the representation of the entity
     */
    public abstract String toString();

    /**
     * Calculate hash code of an entity.
     * @return an int to represent the entity.
     */
    public abstract int hashCode();

    /**
     * Operation to compare entities.
     * @param o The other object to compare the current instance
     * @return a boolean.
     */
    public abstract boolean equals(Object o);
}
