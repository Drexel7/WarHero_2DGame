package main;

import java.sql.*;

public class DataBase {
    private Connection c = null;
    private Statement stmt = null;
    private ResultSet rs;

    public DataBase() throws Exception {
        BazaData();
    }

    public void BazaData() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:databaseJoc.db");
            stmt = c.createStatement();

            String createTableQuery = "CREATE TABLE IF NOT EXISTS DataBase (" +
                    " ID INT PRIMARY KEY NOT NULL," +
                    " Health INT NOT NULL," +
                    " xPOS INT NOT NULL, " +
                    " yPOS INT NOT NULL, " +
                    " currentMap INT NOT NULL, " +
                    " ObjectCount INT DEFAULT 0, " +
                    " jumpCount INT DEFAULT 0)";

            stmt.execute(createTableQuery);

            String countQuery = "SELECT COUNT(*) FROM DataBase";
            ResultSet countResult = stmt.executeQuery(countQuery);
            int rowCount = countResult.getInt(1);
            countResult.close();

            int newID = rowCount + 1;

            String insertQuery = "INSERT INTO DataBase (ID, Health, xPOS, yPOS, currentMap, jumpCount) VALUES " +
                    "(" + newID + ", 100, 0, 1, 1, 1)";
            stmt.executeUpdate(insertQuery);

            String jumpCountQuery = "SELECT SUM(jumpCount) FROM DataBase";
            ResultSet jumpCountResult = stmt.executeQuery(jumpCountQuery);
            int totalJumpCount = jumpCountResult.getInt(1);
            jumpCountResult.close();

            stmt.close();

            System.out.println("Table created and initialized.");
            System.out.println("Total jump count: " + totalJumpCount);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public int getHealth() throws SQLException {
        String query = "SELECT * FROM DataBase";
        try (Statement statement = c.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("Health");
            } else {
                throw new SQLException("No rows found in DataBase table");
            }
        }
    }

    public int getXPos() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM DataBase;");
        return rs.getInt("xPOS");
    }

    public int getYPos() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM DataBase;");
        return rs.getInt("yPOS");
    }

    public int getCurrentMap() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM DataBase;");
        return rs.getInt("currentMap");
    }

    public int getObjectCount() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM DataBase;");
        return rs.getInt("ObjectCount");
    }

    public void updateHealth(int health) throws SQLException {
        String instruction = "UPDATE DataBase SET Health=" + health + " WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public void updateXPos(int xPOS) throws SQLException {
        String instruction = "UPDATE DataBase SET xPOS=" + xPOS + " WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public void updateYPos(int yPOS) throws SQLException {
        String instruction = "UPDATE DataBase SET yPOS=" + yPOS + " WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public void updateCurrentMap(int currentMap) throws SQLException {
        String instruction = "UPDATE DataBase SET currentMap=" + currentMap + " WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }

    public void updateObjectCount(int count) throws SQLException {
        String instruction = "UPDATE DataBase SET ObjectCount=" + count + " WHERE ID=1;";
        stmt.executeUpdate(instruction);
    }


    public void incrementJumpCount() throws SQLException {
        String incrementQuery = "UPDATE DataBase SET jumpCount = jumpCount + 1 WHERE ID = 1;";
        stmt.executeUpdate(incrementQuery);
    }



}
