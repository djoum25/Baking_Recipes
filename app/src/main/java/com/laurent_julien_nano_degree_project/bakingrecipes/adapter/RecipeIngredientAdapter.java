package com.laurent_julien_nano_degree_project.bakingrecipes.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.RecipeIngredientsCellBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;

import java.util.List;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.IngredientBindingHolder> {
    private Context mContext;
    private List<Ingredient> mIngredients;

    public RecipeIngredientAdapter (Context context, List<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientBindingHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        RecipeIngredientsCellBinding cellBinding =
            DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.recipe_ingredients_cell, parent, false);
        return new IngredientBindingHolder(cellBinding.getRoot());
    }

    @Override
    public void onBindViewHolder (@NonNull IngredientBindingHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.mBinding.setIngredient(ingredient);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount () {
        return mIngredients.size();
    }

    public class IngredientBindingHolder extends RecyclerView.ViewHolder {
        RecipeIngredientsCellBinding mBinding;

        IngredientBindingHolder (View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
