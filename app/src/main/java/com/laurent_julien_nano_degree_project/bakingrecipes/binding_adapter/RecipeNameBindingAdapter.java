package com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.laurent_julien_nano_degree_project.bakingrecipes.ScreenSizeUtility;
import com.laurent_julien_nano_degree_project.bakingrecipes.adapter.RecipeNameAdapter;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;

import java.util.List;


public class RecipeNameBindingAdapter {
    private static final int NUM_COL = 2;
    private static final String TAG = RecipeNameBindingAdapter.class.getSimpleName();
    private static float size;

    public RecipeNameBindingAdapter (Activity activity) {
        ScreenSizeUtility screenSizeUtility = new ScreenSizeUtility(activity);
        size = screenSizeUtility.getWidth();
        Log.d(TAG, "RecipeNameBindingAdapter: this is size" + size);
    }

    @BindingAdapter("recipelist")
    public static void setRecipeNameList (RecyclerView view, List<Recipe> recipes) {
        Context context = view.getContext();
        if (recipes == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

        if (layoutManager == null) {
            if (size >= 800) {
                view.setLayoutManager(new GridLayoutManager(context, NUM_COL));
            } else {
                view.setLayoutManager(new LinearLayoutManager(context));
            }

        }

        RecipeNameAdapter adapter = (RecipeNameAdapter) view.getAdapter();
        if (adapter == null) {
            adapter = new RecipeNameAdapter(context, recipes);
            view.setAdapter(adapter);
        }
    }
}
