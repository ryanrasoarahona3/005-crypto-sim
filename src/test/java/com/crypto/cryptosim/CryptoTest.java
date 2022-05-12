package com.crypto.cryptosim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {
    private DatabaseManager dm;
    private CryptoRepository cr;

    @BeforeEach
    void init() throws SQLException {
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();
        cr = CryptoRepository.getInstance();
        cr.buildSQLTable();
    }

    @Test
    void databaseInsertion() throws SQLException {
        Crypto c1 = new Crypto();
        c1.setName("Bitcoin");
        Crypto c2 = new Crypto();
        c2.setName("Euthérium");

        cr.add(c1);
        cr.add(c2);

        // Test Insertino
        assertNotEquals((Integer) null, c1.getId());
        assertNotEquals((Integer) null, c2.getId());

        ArrayList<Crypto> list = cr.getAll();
        assertEquals(2, list.size());
    }
}