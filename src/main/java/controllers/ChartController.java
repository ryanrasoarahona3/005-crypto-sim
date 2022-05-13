package controllers;

import com.crypto.cryptosim.*;
import com.google.gson.Gson;
import java.sql.SQLException;

public class ChartController {
    private static ChartController instance = null;
    public static ChartController getInstance(){
        if(instance == null){
            instance = new ChartController();
        }
        return instance;
    }

    // Les propriétés privés
    // TODO: factoriser les contrôleurs
    private DatabaseManager dm;
    private TickManager tm;
    private MarketManager mm;

    public String chartDataJson = "";
    public ChartController(){

        // Initialisation des propriétés privés
        try {
            dm = DatabaseManager.getInstance();
            dm.setDbName("crypto-staging");
            dm.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tm = TickManager.getInstance();
        mm = MarketManager.getInstance();

        // Initialisation des propriétés publics
        try {
            ValuableCrypto c = MarketManager.getInstance().cryptoByName("Bitcoin");
            chartDataJson = PriceCurve.getInstance().getLastMonthVariationJson(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
