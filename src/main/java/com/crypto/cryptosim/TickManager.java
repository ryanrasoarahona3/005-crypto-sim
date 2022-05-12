package com.crypto.cryptosim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.time.temporal.*;

/**
 * Cette classe permet de gérer l'action du temps
 * Ce sera cette classe qui mettra à jour tous les valeurs des cryptos
 * À Chaque instant
 */
public class TickManager {
    private static TickManager instance = null;

    public static TickManager getInstance(){
        if(instance == null){
            instance = new TickManager();
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

    private ArrayList<ValuableCrypto> toBeUpdated = new ArrayList<>();

    /**
     * Permet de définir le changement de valeur apporté à un crypto
     * Pour notre cas, il s'agit uniquement des cryptos nouvellement
     * mis sur le marché
     * @param c
     */
    public void register(ValuableCrypto c){
        /**
         * Normalement, un crypto ne peut être ajouté qu'une seule fois
         */
        toBeUpdated.add(c);
    }


    public void nextTick() throws SQLException {
        /**
         * Doit être modifié pour prendre en compte tous les autres cryptos
         * déjà sur le marché
         */
        for(int i = 0; i < toBeUpdated.size(); i++){
            ValuableCrypto c = toBeUpdated.get(i);
            PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO \"price\" (price_crypto, price_date, price_value) VALUES (?, ?, ?)");
            stmt.setInt(1, c.getId());
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setInt(3, c.getValue());
            stmt.execute();
        }

        date = date.plus(tick);
    }

    private static LocalDate fromString(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.ENGLISH );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse("2000-Jan-01", formatter);
        return date;
    }

    public static TemporalAmount tick = Period.ofDays(1);
    private LocalDate date = null;

    public LocalDate getDate() {
        return date;
    }

    public TickManager()  {
        date = fromString("01-Jan-2000");
    }
}
