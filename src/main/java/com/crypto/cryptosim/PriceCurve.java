package com.crypto.cryptosim;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class PriceCurve{
    private static PriceCurve instance = null;
    public static PriceCurve getInstance(){
        if(instance == null){
            instance = new PriceCurve();
        }
        return instance;
    }

    /**
     * Get Database connection
     * @return
     */
    protected Connection getConnection(){
        return DatabaseManager.getInstance().getConnection();
    }

    public ArrayList<Integer> getLastMonthVariation(ValuableCrypto c) throws SQLException {
        ArrayList<Integer> output = new ArrayList<>();
        PreparedStatement stmt = getConnection().prepareStatement("SELECT price_value FROM price WHERE price_crypto=? ORDER BY price_date DESC LIMIT 30;");
        stmt.setInt(1, c.getId());
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            output.add(rs.getInt("price_value"));
        return output;
    }

    public String getLastMonthVariationJson(ValuableCrypto c) throws SQLException{
        ArrayList<Integer> variation = getLastMonthVariation(c);
        String json = new Gson().toJson(variation);
        return json;
    }
}
