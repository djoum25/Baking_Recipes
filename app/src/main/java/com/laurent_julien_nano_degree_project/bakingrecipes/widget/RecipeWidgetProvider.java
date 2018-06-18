package com.laurent_julien_nano_degree_project.bakingrecipes.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String UPDATE_WIDGET_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String EXTRA_ITEM = "com.laurent_julien_nano_degree_project.bakingrecipes.EXTRA_ITEM";

    static void updateAppWidget (Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(UPDATE_WIDGET_ACTION)){
            int appWidgetsId[] = manager.getAppWidgetIds(new ComponentName(context,
                RecipeWidgetProvider.class));
            manager.notifyAppWidgetViewDataChanged(appWidgetsId, R.id.ingredient_list);
        }
        super.onReceive(context, intent);
    }
}

