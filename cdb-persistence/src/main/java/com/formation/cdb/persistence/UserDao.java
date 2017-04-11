package com.formation.cdb.persistence;

import com.formation.cdb.entity.impl.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDao.
 */
public interface UserDao {
    
    /**
     * Find by user name.
     *
     * @param username the username
     * @return the user
     */
    User findByUserName(String username);
}
