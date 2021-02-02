package de.hdmstuttgart.checkin;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.checkin.activities.HomeActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class checkInTest {
    
    @Rule
    public ActivityScenarioRule<HomeActivity> activityActivityScenarioRule
            = new ActivityScenarioRule<>(HomeActivity.class);
    
    @Test
    public void checkInTest(){

        //Opening the statistics activity and clearing the history
        onView(withId(R.id.statisticsButton))
                .perform(click());

        onView(withId(R.id.deleteButton))
                .perform(click());

        Espresso.pressBack();

        //Switching theme + showing all activities in the changed theme
        onView(withId(R.id.settingsButton))
                .perform(click());

        onView(withId(R.id.themeSwitch))
                .perform(swipeRight());

        Espresso.pressBack();

        onView(withId(R.id.statisticsButton))
                .perform(click());

        Espresso.pressBack();

        onView(withId(R.id.settingsButton))
                .perform(click());

        onView(withId(R.id.themeSwitch))
                .perform(swipeLeft());

        Espresso.pressBack();
    }

}
