package com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.laurent_julien_nano_degree_project.bakingrecipes.adapter.RecipeIngredientAdapter;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;

import java.util.List;

public class RecipeIngredientBindingAdapter {

    @BindingAdapter("setIngredients")
    public static void setIngredients (RecyclerView view, List<Ingredient> ingredient) {
        Context context = view.getContext();
        if (ingredient == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null) {
            view.setLayoutManager(new LinearLayoutManager(context));
        }


        RecipeIngredientAdapter adapter = (RecipeIngredientAdapter) view.getAdapter();

        if (adapter == null) {
            adapter = new RecipeIngredientAdapter(context, ingredient);
            view.setAdapter(adapter);
        }
    }
}
