package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeNameListBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipe;
import com.laurent_julien_nano_degree_project.bakingrecipes.networkUtil.ConnectionStatus;
import com.laurent_julien_nano_degree_project.bakingrecipes.services.BakingRecipeIntentService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RecipeNameList extends Fragment {
    private static final String URL_TO_QUERY = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private List<Recipe> mRecipes = new ArrayList<>();
    private FragmentRecipeNameListBinding mBinding;

    public RecipeNameList () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mBinding = FragmentRecipeNameListBinding.inflate(inflater);
        mBinding.recipeNameListContainer.setBackground(getActivity()
            .getResources().getDrawable(R.drawable.custom_background));
        return mBinding.getRoot();
    }

    @Override
    public void onStart () {
        super.onStart();
        if (ConnectionStatus.isDeviceConnected(getContext())) {
            BakingRecipeIntentService.startActionQueryUrl(getContext(), URL_TO_QUERY);
            EventBus.getDefault().register(this);
        } else {
            showNoConnectionMessage();
        }
    }

    @Override
    public void onStop () {
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.warning)
            .setMessage(R.string.no_connection)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick (DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
    }

}
