package com.example.eventmapjava.logic;

import android.util.Log;

import com.example.eventmapjava.model.ComponentManager;
import com.example.eventmapjava.model.Event;
import com.example.eventmapjava.model.EventStorage;
import com.example.eventmapjava.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public class EventManager implements EventManagementInterface {
    EventStorage events = new EventStorage();

    @Override
    public boolean authorize( int eventID) {
        return requestEvent(eventID).getHost().getUserID() == ComponentManager.getComponentManager().getUserManager().getCurrentUser().getUserID();
    }

    @Override
    public boolean requestDeletion(int eventID) {
        return events.deleteEvent(eventID);
    }

    @Override
    public Event requestEvent(int eventID) {
        return events.getEvent(eventID);
    }

    @Override
    public boolean modify(int eventID, double[] location, String name, User host, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return events.replaceEvent(new Event(eventID, location, name, host, startDateTime, endDateTime));
    }

    @Override
    public boolean requestInsertion(double[] location, String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return events.addEvent(new Event(location, name, ComponentManager.getComponentManager().getUserManager().getCurrentUser(), startDateTime, endDateTime));
    }

    public void showAllEvents() {
        Map<Integer, Event> eventMap = events.getAllEvents();
        Collection<Event> eventCollection = eventMap.values();
        Log.d("AlleEvents", eventCollection.toString());
    }

    public Collection<Event> getEventList(){
        return events.getAllEvents().values();
    }
}
