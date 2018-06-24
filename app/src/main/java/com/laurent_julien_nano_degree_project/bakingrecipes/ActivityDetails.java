package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.ActivityDetailsBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeDetailsFragment;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeStepsFragment;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

public class ActivityDetails extends AppCompatActivity implements IMainActivity, RecipeDetailsFragment.RecipeDetailsListener {

    private static final String TAG = ActivityDetails.class.getSimpleName();
    private Recipe mRecipe;
    private ActivityDetailsBinding mBinding;
    private boolean isDualPane;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        if (mBinding.details != null &&
            mBinding.details.getVisibility() == View.VISIBLE) {
            isDualPane = true;
        }

        mRecipe = getIntent().getParcelableExtra(getString(R.string.recipe_extra_key));
        if (mRecipe != null) {
            mBinding.setRecipe(mRecipe);
            init(savedInstanceState);
        }

        setSupportActionBar(mBinding.toolbarActivityDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.state_save), mRecipe);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    public void init (Bundle saveInstanceState) {
        if (saveInstanceState != null)
            return;
        int size = mRecipe.getSteps().size();
        Step step = mRecipe.getSteps().get(0);
        RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(mRecipe);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.detail_list, detailsFragment)
            .addToBackStack(null)
            .commit();

        if (isDualPane) {
            RecipeStepsFragment stepsFragment =
                RecipeStepsFragment.newInstance(step, size);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.details, stepsFragment)
                .addToBackStack(null)
                .commit();
        }

        Log.d(TAG, "init");
    }

    @Override
    public void onRecipeShortDescriptionClick (Step step) {
        displayRecipeStep(step);
    }

    @Override
    public void onNextButtonClick (int actualStepId) {
        Step stepToDisplay = null;
        if (actualStepId <= mRecipe.getSteps().size()) {
            //this line of code is specifici for the yellow
            // cake recipe which id came jump from 6 to 8
            // going up
            if (mRecipe.getName().equals("Yellow Cake") && actualStepId == 6) {
                actualStepId += 1;
            }
            actualStepId += 1;
            for (Step step : mRecipe.getSteps()) {
                if (step.getId() == actualStepId) {
                    stepToDisplay = step;
                }
            }
            if (stepToDisplay != null) {
                displayRecipeStep(stepToDisplay);
            }
        }
    }

    @Override
    public void onPreviousButtonClick (int actualStepId) {
        Step stepToDisplay = null;
        if (actualStepId > 0) {
            //this line of code is specifici for the yellow
            // cake recipe which id came jump from 8 to 6 going down
            if (mRecipe.getName().equals(getString(R.string.yellow_cake)) && actualStepId == 8) {
                actualStepId -= 1;
            }
            actualStepId -= 1;
            for (Step step : mRecipe.getSteps()) {
                if (step.getId() == actualStepId) {
                    stepToDisplay = step;
                }
            }
            if (stepToDisplay != null) {
                displayRecipeStep(stepToDisplay);
            }
        }
    }

    private void displayRecipeStep (Step step) {
        RecipeStepsFragment recipeSteps;
        final FragmentManager fragmentManager = getSupportFragmentManager();
        int size = 0;
        //yellow cake id is different
        if (mRecipe.getName().equals(getString(R.string.yellow_cake))) {
            size = mRecipe.getSteps().size();
        } else {
            size = (mRecipe.getSteps().size() - 1);
        }

        if (step != null) {
            recipeSteps = RecipeStepsFragment.newInstance(step, size);
            if (isDualPane) {
                fragmentManager.beginTransaction()
                    .replace(R.id.details, recipeSteps)
                    .addToBackStack(null)
                    .commit();
            } else {
                fragmentManager.beginTransaction()
                    .replace(R.id.detail_list, recipeSteps)
                    .addToBackStack(null)
                    .commit();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipe = savedInstanceState.getParcelable(getString(R.string.state_save));
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                removeBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void chosenRecipe (Recipe recipe) {
        mRecipe = recipe;
    }

    @Override
    public void onBackPressed () {
        removeBackStack();
    }

    private void removeBackStack () {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (!isDualPane) {
            if (backStackEntryCount > 1)
                getSupportFragmentManager().popBackStack();
            else
                NavUtils.navigateUpFromSameTask(this);
        } else {
            if (backStackEntryCount > 2)
                getSupportFragmentManager().popBackStack();
            else NavUtils.navigateUpFromSameTask(this);
        }
    }
}
