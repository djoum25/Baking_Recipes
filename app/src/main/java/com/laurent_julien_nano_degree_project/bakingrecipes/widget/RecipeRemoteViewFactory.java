package com.laurent_julien_nano_degree_project.bakingrecipes.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract;

import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_INGREDIENTS;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_MEASURE;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.COLUMN_QUANTITY;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.CONTENT_URI;

public class RecipeRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = RecipeRemoteViewFactory.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;


    public RecipeRemoteViewFactory (Context context) {
        mContext = context;
    }

    @Override
    public void onCreate () {
    }

    @Override
    public void onDataSetChanged () {
        if (mCursor != null) mCursor.close();
        final long token = Binder.clearCallingIdentity();

        mCursor = mContext.getContentResolver().query(
            CONTENT_URI,
            null,
            null,
            null,
            null);
        Log.d(TAG, "onDataSetChanged: " + CONTENT_URI);

        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy () {
        if (mCursor != null)
            mCursor.close();
    }

    @Override
    public int getCount () {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt (int position) {

        if (position == -1 || mCursor == null ||
            mCursor.getCount() == 0 || !mCursor.moveToPosition(position)) return null;
        //mCursor.moveToPosition(position);

        long ingredientId = mCursor.getLong(
            mCursor.getColumnIndexOrThrow(RecipeIngredientsContract.IngredientEntry._ID));
        String ingredient = mCursor.getString(mCursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS));
        String measure = mCursor.getString(mCursor.getColumnIndexOrThrow(COLUMN_MEASURE));
        float quantity = mCursor.getFloat(mCursor.getColumnIndexOrThrow(COLUMN_QUANTITY));

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout_cell);
        views.setTextViewText(R.id.tvIngredient, ingredient);
        //views.setTextViewText(R.id.tvMeasure, measure);
        //views.setTextViewText(R.id.quantity, (CharSequence) String.valueOf(quantity));
        return views;
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
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds () {
        return true;
    }

}
