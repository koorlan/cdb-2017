package com.formation.cdb;

import java.sql.Connection;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.formation.cdb.persistence.connection.ConnectionManager;

public class DatabaseTest {
    
    @Test
    public void isConnectionNotEmpty() {
        Optional<Connection> conn = ConnectionManager.INSTANCE.getConnection();
        Assert.assertTrue(false);
    }
    

}
