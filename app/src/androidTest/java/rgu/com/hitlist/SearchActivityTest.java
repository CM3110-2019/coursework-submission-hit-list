package rgu.com.hitlist;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rgu.com.hitlist.activities.MainActivity;
import rgu.com.hitlist.activities.SearchActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(SearchActivity.class);

    @Test
    public void testBackButton() {
        pressBack();
    }

    @Test
    public void testFilterButton() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText(R.string.actionPeople)).perform(click());
    }

    @Test
    public void testAdapterView() {
        onView(withId(R.id.rvSearch)).perform(click());
    }
}
