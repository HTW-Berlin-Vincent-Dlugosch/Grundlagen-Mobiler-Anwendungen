package com.example.eventmapjava.logic;

import com.example.eventmapjava.model.Event;
import com.example.eventmapjava.model.User;

import java.time.LocalDateTime;

public interface EventManagementInterface {
    boolean authorize(int eventID);

    boolean requestDeletion(int eventID);

    Event requestEvent(int eventID);

    boolean modify(int eventID, double[] location, String name, User host, LocalDateTime startDateTime, LocalDateTime endDateTime);

    boolean requestInsertion(double[] location, String name, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
