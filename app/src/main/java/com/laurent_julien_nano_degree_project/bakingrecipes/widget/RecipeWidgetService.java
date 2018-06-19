package com.laurent_julien_nano_degree_project.bakingrecipes.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory (Intent intent) {
        return new RecipeRemoteViewFactory(this.getApplicationContext());
    }


}
