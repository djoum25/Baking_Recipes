package com.laurent_julien_nano_degree_project.bakingrecipes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.laurent_julien_nano_degree_project.bakingrecipes.MainActivity;
import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.fragment.RecipeIngredientList;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Ingredient;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeWidgetProvider.class.getSimpleName();

    static void updateAppWidget (Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        RemoteViews views = getRecipeRemoteViews(context);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getRecipeRemoteViews (Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        Intent intent = new Intent(context, RecipeWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list, intent);


        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_empty_view, appPendingIntent);

        views.setEmptyView(R.id.widget_list, R.id.widget_empty_view);

        List<Ingredient> ingredientFromDb = RecipeIngredientList.getIngredientFromDb(context);

        for (Ingredient ingredient : ingredientFromDb) {
            Log.d(TAG, "getRecipeRemoteViews: " + ingredient.getIngredient());
        }
        return views;
    }

    @Override
    public void onEnabled (Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled (Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive (Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

}

