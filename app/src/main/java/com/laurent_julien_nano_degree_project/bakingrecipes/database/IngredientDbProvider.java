package com.laurent_julien_nano_degree_project.bakingrecipes.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.AUTHORITY;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.IngredientEntry.TABLE_NAME;
import static com.laurent_julien_nano_degree_project.bakingrecipes.database.RecipeIngredientsContract.PATH_INGREDIENTS;

public class IngredientDbProvider extends ContentProvider {
    public static final int TASK = 100;
    private static final UriMatcher mMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mMatcher.addURI(AUTHORITY, PATH_INGREDIENTS, TASK);
    }

    private RecipeIngredientsHelper dbHelper;

    @Override
    public boolean onCreate () {
        dbHelper = new RecipeIngredientsHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query (@NonNull Uri uri, @Nullable String[] projection,
                         @Nullable String selection, @Nullable String[]
                             selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase cursor = dbHelper.getReadableDatabase();
        switch (mMatcher.match(uri)) {
            case TASK:
                final Cursor query = cursor.query(TABLE_NAME, projection, selection, selectionArgs,
                    null, null, sortOrder);
                query.setNotificationUri(getContext().getContentResolver(), uri);
                return query;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType (@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert (@NonNull Uri uri, @Nullable ContentValues values) {
        switch (mMatcher.match(uri)) {
            case TASK:
                return insertIngredientsItems(uri, values);
            default:
                throw new IllegalArgumentException("Unknown Uri in the insert");
        }
    }

    @Override
    public int delete (@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (mMatcher.match(uri)) {
            case TASK:
                db.execSQL("delete from " + TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknow Uri int the insert");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update (@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private Uri insertIngredientsItems (Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        final long rowId = db.insert(TABLE_NAME, null, values);
        if (rowId == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, rowId);
    }
}
