package com.crypto.cryptosim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CryptoValueTest {
    private DatabaseManager dm;
    private TickManager tm;
    private CryptoRepository cr;

    @BeforeEach
    void init() throws SQLException {
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();

        tm = TickManager.getInstance();

        cr = CryptoRepository.getInstance();
        cr.buildSQLTable();
    }

    @Test
    void newCrypto() throws Exception {
        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        cr.add((Crypto)c);
        assertEquals(true, cr.isNewCrypto(c));
    }

    @Test
    void oldCrypto() throws Exception {
        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        cr.add((Crypto)c);
        c.initValue(1000);

        tm.nextTick();
        assertEquals(false, cr.isNewCrypto(c));
    }

    @Test
    void testDate() throws Exception {

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        cr.add((Crypto)c);
        c.initValue(1000);

        LocalDate date1 = tm.getDate();
        tm.nextTick();
        LocalDate date2 = tm.getDate();

        assertNotEquals(date1.toString(), date2.toString());
    }


    @Test
    void testSeed() throws SQLException {
        ValuableCrypto c = new ValuableCrypto();
        c.setName("ETH");
        cr.add((Crypto)c);
        c.initValue(1000);

        tm.nextTick();

        // Seed est utilisé pour
        assertNotEquals(-1, (Integer)c.getSeed());
    }
}
