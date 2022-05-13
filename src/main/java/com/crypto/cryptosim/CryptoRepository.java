package com.crypto.cryptosim;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * Pour la suite du projet, CryptoRepository est désormais remplacé par MarketManager
 * Qui est plus adapté à la classe ValuableCrypto
 * @deprecated
 */
public class CryptoRepository extends AbstractRepository{

    private static CryptoRepository instance = null;

    public static CryptoRepository getInstance(){
        if(instance == null)
            instance = new CryptoRepository();
        return instance;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS \"price\";\n" +
                "DROP TABLE IF EXISTS \"crypto\";\n" +
                "" +
                "" +
                "CREATE TABLE \"crypto\" (\n" +
                "    crypto_id serial PRIMARY KEY,\n" +
                "    crypto_name varchar(255),\n" +
                "    crypto_slug varchar(255),\n" +
                "    crypto_desc text,\n" +
                "    crypto_seed INT UNIQUE\n" +
                ");" +
                "" +
                "" +
                "CREATE TABLE \"price\" (\n" +
                "    price_crypto INT,\n" +
                "    price_date DATE NOT NULL,\n" +
                "    price_value INT,\n" +
                "    CONSTRAINT fk_crypto FOREIGN KEY (price_crypto) REFERENCES crypto(crypto_id)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    public Crypto getFromResultSet(ResultSet rs) throws SQLException {
        Crypto c = new Crypto();
        c.setId(rs.getInt("crypto_id"));
        c.setName(rs.getString("crypto_name"));
        c.setSlug(rs.getString("crypto_slug"));
        c.setDescription(rs.getString("crypto_desc"));
        return c;
    }



    @Override
    public void add(Object _c) throws SQLException {
        Crypto c = (Crypto)_c;
        PreparedStatement stmt = null;
        stmt = getConnection().prepareStatement("INSERT INTO \"crypto\" (crypto_name, crypto_slug, crypto_desc) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, c.getName());
        stmt.setString(2, c.getSlug());
        stmt.setString(3, c.getDescription());
        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        c.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<Crypto> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"crypto\"");

        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }

    public boolean isNewCrypto(ValuableCrypto c) throws Exception {
        if(c.getId() == 0) {
            throw new Exception("Crypto should be a stored inside database");
        }
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM \"price\" WHERE price_crypto=?");
        stmt.setInt(1, c.getId());
        ResultSet rs = stmt.executeQuery();
        return !rs.next();
    }

}
