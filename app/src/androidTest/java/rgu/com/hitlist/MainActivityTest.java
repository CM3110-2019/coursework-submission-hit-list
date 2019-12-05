package rgu.com.hitlist;

import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rgu.com.hitlist.activities.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testWatchListButton() {
        onView(withId(R.id.actionWatchList)).perform(click());
    }

    @Test
    public void testSearchButton() {
        onView(withId(R.id.actionSearch)).perform(click()); // click on search
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText("query"), pressImeActionButton()); // type a query and press enter
    }

    @Test
    public void testLanguageButton() {
        onView(withId(R.id.actionLanguage)).perform(click());
        onView(isAssignableFrom(MenuPopupWindow.MenuDropDownListView.class)).perform(click());
    }

    @Test
    public void testAdapterView() {
        onView(withId(R.id.rvTrending)).perform(click());
    }
}
