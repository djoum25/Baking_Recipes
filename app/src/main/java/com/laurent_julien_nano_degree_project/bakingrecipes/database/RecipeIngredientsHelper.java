package com.laurent_julien_nano_degree_project.bakingrecipes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeIngredientsHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ingredients";

    public RecipeIngredientsHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(RecipeIngredientsContract.SQL_CREATE_INGREDIENTS_ENTRIES);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RecipeIngredientsContract.SQL_DELETE_INGREDIENTS_ENTRIES);
        onCreate(db);
    }
}
