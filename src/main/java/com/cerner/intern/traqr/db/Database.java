package com.cerner.intern.traqr.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cerner.intern.traqr.core.ConnectedLocation;
import com.cerner.intern.traqr.core.Location;

/**
 * Represents the database in which traQR data is stored.
 */
public class Database {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    private static Connection connection;
    static {
        try {
            Class.forName("org.sqlite.JDBC");
            getConnectionOrRetry();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error in loading database driver.", e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            LOGGER.error("Error establishing database connectivity.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the LOCATION and CONNECTION tables needed for traQR.
     */
    public static void createTables() {
        try (final Connection connection = getConnectionOrRetry()) {
            try (final Statement statementLocation = connection.createStatement()) {
                String sql = "CREATE TABLE LOCATION (" +
                        " ID   INT PRIMARY KEY      NOT NULL," +
                        " NAME          TEXT        NOT NULL," +
                        " QR_CODE_PATH  CHAR(80))";
                statementLocation.executeUpdate(sql);
            }
            try (final Statement statementConnection = connection.createStatement()) {
                String sql = "CREATE TABLE CONNECTION (" +
                        " PREV   INT    NOT NULL," +
                        " NEXT   INT    NOT NULL," +
                        " DESCRIPTION TEXT NOT NULL," +
                        " PRIMARY KEY(PREV, NEXT))";
                statementConnection.executeUpdate(sql);
            }
        } catch (SQLException e) {
            LOGGER.warn("Tables already exist or error creating table.", e);
        }

    }

    /*
     * (non-Javadoc)
     * Gets the open connection or retries if it has been dropped.
     */
    private static Connection getConnectionOrRetry() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:traqr.db");
            LOGGER.trace("Successfully opened new connection.");
        }
        return connection;
    }

    /**
     * Inserts a new location into the database.
     * @param location the {@link Location} to insert. Should not be null.
     * @return true if the insert was successful, false otherwise.
     * @throws SQLException when something goes wrong with the INSERT operation.
     */
    public static boolean insertLocation(final Location location) throws SQLException {
        boolean successful = false;
        try (final Connection connection = getConnectionOrRetry()) {
            try (final Statement statement = connection.createStatement()) {
                String sql = String.format("INSERT INTO LOCATION (ID,NAME,QR_CODE_PATH)" +
                        " VALUES (%d, '%s', '%s');", location.getId(), location.getName(), "dummy"); // TODO use actual url
                successful = statement.execute(sql);
            }
        }
        return successful;
    }

    /**
     * Inserts a new connection into the database.
     * @param connection the {@link com.cerner.intern.traqr.core.Connection} to insert. Should not be null.
     * @return true if the insert was successful, false otherwise.
     * @throws SQLException when something goes wrong with the INSERT operation.
     */
    public static boolean insertConnection(final com.cerner.intern.traqr.core.Connection connection) throws SQLException {
        boolean successful = false;
        try (final Connection sqlConnection = getConnectionOrRetry()) {
            try (final Statement statement = sqlConnection.createStatement()) {
                String sql = String.format("INSERT INTO CONNECTION (PREV,NEXT,DESCRIPTION)" +
                        " VALUES (%d, %d, '%s');", connection.getStart().getId(), connection.getEnd().getId(),
                        connection.getDescription());
                successful = statement.execute(sql);
            }
        }
        return successful;
    }
}
