package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.laurent_julien_nano_degree_project.bakingrecipes.adapter.RecipeNameAdapter;
import com.laurent_julien_nano_degree_project.bakingrecipes.adapter.RecipeStepsAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(JUnit4.class)
public class RecipeNameListTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecipeNameListRecyclerView () {
        onView(
            allOf(
                withId(R.id.recipe_name_list)))
            .perform(RecyclerViewActions.<RecipeNameAdapter.RecipeBindingHolder>
                actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.recipe_name_details_list))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.recipe_name_details_list)))
            .perform(RecyclerViewActions.<RecipeStepsAdapter.RecipeStepsBidingHolder>
                actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredient_title))
            .check(matches(withText("List of ingredients to prepare a  Nutella Pie")));
    }
}