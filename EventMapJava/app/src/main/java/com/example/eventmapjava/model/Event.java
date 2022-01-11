package com.example.eventmapjava.model;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Event {
    private int eventID;
    private double[] location;
    private String name;
    private User host;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Event(double[] location, String name, User host,  LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.location = location;
        this.name = name;
        this.host = host;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Event(int eventID, double[] location, String name, User host,  LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.eventID = eventID;
        this.location = location;
        this.name = name;
        this.host = host;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getEventID() {
        return eventID;
    }

    public double[] getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public User getHost() {
        return host;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", location=" + Arrays.toString(location) +
                ", name='" + name + '\'' +
                ", host=" + host +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
