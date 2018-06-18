package com.laurent_julien_nano_degree_project.bakingrecipes.database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class RecipeIngredientsContract {
    private RecipeIngredientsContract () {}

    public static final String AUTHORITY = "com.laurent_julien_nano_degree_project" +
        ".bakingrecipes.database.RecipeIngredientsContract";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_INGREDIENTS = "ingredients";

    public static final String SQL_CREATE_INGREDIENTS_ENTRIES =
        "CREATE TABLE " + IngredientEntry.TABLE_NAME + " (" +
        IngredientEntry._ID + " INTEGER PRIMARY KEY," +
        IngredientEntry.COLUMN_RECIPE_NAME + " TEXT," +
            IngredientEntry.COLUMN_INGREDIENTS + " TEXT," +
            IngredientEntry.COLUMN_QUANTITY + " REAL," +
        IngredientEntry.COLUMN_MEASURE + " TEXT)";

    public static final String SQL_DELETE_INGREDIENTS_ENTRIES =
        "DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME;

    public static class IngredientEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
            .buildUpon().appendPath(PATH_INGREDIENTS).build();

        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_RECIPE_NAME = "recipe";
        public static final String COLUMN_INGREDIENTS = "ingredients";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
    }
}
