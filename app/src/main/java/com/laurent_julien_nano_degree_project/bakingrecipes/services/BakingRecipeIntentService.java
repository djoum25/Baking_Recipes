package com.laurent_julien_nano_degree_project.bakingrecipes.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;
import com.laurent_julien_nano_degree_project.bakingrecipes.networkUtil.HttpHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BakingRecipeIntentService extends IntentService {

    private static final String QUERY_MAIN_URL = "com.laurent_julien_nano_degree_project.bakingrecipes.services.action.query_main_url";
    private static final String EXTRA_MAIN_URL_PARAM = "com.laurent_julien_nano_degree_project.bakingrecipes.services.extra.main_url";
    private static final String TAG = "TEST";

    public BakingRecipeIntentService () {
        super("BakingRecipeIntentService");
    }


    public static void startActionQueryUrl (Context context, String url) {
        Intent intent = new Intent(context, BakingRecipeIntentService.class);
        intent.setAction(QUERY_MAIN_URL);
        intent.putExtra(EXTRA_MAIN_URL_PARAM, url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent (Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (QUERY_MAIN_URL.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_MAIN_URL_PARAM);
                handleActionQueryUrl(url);
            }
        }
    }

    private void handleActionQueryUrl (String url) {
        List<Recipe> recipesList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Recipe[] recipes = gson.fromJson(HttpHelper.connectToUrl(url), Recipe[].class);
            /**
             * recipe id 1 step 5 has the videoUrl in thumbnail
             * but the code can break at  anytime if data change
             */

            if (recipes == null)
                return;

            for (Recipe recipe : recipes) {
                for (Step step : recipe.getSteps()) {
                    if (recipe.getId() == 1 && step.getId() == 5) {
                        step.setVideoURL(step.getThumbnailURL());
                    }
                }
                recipesList.add(recipe);
            }
            EventBus.getDefault().post(recipesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
