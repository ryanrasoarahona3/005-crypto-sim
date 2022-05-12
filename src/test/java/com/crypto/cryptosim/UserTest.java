package com.crypto.cryptosim;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {
    private DatabaseManager dm;
    private UserRepository ur;

    @BeforeEach
    void init() throws SQLException {
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();
        ur = UserRepository.getInstance();
        ur.buildSQLTable();
    }

    @Test
    void databaseInsertion() throws SQLException {

        User u = new User();
        u.setEmail("user@mail.com");
        u.setPseudo("pseudo");
        u.setPassword("password");
        ur.add(u);

        assertNotEquals((Integer) null, u.getId());
    }

    @Test
    void databaseListing() throws SQLException {
        User u1 = new User();
        u1.setEmail("user@mail.com");
        User u2 = new User();
        u2.setEmail("user2@mail.com");

        ur.add(u1);
        ur.add(u2);

        ArrayList<User> users = ur.getAll();
        assertEquals(2, users.size());
    }

    @Test
    void verifyCredentials() throws SQLException {
        User u1 = new User();
        u1.setEmail("john@gmail.com");
        u1.setPassword("password");
        ur.add(u1);
        User u = new User();

        u.setEmail("john@gmail.com");
        u.setPassword("pass");
        assertEquals(false, ur.verifyCredentials(u));

        u.setEmail("john@gmail.co");
        u.setPassword("password");
        assertEquals(false, ur.verifyCredentials(u));

        u.setEmail("john@gmail.com");
        u.setPassword("password");
        assertEquals(true, ur.verifyCredentials(u));
    }
}