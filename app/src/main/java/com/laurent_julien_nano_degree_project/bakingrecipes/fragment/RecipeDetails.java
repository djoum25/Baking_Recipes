package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeDetailsBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

import java.util.List;

public class RecipeDetails extends Fragment {
    private static final String ARG_RECIPE_PARAM = "recipe";

    private Recipe mRecipe;
    private FragmentRecipeDetailsBinding mBinding;
    private List<Step> mRecipeSteps;

    public RecipeDetails () {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecipeDetails newInstance (Recipe recipes) {
        RecipeDetails fragment = new RecipeDetails();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE_PARAM, recipes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           mRecipe = getArguments().getParcelable(ARG_RECIPE_PARAM);
            if (mRecipe != null) {
                mRecipeSteps = mRecipe.getSteps();
            }
        }

        for (Step recipeStep : mRecipeSteps) {
            Log.d("TEST", "onCreate: " + recipeStep.getShortDescription());
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mBinding = FragmentRecipeDetailsBinding.inflate(inflater);
        return mBinding.getRoot();
    }

}
