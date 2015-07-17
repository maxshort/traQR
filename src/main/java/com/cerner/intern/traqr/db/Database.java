package com.cerner.intern.traqr.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cerner.intern.traqr.core.Location;

/**
 * Represents the database in which traQR data is stored.
 */
public class Database {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    private static final String LAST_INSERT_ROWID = "SELECT last_insert_rowid();";
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
            try (final PreparedStatement statementLocation = connection.prepareStatement(
                    "CREATE TABLE LOCATION (" +
                    " ID   INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME          TEXT        NOT NULL," +
                    " QR_CODE_PATH  CHAR(80))"
            )) {
                statementLocation.execute();
            }
            try (final PreparedStatement statementConnection = connection.prepareStatement(
                    "CREATE TABLE CONNECTION (" +
                    " PREV   INTEGER    NOT NULL," +
                    " NEXT   INTEGER    NOT NULL," +
                    " DESCRIPTION TEXT NOT NULL," +
                    " DURATION    INTEGER  NOT NULL," +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT)")) {
                statementConnection.executeUpdate();
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
     * @param name the name of the location to insert. Should not be null.
     * @return the {@link Location}.
     * @throws SQLException when something goes wrong with the INSERT operation.
     */
    public static Location insertLocation(final String name) throws SQLException {
        boolean successful = false;
        try (final Connection connection = getConnectionOrRetry()) {
            try (final PreparedStatement statement = connection.prepareStatement(
                    String.format("INSERT INTO LOCATION (NAME,QR_CODE_PATH)" +
                            " VALUES ('%s', '%s');", name, "dummy" // TODO Use actual url
            ))) {
                successful = statement.execute();
            }
            try (final PreparedStatement stmt = connection.prepareStatement(LAST_INSERT_ROWID)) {
                try (final ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    int lastInsertRowId = rs.getInt(1);
                    return new Location(lastInsertRowId, name);
                }
            }
        }
    }

    /**
     * Inserts a new connection.
     * @param description
     * @param start
     * @param end
     * @param estimatedTime
     * @return the {@link com.cerner.intern.traqr.core.Connection}.
     * @throws SQLException when something goes wrong with the INSERT operation.
     */
    public static com.cerner.intern.traqr.core.Connection insertConnection(String description, Location start, Location end, Duration estimatedTime) throws SQLException {
        boolean successful = false;
        try (final Connection sqlConnection = getConnectionOrRetry()) {
            try (final PreparedStatement statement = sqlConnection.prepareStatement(String.format("INSERT INTO CONNECTION (PREV,NEXT,DESCRIPTION,DURATION)" +
                            " VALUES (%d, %d, '%s', %d);", start.getId(), end.getId(),
                    description, estimatedTime.toMillis()))) {
                successful = statement.execute();
            }
            try (final PreparedStatement stmt = connection.prepareStatement(LAST_INSERT_ROWID)) {
                try (final ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    int lastInsertRowId = rs.getInt(1);
                    return new com.cerner.intern.traqr.core.Connection(lastInsertRowId, description, start, end, estimatedTime);
                }
            }
        }
    }

    public static Map<Integer, Location> getAllLocationsById() {
        try {
            return getLocationsByQuery("SELECT ID, NAME FROM LOCATION;");
        } catch (SQLException e) {
            LOGGER.error("Error occurred while retrieving all locations", e);
            return new HashMap<>();
        }
    }

    public static Map<Integer, com.cerner.intern.traqr.core.Connection> getAllConnectionsById() {
        Map<Integer, Location> locations = getAllLocationsById();
        try {
            return getConnectionsByQuery("SELECT PREV, NEXT, DESCRIPTION, DURATION, ID FROM CONNECTION;", locations);
        } catch (SQLException e) {
            LOGGER.error("Error occurred while retrieving all connections", e);
            return new HashMap<>();
        }
    }

    public static Map<Integer, Location> getLocationsById(final Set<Long> ids) throws SQLException {
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
        String sql = String.format("SELECT ID, NAME FROM LOCATION WHERE ID IN (%s);", stringBuilder.toString());
        return getLocationsByQuery(sql);
    }

    public static Map<Integer, com.cerner.intern.traqr.core.Connection> getConnectionsByStartLocation(final Map<Integer, Location> locations) throws SQLException {
        if (locations == null || locations.isEmpty()) {
            throw new IllegalArgumentException("Locations cannot be null or empty.");
        }
        final Map<Integer, com.cerner.intern.traqr.core.Connection> connections = new HashMap<>();
        final StringBuilder stringBuilder = new StringBuilder(128);
        locations.keySet().forEach(anId -> {
            if (anId != null) {
                stringBuilder.append(anId + ", ");
            }
        });
        // Delete trailing comma and space
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        String sql = String.format("SELECT PREV, NEXT, DESCRIPTION, DURATION, ID FROM CONNECTION WHERE PREV in (%d);", stringBuilder.toString());
        return getConnectionsByQuery(sql, locations);
    }

    private static Map<Integer, Location> getLocationsByQuery(final String sql) throws SQLException {
        final Map<Integer, Location> locations = new HashMap<>();
        try (final Connection sqlConnection = getConnectionOrRetry()) {
            try (final PreparedStatement statement = sqlConnection.prepareStatement(sql)) {
                try (final ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        final Integer id = rs.getInt(1);
                        final Location location = new Location(id, rs.getString(2));
                        locations.put(id, location);
                    }
                }
            }
        }
        return locations;
    }

    private static Map<Integer, com.cerner.intern.traqr.core.Connection> getConnectionsByQuery(final String sql, final Map<Integer, Location> locations) throws SQLException {
        final HashMap<Integer, com.cerner.intern.traqr.core.Connection> connections = new HashMap<>();
        try (final Connection sqlConnection = getConnectionOrRetry()) {
            try (final PreparedStatement statement = sqlConnection.prepareStatement(sql)) {
                try (final ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        final int prev = rs.getInt(1);
                        final int next = rs.getInt(2);
                        final String description = rs.getString(3);
                        final int duration = rs.getInt(4);
                        final int id = rs.getInt(5);
                        final com.cerner.intern.traqr.core.Connection connection =
                                new com.cerner.intern.traqr.core.Connection(id, description, locations.get(prev),
                                        locations.get(next), Duration.ofMillis(duration));
                        connections.put(prev, connection);
                    }
                }
            }
        }
        return connections;
    }

    public static void main(String args[]) throws SQLException {
        createTables();
        System.out.println(insertLocation("foobah"));
        System.out.println(insertLocation("borg"));

        Collection<Location> locations = getAllLocationsById().values();
        locations.forEach(aConsumer -> {
            System.out.println(aConsumer.getId() + " " + aConsumer.getName());
        });
        Iterator<Location> iterator = locations.iterator();
        System.out.println(insertConnection("I am a description.", iterator.next(), iterator.next(), Duration.ofMinutes(10)));
        Collection<com.cerner.intern.traqr.core.Connection> connections = getAllConnectionsById().values();
        connections.forEach(aConnection -> {
            System.out.println(aConnection.getId() + " " + aConnection.getStart() + " " + aConnection.getEnd() + " " + aConnection.getDescription() + " " + aConnection.getEstimatedTime());
        });
    }
}
