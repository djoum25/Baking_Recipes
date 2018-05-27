package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeNameListBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Recipes;
import com.laurent_julien_nano_degree_project.bakingrecipes.networkUtil.ConnectionStatus;
import com.laurent_julien_nano_degree_project.bakingrecipes.services.BakingRecipeIntentService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class RecipeNameList extends Fragment {
    private static final String URL_TO_QUERY = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private List<Recipes> mRecipes = new ArrayList<>();
    private FragmentRecipeNameListBinding mBinding;
    public RecipeNameList () {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ConnectionStatus.isDeviceConnected(getContext())){
            BakingRecipeIntentService.startActionQueryUrl(getContext(), URL_TO_QUERY);
            EventBus.getDefault().register(this);
        }else {
            Toast.makeText(getContext(), "Device is not conected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mBinding = FragmentRecipeNameListBinding.inflate(inflater);
        return mBinding.getRoot();
    }


    @Override
    public void onDestroy () {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnrecipeRecive(List<Recipes> recipes){
        if (recipes == null){
            return;
        }
        mBinding.setRecipes(recipes);
    }
}
