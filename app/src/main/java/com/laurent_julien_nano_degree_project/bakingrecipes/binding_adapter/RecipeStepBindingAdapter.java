package com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.laurent_julien_nano_degree_project.bakingrecipes.adapter.RecipeStepsAdapter;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

import java.util.List;

public class RecipeStepBindingAdapter {
    @BindingAdapter("setRecipeStep")
    public static void setRecipeStep (RecyclerView view, List<Step> steps) {

        if (steps == null) return;

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

        if (layoutManager == null) {
            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }

        RecipeStepsAdapter adapter = (RecipeStepsAdapter) view.getAdapter();
        if (adapter == null) {
            adapter = new RecipeStepsAdapter(view.getContext(), steps);
            view.setAdapter(adapter);
        }
    }
}
