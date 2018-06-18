package com.laurent_julien_nano_degree_project.bakingrecipes.widget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsHelper;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Ingredient> mIngredients;

    public WidgetRemoteViewsFactory (Context context) {
        mContext = context;
    }

    @Override
    public void onCreate () {

    }

    @Override
    public void onDataSetChanged () {

    }

    @Override
    public void onDestroy () {

    }

    @Override
    public int getCount () {
        return 0;
    }

    @Override
    public RemoteViews getViewAt (int position) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView () {
        return null;
    }

    @Override
    public int getViewTypeCount () {
        return 0;
    }

    @Override
    public long getItemId (int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds () {
        return false;
    }

    private List<Ingredient> loadIngredients(){
        RecipeIngredientsHelper mHelper = new RecipeIngredientsHelper(mContext);
        SQLiteDatabase db = mHelper.getReadableDatabase();

return null;
    }
}
