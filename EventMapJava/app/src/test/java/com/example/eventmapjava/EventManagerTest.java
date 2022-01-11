package com.example.eventmapjava;

import com.example.eventmapjava.logic.EventManager;
import com.example.eventmapjava.model.ComponentManager;
import com.example.eventmapjava.model.Event;
import com.example.eventmapjava.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventManagerTest {
    ComponentManager componentManager = new ComponentManager();
    EventManager mgr = componentManager.getEventManager();

    @Before
    public void setUp() {
        mgr.requestInsertion(new double[]{0.5, 0.5}, "Party1", new User(), LocalDateTime.now(), LocalDateTime.now());
        mgr.requestInsertion(new double[]{0.5, 0.5}, "Party2", new User(), LocalDateTime.now(), LocalDateTime.now());
        mgr.requestInsertion(new double[]{0.5, 0.5}, "Party3", new User(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void testIfEventListGrowsOnStoringEvents() {
        Assert.assertEquals(3, mgr.getEventList().size());
        mgr.requestInsertion(new double[]{0.5, 0.5}, "Party", new User(), LocalDateTime.now(), LocalDateTime.now());
        Assert.assertEquals(4, mgr.getEventList().size());
    }

    //Flaky Test
    @Test
    public void testForUniqueIds() {
        ArrayList<Event> events = new ArrayList<>(mgr.getEventList());
        int id1 = events.get(0).getEventID();
        int id2 = events.get(1).getEventID();
        int id3 = events.get(2).getEventID();
        System.out.println(id1 + "." + id2 + "." + id3);
        boolean diffrentIDS = id1 == id2 && id1 == id3;
        Assert.assertFalse(diffrentIDS);
    }

    @Test
    public void testDeletion() {
        ArrayList<Event> events = new ArrayList<>(mgr.getEventList());
        int id1 = events.get(0).getEventID();
        Assert.assertTrue(mgr.requestDeletion(id1));
        ArrayList<Event> eventsAfterDeleteion = new ArrayList<>(mgr.getEventList());
        for (Event e : eventsAfterDeleteion) {
            if (e.getEventID() == id1) {
                Assert.fail();
            }
        }
        Assert.assertEquals(2, mgr.getEventList().size());
    }

    @Test
    public void testDeletionRandomID() {
        Assert.assertFalse(mgr.requestDeletion(23423));
        Assert.assertEquals(3, mgr.getEventList().size());
    }
}
