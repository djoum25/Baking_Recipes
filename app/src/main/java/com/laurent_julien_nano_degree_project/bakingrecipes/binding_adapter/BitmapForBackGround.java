package com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;
import com.squareup.picasso.Picasso;

public class BitmapForBackGround {
    private static final String TAG = BitmapForBackGround.class.getSimpleName();

    @BindingAdapter("setBackground2")
    public static void setBackground2 (final View view, Recipe recipe) {
        final Context context = view.getContext();
        String myRecipeImage = null;
        switch (recipe.getName()) {
            case "Nutella Pie":
                myRecipeImage = context.getString(R.string.image_nutella_pie);
                break;
            case "Brownies":
                myRecipeImage = context.getString(R.string.image_brownies);
                break;
            case "Yellow Cake":
                myRecipeImage = context.getString(R.string.image_yellow_cake);
                break;
            case "Cheesecake":
                myRecipeImage = context.getString(R.string.image_cheecake);
                break;
        }

        Glide.with(context).load(myRecipeImage).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady (@NonNull Drawable resource,
                                         @Nullable Transition<? super Drawable> transition) {
                view.setBackground(resource);
            }
        });
    }

    @BindingAdapter("setRecipeStepImage")
    public static void setRecipeStepImage (ImageView view, Step step) {
        loadImage(view, step);
    }

    private static void loadImage (ImageView view, Step step) {
        if (step.getVideoURL().isEmpty()) {
            String thumbnailUrl = step.getThumbnailURL();
            if (thumbnailUrl.isEmpty()) {
                thumbnailUrl = view.getContext().getString(R.string.thumbnail);
            }
            Picasso.get().load(thumbnailUrl)
                .placeholder(R.drawable.custom_background)
                .error(R.drawable.custom_background)
                .resize(500, 500)
                .centerCrop()
                .into(view);
        }
    }
}
