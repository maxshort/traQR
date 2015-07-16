package com.cerner.intern.traqr.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                        " DURATION    INT  NOT NULL," +
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

    public static List<Location> getLocationsById(final Set<Long> ids) throws SQLException {
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("Ids cannot be empty.");
        }
        final StringBuilder stringBuilder = new StringBuilder(128);
        ids.forEach(aLong -> {
            if (aLong != null) {
                stringBuilder.append(aLong + ", ");
            }
        });
        // Delete trailing comma and space
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        System.out.println(stringBuilder.toString());
        final List<Location> locations = new ArrayList<>();
        String sql = String.format("SELECT ID, NAME FROM LOCATION WHERE ID IN (%s);", stringBuilder.toString());
        try (final Connection sqlConnection = getConnectionOrRetry()) {
            try (final Statement statement = sqlConnection.createStatement()) {
                try (final ResultSet rs = statement.executeQuery(sql)) {
                    while (rs.next()) {
                        final Location location = new Location(rs.getInt(1), rs.getString(2));
                        locations.add(location);
                    }
                }
            }
        }
        return locations;
    }

    public static List<com.cerner.intern.traqr.core.Connection> getConnectionsByStartLocation(final Location location) throws SQLException {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }
        final List<com.cerner.intern.traqr.core.Connection> connections = new ArrayList<>();
        String sql = String.format("SELECT PREV, NEXT, DESCRIPTION FROM CONNECTION WHERE PREV = %d;", location.getId());
        try (final Connection sqlConnection = getConnectionOrRetry()) {
            try (final Statement statement = sqlConnection.createStatement()) {
                try (final ResultSet rs = statement.executeQuery(sql)) {
//                    while (rs.next()) {
//                        final com.cerner.intern.traqr.core.Connection connection = new com.cerner.intern.traqr.core.Connection(rs.getInt(1), rs.getString(3), rs.getInt(1), rs.getInt(2), 0);
//                        connections.add(connection);
//                    }
                }
            }
        }
        return connections;
    }

    public static void main(String args[]) throws SQLException {
        createTables();
        insertLocation(new Location(234, "foobah"));
        insertLocation(new Location(235, "borg"));
        Set<Long> ids = new HashSet<>(2);
        ids.add(234L);
        ids.add(235L);
        List<Location> locations = getLocationsById(ids);
        locations.forEach(aConsumer -> {
            System.out.println(aConsumer.getId() + " " + aConsumer.getName());
        });
    }
}
