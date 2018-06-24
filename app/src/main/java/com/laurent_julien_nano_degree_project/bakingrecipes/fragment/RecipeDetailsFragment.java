package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsHelper;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeDetailsBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;
import com.laurent_julien_nano_degree_project.bakingrecipes.widget.RecipeWidgetProvider;

import java.util.List;

import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_INGREDIENTS;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_MEASURE;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_QUANTITY;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_RECIPE_NAME;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.CONTENT_URI;

public class RecipeDetailsFragment extends Fragment {
    private static final String ARG_RECIPE_PARAM = "recipe";
    private List<Step> mRecipeSteps;
    FragmentRecipeDetailsBinding mBinding;

    private RecipeDetailsListener mListener;
    private Recipe mRecipe;
    private List<Ingredient> mIngredientList;
    private RecipeIngredientsHelper mHelper;

    public RecipeDetailsFragment () {
        // Required empty public constructor
    }

    public static RecipeDetailsFragment newInstance (Recipe recipes) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
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
                mIngredientList = mRecipe.getIngredients();
                mListener.chosenRecipe(mRecipe);
            }
        }
        mHelper = new RecipeIngredientsHelper(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mBinding = FragmentRecipeDetailsBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onResume () {
        super.onResume();
        if (mRecipe != null) {
            mBinding.setIngredients(mIngredientList);
            mBinding.setSteps(mRecipeSteps);
        }
    }

    @Override
    public void onDestroy () {
        if (mHelper != null)
            mHelper.close();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ingredients_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_desired_recipe_action:
                final List<Ingredient> ingredients = mRecipe.getIngredients();
                addIngredientsToWidget(ingredients);
                RecipeWidgetProvider.refresh(getContext());
                return true;
        }
        return false;
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
    }

    private void addIngredientsToWidget (List<Ingredient> ingredients) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        getContext().getContentResolver().delete(CONTENT_URI, null, null);
        ContentValues values = new ContentValues();
        long insert = 0;
        if (ingredients != null) {
            values.put(COLUMN_RECIPE_NAME, mRecipe.getName());
            for (Ingredient ingredient : ingredients) {
                values.put(COLUMN_INGREDIENTS, ingredient.getIngredient());
                values.put(COLUMN_QUANTITY, ingredient.getQuantity());
                values.put(COLUMN_MEASURE, ingredient.getMeasure());
                final Uri result = getContext().getContentResolver().insert(CONTENT_URI, values);
            }
        }
    }

}
