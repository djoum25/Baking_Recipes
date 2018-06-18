package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract;
import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry;
import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsHelper;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeIngredientListBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;

import java.util.List;

import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.*;


public class RecipeIngredientList extends Fragment {
    private RecipeIngredientsHelper mHelper;

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
        mHelper = new RecipeIngredientsHelper(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ingredients_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_to_desired_recipe_action:
                final List<Ingredient> ingredients = mRecipe.getIngredients();
                 addIngredientsToWidget(ingredients);
                return true;
        }
        return false;
    }

    private void addIngredientsToWidget(List<Ingredient> ingredients){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        ContentValues values = new ContentValues();
        long insert = 0;
        if (ingredients != null){
            values.put(COLUMN_RECIPE_NAME, mRecipe.getName());
            for (Ingredient ingredient : ingredients) {
                values.put(COLUMN_INGREDIENTS, ingredient.getIngredient());
                values.put(COLUMN_QUANTITY, ingredient.getQuantity());
                values.put(COLUMN_MEASURE, ingredient.getMeasure());
                insert += db.insert(TABLE_NAME, null, values);
            }
        }
    }

    @Override
    public void onDestroy () {
        if (mHelper != null)
            mHelper.close();
        super.onDestroy();

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
