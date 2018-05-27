package com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.laurent_julien_nano_degree_project.bakingrecipes.adapter.RecipeNameAdapter;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipes;

import java.util.List;


public class RecipeNameBindingAdapter {

   @BindingAdapter("recipelist")
    public static void setRecipeNameList(RecyclerView view, List<Recipes> recipes){
        Context context = view.getContext();

        if (recipes == null){
            return;
        }

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null){
            view.setLayoutManager(new LinearLayoutManager(context));
        }

        RecipeNameAdapter adapter = (RecipeNameAdapter) view.getAdapter();
        if (adapter == null){
            adapter = new RecipeNameAdapter(context, recipes);
            view.setAdapter(adapter);
        }
    }
}
