package org.example.task2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class Database {
    private static Database instance;
    private  Connection c;

    private Database() {
        openDB();
        createTable();
    }

    private void openDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cache.db");

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS CACHE" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " URL           TEXT, " +
                    " SCRAPED            TEXT )";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static Database getInstance() {
        instance = Objects.requireNonNullElseGet(instance, Database::new);
        return instance;
    }

    public String getScrapedByUrl(String filepath) {
        String parsed = null;
        try {
            String sql = "SELECT SCRAPED from CACHE WHERE URL = ?;";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, filepath);

            ResultSet response = pstmt.executeQuery();

            if (response.next()) {
                parsed = response.getString("SCRAPED");
                System.out.println("Found scraped page in a database successfully.\n");
            } else {
                System.out.println("Did not find scraped page in cache. Scraping to be done.\n");
            }
            pstmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return parsed;
    }

    public void addScrapedPage(String filePath, String parsed) {
        try {
            String sql = "INSERT INTO CACHE (URL,SCRAPED) VALUES (?, ? );";
            PreparedStatement pstmt  = c.prepareStatement(sql);
            pstmt.setString((int)1, filePath);
            pstmt.setString((int)2, parsed);

            pstmt.executeUpdate();
            pstmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Scraped page cached successfully.\n");
    }
}
