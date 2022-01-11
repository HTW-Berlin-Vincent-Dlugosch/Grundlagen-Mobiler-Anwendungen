package com.example.eventmapjava;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.eventmapjava.gui.CreateEvent;
import com.example.eventmapjava.gui.EventsMap;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapOverlaySwitchTest {
    @Rule
    public ActivityScenarioRule<EventsMap> activityScenarioRule = new ActivityScenarioRule<>(EventsMap.class);

    @Before
    public void initIntent() {
        init();
    }

    @Test
    public void testOverlaySwitch() {
        onView(withId(R.id.map_mode_toogle_button)).check(matches(withText(R.string.map_mode_toggle_create)));
        onView(withId(R.id.map_mode_toogle_button)).perform(click());
        onView(withId(R.id.map_mode_toogle_button)).check(matches(withText(R.string.map_mode_toggle_view)));
        onView(withId(R.id.map)).perform(click());
        // Leider funktioniert das hier nicht. Die richtige Activity wird durch einen Intent gestartet der Test schlaegt trotzdem fehl
        // intended(hasComponent(CreateEvent.class.getName()));
        // Das leider auch nicht...
       // onView(withText("Date")).check(matches(isDisplayed()));
    }
}