package com.laurent_julien_nano_degree_project.bakingrecipes.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.IMainActivity;
import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.RecipeDetailsCellBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

import java.util.List;

/**
 * this adapter implement a header for the
 * recyclerview in which implementation is inspired
 * from the following web address:
 * https://stackoverflow.com/questions/26530685/is-there-an-addheaderview-equivalent-for-recyclerview
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsBidingHolder> {
    private static final int HEADER = 0;
    private static final int OTHER = 1;
    private Context mContext;
    private List<Step> mSteps;

    public RecipeStepsAdapter (Context context, List<Step> steps) {
        mContext = context;
        mSteps = steps;
    }

    @NonNull
    @Override
    public RecipeStepsBidingHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {

                RecipeDetailsCellBinding detailsCellBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.recipe_details_cell, parent, false);
                return new RecipeStepsBidingHolder(detailsCellBinding.getRoot());
    }

    @Override
    public void onBindViewHolder (@NonNull RecipeStepsBidingHolder holder, int position) {
        Step step = mSteps.get(position);
            ((RecipeStepsBidingHolder) holder).mBinding.setStep(step);
            ((RecipeStepsBidingHolder) holder).mBinding.setIMainActivity((IMainActivity) mContext);
            ((RecipeStepsBidingHolder) holder).mBinding.executePendingBindings();

    }


    @Override
    public int getItemCount () {
        return mSteps.size();
    }

    private Step getItem (int position) {
        return mSteps.get(position - 1);
    }

    private boolean isHeader (int position) {
        return position == 0;
    }

    public class RecipeStepsBidingHolder extends RecyclerView.ViewHolder {
        RecipeDetailsCellBinding mBinding;

        RecipeStepsBidingHolder (View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
