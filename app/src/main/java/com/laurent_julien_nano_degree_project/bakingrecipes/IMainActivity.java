package com.laurent_julien_nano_degree_project.bakingrecipes;

import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

public interface IMainActivity {
    public void onRecipeNameClick (Recipe recipes);

    public void onRecipeShortDescriptionClick (Step step);

    public void OnRecipeIngredientClick ();
}
