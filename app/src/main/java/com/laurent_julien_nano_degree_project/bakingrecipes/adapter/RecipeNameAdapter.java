package com.laurent_julien_nano_degree_project.bakingrecipes.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.IMainActivity2;
import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.RecipeNameCellBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;

import java.util.List;

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.RecipeBindingHolder> {

    private List<Recipe> mRecipes;
    private Context mContext;

    public RecipeNameAdapter (Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    @NonNull
    @Override
    public RecipeBindingHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        RecipeNameCellBinding nameCellBinding =
            DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.recipe_name_cell, parent, false);
        return new RecipeBindingHolder(nameCellBinding.getRoot());
    }

    @Override
    public void onBindViewHolder (@NonNull RecipeBindingHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.mBinding.setRecipe(recipe);
        holder.mBinding.setIMainActivity2((IMainActivity2) mContext);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount () {
        return mRecipes.size();
    }

    public class RecipeBindingHolder extends RecyclerView.ViewHolder {
        RecipeNameCellBinding mBinding;

        public RecipeBindingHolder (View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
