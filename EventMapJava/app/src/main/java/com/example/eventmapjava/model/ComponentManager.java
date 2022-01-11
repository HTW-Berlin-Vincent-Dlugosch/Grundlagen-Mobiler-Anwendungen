package com.example.eventmapjava.model;

import com.example.eventmapjava.logic.EventManager;
import com.example.eventmapjava.logic.UserManager;

public class ComponentManager {
    private static ComponentManager singleton;
    private EventManager eventManager;
    private UserManager userManager;

    public static ComponentManager getComponentManager() {
        if (ComponentManager.singleton == null) {
            ComponentManager.singleton = new ComponentManager();
        }
        return ComponentManager.singleton;
    }

    public EventManager getEventManager(){
        if(eventManager == null){
            eventManager = new EventManager();
        }
        return  eventManager;
    }

    public UserManager getUserManager(){
        if (userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }
}
