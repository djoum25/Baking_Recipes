package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeDetailsBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

import java.util.List;

public class RecipeDetails extends Fragment {
    private static final String ARG_RECIPE_PARAM = "recipe";
    private FragmentRecipeDetailsBinding mBinding;
    private List<Step> mRecipeSteps;

    private RecipeDetailsListener mListener;
    private Recipe mRecipe;

    public RecipeDetails () {
        // Required empty public constructor
    }

    public static RecipeDetails newInstance (Recipe recipes) {
        RecipeDetails fragment = new RecipeDetails();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE_PARAM, recipes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        try {
            mListener = (RecipeDetailsListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + "Must implement RecipeDetailsListener");
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE_PARAM);
            if (mRecipe != null) {
                mRecipeSteps = mRecipe.getSteps();
                mListener.chosenRecipe(mRecipe);
            }
        }
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mBinding = FragmentRecipeDetailsBinding.inflate(inflater);

        if (mRecipeSteps != null) {
            mBinding.setSteps(mRecipeSteps);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onResume () {
        super.onResume();
        if (mListener != null) {
            mListener.setToolbarTitle(mRecipe);
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        if (mListener != null) {
            mListener = null;
        }
    }


    public interface RecipeDetailsListener {
        void chosenRecipe (Recipe recipe);
        void setToolbarTitle (Recipe recipe);
    }
}
