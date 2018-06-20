package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsHelper;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeIngredientListBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.widget.RecipeWidgetProvider;

import java.util.ArrayList;
import java.util.List;

import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_INGREDIENTS;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_MEASURE;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_QUANTITY;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_RECIPE_NAME;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.CONTENT_URI;


public class RecipeIngredientList extends Fragment {
    private static final String TAG = RecipeIngredientList.class.getSimpleName();

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

                // TODO: 6/19/18 may need to be removed 
                RecipeWidgetProvider.refresh(getContext());
                return true;
        }
        return false;
    }

    private void addIngredientsToWidget(List<Ingredient> ingredients){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        getContext().getContentResolver().delete(CONTENT_URI, null, null);
        ContentValues values = new ContentValues();
        long insert = 0;
        if (ingredients != null){
            values.put(COLUMN_RECIPE_NAME, mRecipe.getName());
            for (Ingredient ingredient : ingredients) {
                values.put(COLUMN_INGREDIENTS, ingredient.getIngredient());
                values.put(COLUMN_QUANTITY, ingredient.getQuantity());
                values.put(COLUMN_MEASURE, ingredient.getMeasure());
                final Uri result = getContext().getContentResolver().insert(CONTENT_URI, values);
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

    public static List<Ingredient> getIngredientFromDb (Context context) {
        List<Ingredient> ingredients = new ArrayList<>();
        Log.d(TAG, "getDataForTestingPurpose is called");
        String[] projection = {COLUMN_INGREDIENTS, COLUMN_QUANTITY, COLUMN_MEASURE};
        final Cursor query = context.getContentResolver().query(CONTENT_URI,
            projection, null, null, null, null);
        if (query != null) {
            while (query.moveToNext()) {
                ingredients.add(new Ingredient(
                    query.getString(query.getColumnIndexOrThrow(COLUMN_INGREDIENTS)),
                    query.getString(query.getColumnIndexOrThrow(COLUMN_MEASURE)),
                    query.getFloat(query.getColumnIndexOrThrow(COLUMN_QUANTITY))
                ));
            }
        }
        if (query != null)
            query.close();
        return ingredients;
    }
}
