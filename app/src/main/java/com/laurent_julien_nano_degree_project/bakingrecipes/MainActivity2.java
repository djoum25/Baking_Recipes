package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.ActivityMain2Binding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.networkUtil.ConnectionStatus;
import com.laurent_julien_nano_degree_project.bakingrecipes.services.BakingRecipeIntentService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity2 extends AppCompatActivity implements IMainActivity2 {

    ActivityMain2Binding mBinding;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
    }

    @Override
    protected void onStart () {
        super.onStart();
        if (ConnectionStatus.isDeviceConnected(this)) {
            BakingRecipeIntentService.startActionQueryUrl(this,
                getString(R.string.url_to_query));
            EventBus.getDefault().register(this);
        } else {
            showNoConnectionMessage();
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnrecipeRecive (List<Recipe> recipes) {
        mBinding.nameListProgressBar.setVisibility(View.GONE);
        if (recipes == null) {
            return;
        }
        mBinding.setRecipes(recipes);
    }

    public void showNoConnectionMessage () {
        mBinding.nameListProgressBar.setVisibility(View.GONE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity2.this);
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.warning)
            .setMessage(R.string.no_connection)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick (DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
    }

    @Override
    public void onRecipeNameClick (Recipe recipes) {
        Intent intent = new Intent(this, ActivityDetails.class);
        intent.putExtra(getString(R.string.recipe_extra_key), recipes);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
