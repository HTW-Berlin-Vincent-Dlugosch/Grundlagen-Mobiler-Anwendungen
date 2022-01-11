package com.example.eventmapjava;

import androidx.annotation.ContentView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import com.example.eventmapjava.gui.CreateEvent;
import com.example.eventmapjava.gui.EventsMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class EventCreationTest {
    @Rule
    public ActivityScenarioRule<CreateEvent> activityScenarioRule = new ActivityScenarioRule<>(CreateEvent.class);
    @Test
    public void testEventCreation(){
        onView(withId(R.id.event_name_input)).perform(typeText("TestEvent123"));
        onView(withId(R.id.date_pick_button)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.start_time_button)).perform(click());
     // onView(withText("10")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.end_time_button)).perform(click());
        // onView(withText("12")).perform(click());
        onView(withText("OK")).perform(click());
    }

}
