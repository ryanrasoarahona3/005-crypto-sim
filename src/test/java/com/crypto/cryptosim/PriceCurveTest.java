package com.crypto.cryptosim;

import com.google.gson.Gson;
import controllers.InstallationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PriceCurveTest {
    private DatabaseManager dm;
    private TickManager tm;
    private MarketManager mm;
    private PriceCurve pc;

    @BeforeEach
    void init() throws SQLException {
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-staging");
        dm.init();
        tm = TickManager.getInstance();
        mm = MarketManager.getInstance();
        pc = PriceCurve.getInstance();
        InstallationController.getInstance().install();
    }

    @Test
    void testMonthVariation () throws SQLException {
        // TODO: l'organisation TEST/STAGE/DEPLOY peut
        // être refaite dans une troisième itération du projet
        // Inclusion du controleur "installationController"
        // Lors des tests

        // tests d'intégration (je pense)
        ValuableCrypto c = mm.cryptoByName("Bitcoin");
        assertEquals("BTC", c.getSlug());

        // L'historique des prix durant les 30 derniers jours

        ArrayList<Integer> variation = pc.getLastMonthVariation(c);
        String json = new Gson().toJson(variation);
        System.out.println("");
    }
}