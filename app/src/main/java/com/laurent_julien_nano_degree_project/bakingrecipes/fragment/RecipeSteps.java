package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laurent_julien_nano_degree_project.bakingrecipes.R;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeStepsBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

public class RecipeSteps extends Fragment implements View.OnClickListener {

    private static final String ARG_STEP = "step_param";
    private static final String TAG = "RecipeSteps";
    FragmentRecipeStepsBinding mBinding;
    RecipeStepsListener mListener;
    private Step mStepParam;

    public RecipeSteps () {
        // Required empty public constructor
    }


    public static RecipeSteps newInstance (Step step) {
        RecipeSteps fragment = new RecipeSteps();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        try {
            mListener = (RecipeStepsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                + "Must implement RecipeStepsListener");
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepParam = getArguments().getParcelable(ARG_STEP);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mBinding = FragmentRecipeStepsBinding.inflate(inflater);
        if (mStepParam != null) {
            mBinding.setStep(mStepParam);
            Log.d(TAG, "onCreate " + mStepParam.getVideoURL());
        }
        mBinding.btnPrevious.setOnClickListener(this);
        mBinding.btnNext.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onResume () {
        super.onResume();
        if (mListener != null) {
            mListener.actualStepId(mStepParam.getId());
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        if (mListener != null) {
            mListener = null;
        }
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                mListener.onNextStepClick();
                break;
            case R.id.btn_previous:
                mListener.onPreviousStepClick();
                break;
        }
    }

    public interface RecipeStepsListener {
        void onPreviousStepClick ();

        void onNextStepClick ();

        void actualStepId (int actualStepId);
    }
}
