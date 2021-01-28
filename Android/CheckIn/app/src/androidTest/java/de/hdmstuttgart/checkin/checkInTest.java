package de.hdmstuttgart.checkin;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.checkin.activities.HomeActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class checkInTest {
    
    @Rule
    public ActivityScenarioRule<HomeActivity> activityActivityScenarioRule
            = new ActivityScenarioRule<>(HomeActivity.class);
    
    @Test
    public void checkInTest(){
        onView(withId(R.id.statisticsButton))
                .perform(click());

        onView(withId(R.id.deleteButton))
                .perform(click());

        onView(withId(R.id.backButtonStatistics))
                .perform(click());

        onView(withId(R.id.settingsButton))
                .perform(click());

        onView(withId(R.id.backButtonSettings))
                .perform(click());
    }

}
