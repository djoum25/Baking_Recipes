package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.ActivityMainBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeDetails;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeNameList;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initListFragment();
    }

    private void initListFragment(){
        RecipeNameList nameList = new RecipeNameList();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.container_list, nameList)
            .commit();
    }

    private void attachRecipeDetailFragment(Recipe recipe){
        RecipeDetails recipeDetails = RecipeDetails.newInstance(recipe);
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.container_list, recipeDetails)
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onRecipeNameClick (Recipe recipes) {
        attachRecipeDetailFragment(recipes);
    }

    @Override
    public void onBackPressed () {
        final int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount >= 2){
            getSupportFragmentManager().popBackStack();
        }else {
        super.onBackPressed();
        }
    }
}
