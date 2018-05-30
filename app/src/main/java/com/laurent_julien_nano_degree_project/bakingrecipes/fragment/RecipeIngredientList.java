package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeIngredientListBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;

import java.util.List;


public class RecipeIngredientList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    FragmentRecipeIngredientListBinding mBinding;
    private Recipe mRecipe;

    private IngredientListener mListener;

    public RecipeIngredientList () {
        // Required empty public constructor
    }

    public static RecipeIngredientList newInstance (Recipe recipe) {
        RecipeIngredientList fragment = new RecipeIngredientList();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        if (context instanceof IngredientListener) {
            mListener = (IngredientListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        mBinding = FragmentRecipeIngredientListBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onResume () {
        super.onResume();
        if (mRecipe != null) {
            mBinding.setRecipe(mRecipe);
            final List<Ingredient> ingredients = mRecipe.getIngredients();
            mBinding.setIngredients(ingredients);
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        mListener = null;
    }

    public interface IngredientListener {


    }
}
