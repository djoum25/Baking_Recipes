package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter.RecipeNameBindingAdapter;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.ActivityMainBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeDetails;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeIngredientList;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeNameList;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

public class MainActivity extends AppCompatActivity implements IMainActivity,
    RecipeDetails.RecipeDetailsListener, RecipeIngredientList.IngredientListener {

    private static final String TAG = "MainActivity";
    ActivityMainBinding mBinding;
    private Recipe mRecipe, saveRecipe;
    private boolean mTablet;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ScreenSizeUtility screenSizeUtility = new ScreenSizeUtility(this);
        mTablet = screenSizeUtility.getWidth() >= 800;
        // TODO: 5/29/18 rememenber to remove if not working 
        RecipeNameBindingAdapter recipeNameBindingAdapter = new RecipeNameBindingAdapter(this);

        if (mTablet || (savedInstanceState == null)) {
            initListFragment();
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("state_save", saveRecipe);
    }

    private void initListFragment () {
        RecipeNameList nameList = new RecipeNameList();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.container_list, nameList)
            .commit();
    }

    private void attachRecipeDetailFragment (Recipe recipe) {
        RecipeDetails recipeDetails = RecipeDetails.newInstance(recipe);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (mTablet) {
            mBinding.containerDetails.setVisibility(View.VISIBLE);
        }
        fragmentManager.beginTransaction()
            .replace(R.id.container_list, recipeDetails)
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onRecipeNameClick (Recipe recipes) {
        attachRecipeDetailFragment(recipes);
    }

    @Override
    public void onRecipeShortDescriptionClick (Step step) {
        Toast.makeText(this, step.getDescription(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRecipeIngredientClick () {
        RecipeIngredientList recipeIngredientList;
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (mRecipe != null) {
            recipeIngredientList = RecipeIngredientList.newInstance(mRecipe);
            if (mTablet) {
                fragmentManager.beginTransaction()
                    .replace(R.id.container_details, recipeIngredientList)
                    .addToBackStack(null)
                    .commit();
            } else {
                fragmentManager.beginTransaction()
                    .replace(R.id.container_list, recipeIngredientList)
                    .addToBackStack(null)
                    .commit();
            }
        }
    }

    @Override
    public void onBackPressed () {
        final int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount >= 2) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (mTablet)
                mBinding.containerDetails.setVisibility(View.GONE);
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        mBinding.toolbar.setTitle(getString(R.string.app_name));
    }

    @Override
    public void chosenRecipe (Recipe recipe) {
        mRecipe = recipe;
    }

    @Override
    public void setToolbarTitle (Recipe recipe) {
        mBinding.toolbar.setTitle(recipe.getName());
    }
}
