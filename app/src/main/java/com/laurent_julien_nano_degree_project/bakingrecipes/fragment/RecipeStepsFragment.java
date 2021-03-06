package com.laurent_julien_nano_degree_project.bakingrecipes.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.laurent_julien_nano_degree_project.bakingrecipes.IMainActivity;
import com.laurent_julien_nano_degree_project.bakingrecipes.databinding.FragmentRecipeStepsBinding;
import com.laurent_julien_nano_degree_project.bakingrecipes.model.Step;

public class RecipeStepsFragment extends Fragment {

    private static final String ARG_STEP = "step_param";
    private static final String TAG = "RecipeStepsFragment";
    private static final String SIZE = "size";
    private FragmentRecipeStepsBinding mBinding;
    private DefaultBandwidthMeter mBandwidthMeter = new DefaultBandwidthMeter();
    private long playBackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private Step mStepParam;
    private String mVideoURL;
    private ComponentListener mComponentListener;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private RecipeListener mListener;
    private boolean mMTablet;
    private int mStepSize;
    private boolean mPlayer_state;
    private boolean mPlayWhenReady;
    private long mCurrentPosition;
    private boolean mWhenReady;

    public RecipeStepsFragment () {
        // Required empty public constructor
    }

    public static RecipeStepsFragment newInstance (Step step, int size) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);
        args.putInt(SIZE, size);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        try {
            mListener = (RecipeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("should implement " + context);
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepParam = getArguments().getParcelable(ARG_STEP);
            mStepSize = getArguments().getInt(SIZE);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mBinding = FragmentRecipeStepsBinding.inflate(inflater);
        playerView = mBinding.simpleExoPlayerView;
        mComponentListener = new ComponentListener();
        if (mStepParam != null && mStepSize != 0) {
            mBinding.setStep(mStepParam);
            mBinding.setSize(mStepSize);
            mVideoURL = mStepParam.getVideoURL();
        }
        mBinding.setIMainActivity((IMainActivity) getContext());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            playBackPosition = savedInstanceState.getLong("playBackPosition");
            playWhenReady = savedInstanceState.getBoolean("player_state");
        }
    }

    @Override
    public void onStart () {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initPlayer();
        }
    }

    @Override
    public void onResume () {
        super.onResume();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemui();
            mListener.hideToolBarOnLanscapeMode();
        }
        if (Util.SDK_INT <= 23 || player == null) {
            initPlayer();
        }
    }

    @Override
    public void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("player_state", playWhenReady);
        outState.putLong("playBackPosition", playBackPosition);
    }

    @Override
    public void onPause () {
        super.onPause();
        if (playBackPosition == 0) {
            playBackPosition = player.getCurrentPosition();
        }
        playWhenReady = player.getPlayWhenReady();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop () {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        releasePlayer();
        if (mListener != null) {
            mListener = null;
        }
    }

    private void releasePlayer () {
        if (player != null) {
            playBackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.removeListener(mComponentListener);
            player.setVideoListener(null);
            player.setAudioDebugListener(null);
            player.setVideoDebugListener(null);
            player.release();
            player = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemui () {
        playerView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    //init the player
    private void initPlayer () {
        if (player == null) {
            TrackSelection.Factory adaptive = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(adaptive),
                new DefaultLoadControl());
            player.addListener(mComponentListener);
            player.setVideoDebugListener(mComponentListener);
            player.setAudioDebugListener(mComponentListener);
            playerView.setPlayer(player);
        }

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playBackPosition);
        MediaSource mediaSource = buildMediaSource(Uri.parse(mVideoURL));
        player.prepare(mediaSource, false, false);

        Log.d(TAG, "initPlayer " + playBackPosition + " and " + playWhenReady);
    }

    private MediaSource buildMediaSource (Uri parse) {
        return new ExtractorMediaSource.Factory(new
            DefaultHttpDataSourceFactory("bakingrecipes")).createMediaSource(parse);
    }

    public interface RecipeListener {
        void hideToolBarOnLanscapeMode ();
    }

    /**
     * the component listener of the exoplayer starts here
     */
    private class ComponentListener implements ExoPlayer.EventListener,
        VideoRendererEventListener, AudioRendererEventListener {

        @Override
        public void onTimelineChanged (Timeline timeline, Object manifest, int reason) {
        }

        @Override
        public void onTracksChanged (TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        }

        @Override
        public void onLoadingChanged (boolean isLoading) {
        }

        @Override
        public void onPlayerStateChanged (boolean playWhenReady, int playbackState) {
            String state;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    state = "iddle";
                    break;
                case ExoPlayer.STATE_READY:
                    state = "Ready";
                    mBinding.videoProgress.setVisibility(View.GONE);
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    state = "Buffering";
                    mBinding.videoProgress.setVisibility(View.VISIBLE);
                    break;
                case ExoPlayer.STATE_ENDED:
                    state = "ended";
                    mBinding.videoProgress.setVisibility(View.GONE);
                    break;
                default:
                    state = "Unknown state";
            }
        }

        @Override
        public void onRepeatModeChanged (int repeatMode) {
        }

        @Override
        public void onShuffleModeEnabledChanged (boolean shuffleModeEnabled) {
        }

        @Override
        public void onPlayerError (ExoPlaybackException error) {
        }

        @Override
        public void onPositionDiscontinuity (int reason) {
        }

        @Override
        public void onPlaybackParametersChanged (PlaybackParameters playbackParameters) {
        }

        @Override
        public void onSeekProcessed () {
        }

        @Override
        public void onAudioEnabled (DecoderCounters counters) {
        }

        @Override
        public void onAudioSessionId (int audioSessionId) {
        }

        @Override
        public void onAudioDecoderInitialized (String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        }

        @Override
        public void onAudioInputFormatChanged (Format format) {
        }

        @Override
        public void onAudioSinkUnderrun (int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        }

        @Override
        public void onAudioDisabled (DecoderCounters counters) {
        }

        @Override
        public void onVideoEnabled (DecoderCounters counters) {
        }

        @Override
        public void onVideoDecoderInitialized (String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        }

        @Override
        public void onVideoInputFormatChanged (Format format) {
        }

        @Override
        public void onDroppedFrames (int count, long elapsedMs) {
        }

        @Override
        public void onVideoSizeChanged (int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        }

        @Override
        public void onRenderedFirstFrame (Surface surface) {
        }

        @Override
        public void onVideoDisabled (DecoderCounters counters) {
        }
    }
}
