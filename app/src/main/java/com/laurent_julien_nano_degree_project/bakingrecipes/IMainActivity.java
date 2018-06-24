package com.laurent_julien_nano_degree_project.bakingrecipes;

import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

public interface IMainActivity {

    void onRecipeShortDescriptionClick (Step step);

    void onNextButtonClick (int actualStepId);

    void onPreviousButtonClick (int actualStepId);

}
