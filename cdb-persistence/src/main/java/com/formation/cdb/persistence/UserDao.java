package com.formation.cdb.persistence;

import com.formation.cdb.entity.impl.User;

public interface UserDao {
    User findByUserName(String username);
}
