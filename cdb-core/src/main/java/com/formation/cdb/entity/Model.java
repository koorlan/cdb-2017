package com.formation.cdb.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 */
public abstract class Model {

    /**
     * Return the string representation of an entity.
     * @return the representation of the entity
     */
    @Override
    public abstract String toString();

    /**
     * Calculate hash code of an entity.
     * @return an int to represent the entity.
     */
    @Override
    public abstract int hashCode();

    /**
     * Operation to compare entities.
     * @param o The other object to compare the current instance
     * @return a boolean.
     */
    @Override
    public abstract boolean equals(Object o);
}
