package model.node;

/**
 * The Station class represents a station node in a track system.
 * It extends the Node class and inherits its properties and functionality.
 * Stations are specific types of nodes that can be used for boarding, alighting,
 * or transferring passengers in a track-based transportation system.
 */
public class Station extends Node {

    /**
     * Constructs a new Station object with the specified name and associates it with the given NodeTracker.
     *
     * @param tracker The NodeTracker instance to associate the station with.
     * @param name The name of the station.
     */
    public Station(NodeTracker tracker, String name) {
        super(tracker, name);
    }

}