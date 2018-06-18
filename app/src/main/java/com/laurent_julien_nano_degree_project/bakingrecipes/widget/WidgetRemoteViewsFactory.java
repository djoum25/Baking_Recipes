package com.laurent_julien_nano_degree_project.bakingrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsHelper;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_INGREDIENTS;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_MEASURE;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_QUANTITY;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.TABLE_NAME;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry._ID;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Ingredient> mIngredients;

    public WidgetRemoteViewsFactory (Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate () {
        mIngredients = loadIngredients();
    }

    @Override
    public void onDataSetChanged () {

    }

    @Override
    public void onDestroy () {
        mIngredients.clear();
    }

    @Override
    public int getCount () {
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt (int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout_cell);
        Ingredient ingredient = mIngredients.get(position);
        rv.setTextViewText(R.id.tvIngredient, ingredient.getIngredient());
        rv.setTextViewText(R.id.tvQuantity, String.valueOf(ingredient.getQuantity()));
        rv.setTextViewText(R.id.tvMeasure, ingredient.getMeasure());

        Bundle extras = new Bundle();
        extras.putInt(RecipeWidgetProvider.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtra("ingredient", ingredient);
        intent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_layout, intent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView () {
        return null;
    }

    @Override
    public int getViewTypeCount () {
        return 1;
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public boolean hasStableIds () {
        return true;
    }

    private List<Ingredient> loadIngredients(){
        RecipeIngredientsHelper mHelper = new RecipeIngredientsHelper(mContext);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<Ingredient> ingredients = new ArrayList<>();
        String[] projection = {_ID, COLUMN_INGREDIENTS, COLUMN_QUANTITY, COLUMN_MEASURE};
        final Cursor query = db.query(TABLE_NAME, projection, null, null,
            null, null, null);

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
