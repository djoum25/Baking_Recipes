package com.laurent_julien_nano_degree_project.bakingrecipes;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeFullTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition (
        final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo (Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely (View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                    && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void recipeFullTest () {
        ViewInteraction recyclerView = onView(
            allOf(withId(R.id.recipe_name_list),
                childAtPosition(
                    withId(R.id.recipe_name_list_container),
                    0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
            allOf(withId(R.id.recipe_name_details_list),
                childAtPosition(
                    withClassName(is("android.widget.LinearLayout")),
                    0)));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
            allOf(withId(R.id.btn_next), withText("Up next"),
                childAtPosition(
                    childAtPosition(
                        withClassName(is("android.widget.ScrollView")),
                        0),
                    6)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
            allOf(withId(R.id.txt_description), withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed."),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                        0),
                    4),
                isDisplayed()));
        textView.check(matches(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")));

        ViewInteraction textView2 = onView(
            allOf(withId(R.id.txt_description), withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed."),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                        0),
                    4),
                isDisplayed()));
        textView2.check(matches(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")));

        ViewInteraction appCompatButton2 = onView(
            allOf(withId(R.id.btn_next), withText("Up next"),
                childAtPosition(
                    childAtPosition(
                        withClassName(is("android.widget.ScrollView")),
                        0),
                    6)));
        appCompatButton2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView3 = onView(
            allOf(withId(R.id.txt_description), withText("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature."),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                        0),
                    4),
                isDisplayed()));
        textView3.check(matches(withText("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.")));

        ViewInteraction appCompatButton3 = onView(
            allOf(withId(R.id.btn_next), withText("Up next"),
                childAtPosition(
                    childAtPosition(
                        withClassName(is("android.widget.ScrollView")),
                        0),
                    6)));
        appCompatButton3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView4 = onView(
            allOf(withId(R.id.txt_description), withText("4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy."),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                        0),
                    4),
                isDisplayed()));
        textView4.check(matches(withText("4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.")));

        ViewInteraction appCompatButton4 = onView(
            allOf(withId(R.id.btn_next), withText("Up next"),
                childAtPosition(
                    childAtPosition(
                        withClassName(is("android.widget.ScrollView")),
                        0),
                    6)));
        appCompatButton4.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView5 = onView(
            allOf(withId(R.id.txt_description), withText("5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form."),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                        0),
                    4),
                isDisplayed()));
        textView5.check(matches(withText("5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.")));

        ViewInteraction appCompatButton5 = onView(
            allOf(withId(R.id.btn_next), withText("Up next"),
                childAtPosition(
                    childAtPosition(
                        withClassName(is("android.widget.ScrollView")),
                        0),
                    6)));
        appCompatButton5.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView6 = onView(
            allOf(withId(R.id.txt_description), withText("6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                        0),
                    4),
                isDisplayed()));
        textView6.check(matches(withText("6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!")));

    }
}
