package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.ActivityMainBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeNameList;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipes;

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

    @Override
    public void onRecipeNameClick (Recipes recipes) {
        Toast.makeText(this, recipes.getName(), Toast.LENGTH_SHORT).show();
    }
}
