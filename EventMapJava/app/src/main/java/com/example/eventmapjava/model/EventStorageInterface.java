package com.example.eventmapjava.model;

import java.util.Map;

public interface EventStorageInterface {
    Event getEvent(int eventID);

    boolean deleteEvent(int eventID);

    boolean addEvent(Event e);

    boolean replaceEvent(Event e);

    Map<Integer, Event> getAllEvents();
}
