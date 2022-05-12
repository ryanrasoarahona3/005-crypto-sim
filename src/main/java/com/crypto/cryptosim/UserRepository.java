package com.crypto.cryptosim;

import java.sql.*;
import java.util.ArrayList;

/**
 * Un Repository, en quelques sortes, permet
 * de définir une entité matérialisée par une table SQL
 */
public class UserRepository extends AbstractRepository<User>{
    private static UserRepository instance = null;


    // A factoriser
    public void buildSQLTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS \"user\";\n" +
                "CREATE TABLE \"user\" (\n" +
                "                        user_id serial PRIMARY KEY,\n" +
                "                        user_email varchar(255),\n" +
                "                        user_pseudo varchar(255),\n" +
                "                        user_password varchar(255)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    // TODO: factoriser
    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("user_id"));
        u.setEmail(rs.getString("user_email"));
        u.setPseudo(rs.getString("user_pseudo"));
        u.setPassword(rs.getString("user_password"));
        return u;
    }

    public void add(User u) throws SQLException {
        PreparedStatement stmt = null;
        stmt = getConnection().prepareStatement("INSERT INTO \"user\" (user_email, user_pseudo, user_password) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, u.getEmail());
        stmt.setString(2, u.getPseudo());
        stmt.setString(3, u.getPassword());
        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        u.setId(auto_id);
    }

    public ArrayList<User> getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<User> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"user\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getUserFromResultSet(rs));
        }
        return output;
    }

    public boolean verifyCredentials(User u) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM \"user\" WHERE user_email=? AND user_password=?");
        stmt.setString(1, u.getEmail());
        stmt.setString(2, u.getPassword());
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public static UserRepository getInstance(){
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }
}
