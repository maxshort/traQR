package com.cerner.intern.traqr.core;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created on 7/14/15.
 */
public class Trip {

    private List<Connection> connectionList;
    private Optional<Duration> estimatedDuration = Optional.empty(); //will be cached once calced


    public Trip(List<Connection> connectionList) {
        this.connectionList = new ArrayList<>(connectionList);
    }

    public List<Connection> getConnections() {
        return new ArrayList<>(connectionList);
    }

    //TODO: not sure how this will display...might want to make more user-friendly
    public Duration getEstimatedTime() {
        if (!estimatedDuration.isPresent()) {
            estimatedDuration = Optional.of(
                    connectionList.stream()
                            .map(connection -> connection.getEstimatedTime())
                            .reduce(Duration.ofSeconds(0),
                                    (Duration initial, Duration latest) -> initial.plus(latest)));
        }
        return estimatedDuration.get();

    }
}
