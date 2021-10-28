package ch.epfl.sweng.todoapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    String text = "Hello world!";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("ch.epfl.sweng.todoapp", appContext.getPackageName());
    }

    @Test
    public void addTodoItem() {
        onView(withId(R.id.new_item_field)).perform(typeText(text));
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.item_text)).check(matches(withText(text)));
    }

    @Test
    public void removeItem() {
        onView(withId(R.id.new_item_field)).perform(typeText(text));
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.new_item_field)).perform(typeText("And another one"));
        onView(withId(R.id.add_button)).perform(click());
        onView(allOf(withId(R.id.item_delete_button), hasSibling(withText(text)))).perform(click());
        onView(withText(text)).check(doesNotExist());
    }
}
