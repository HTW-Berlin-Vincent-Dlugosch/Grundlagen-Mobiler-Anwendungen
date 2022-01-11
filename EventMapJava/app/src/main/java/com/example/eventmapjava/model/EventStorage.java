package com.example.eventmapjava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class EventStorage implements EventStorageInterface{
    Map<Integer, Event> eventList = new HashMap<>();

    @Override
    public Event getEvent(int eventID) {
       return eventList.get(eventID);
    }

    @Override
    public boolean deleteEvent(int eventID) {
        if(eventList.containsKey(eventID)){
            eventList.remove(eventID);
            return true;
        }
        return false;
    }

    @Override
    public boolean addEvent(Event e) {
        int eventID = new Random().nextInt(Integer.MAX_VALUE);
        e.setEventID(eventID);
        eventList.put(eventID, e);
        return true;
    }

    @Override
    public boolean replaceEvent(Event e) {
        eventList.put(e.getEventID(), e);
        return true;
    }


    @Override
    public Map<Integer, Event> getAllEvents() {
        return eventList;
    }
}
