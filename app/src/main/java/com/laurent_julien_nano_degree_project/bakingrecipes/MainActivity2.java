package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.laurent_julien_nano_degree_project.bakingrecipes.binding_adapter.RecipeNameBindingAdapter;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.ActivityMain2Binding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.networkUtil.ConnectionStatus;
import com.laurent_julien_nano_degree_project.bakingrecipes.services.BakingRecipeIntentService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements IMainActivity2, View.OnClickListener {

    private static final String TAG = MainActivity2.class.getSimpleName();
    ActivityMain2Binding mBinding;
    private Button btnNoData;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        RecipeNameBindingAdapter nameBindingAdapter =
            new RecipeNameBindingAdapter(this);
        btnNoData = findViewById(R.id.no_data);
        btnNoData.setOnClickListener(this);
    }

    @Override
    protected void onStart () {
        super.onStart();
        checkConnectionStatusAndStartActivity();
    }

    private void checkConnectionStatusAndStartActivity () {
        if (ConnectionStatus.isDeviceConnected(this)) {
            btnNoData.setVisibility(View.GONE);
            BakingRecipeIntentService.startActionQueryUrl(this,
                getString(R.string.url_to_query));
            EventBus.getDefault().register(this);
            mBinding.nameListProgressBar.setVisibility(View.GONE);
        } else {
            btnNoData.setVisibility(View.VISIBLE);
            mBinding.nameListProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onStop () {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnrecipeRecive (ArrayList<Recipe> recipes) {
        mBinding.nameListProgressBar.setVisibility(View.GONE);
        if (recipes == null) {
            return;
        }
        mBinding.setRecipes(recipes);
    }

    @Override
    public void onRecipeNameClick (Recipe recipes) {
        Intent intent = new Intent(this, ActivityDetails.class);
        intent.putExtra(getString(R.string.recipe_extra_key), recipes);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.no_data:
                checkConnectionStatusAndStartActivity();
                break;
        }
    }
}
