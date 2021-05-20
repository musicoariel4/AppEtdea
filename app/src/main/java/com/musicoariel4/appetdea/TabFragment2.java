package com.musicoariel4.appetdea;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;



public class TabFragment2 extends Fragment {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private int centinela = 0;
    private ImageView img_search2;
    private PlaybackStateListener playbackStateListener;
    private static final String TAG = TabFragment2.class.getName();

    public TabFragment2() {


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        playbackStateListener = new TabFragment2.PlaybackStateListener();
        // Inflate the layout for this fragment.
        final View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        playerView = rootView.findViewById(R.id.video_view2);
        img_search2 = (ImageView) rootView.findViewById(R.id.imageView2);
        img_search2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(getActivity(), EduvisionActivity2.class);
                // A web page URL
                messageIntent.setData(Uri.parse("http://eduvision.com.co/tv-online/"));
                startActivity(messageIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
     //   player.setPlayWhenReady(true);
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // player.setPlayWhenReady(false);
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        player.release();
        //releasePlayer();

    }

    private void initializePlayer() {

        if (player == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(getContext());

            trackSelector.setParameters(
                    trackSelector
                            .buildUponParameters()
                            .setMaxVideoSizeSd()
                           );
            player = new SimpleExoPlayer.Builder(getContext())
                    /*.setLivePlaybackSpeedControl(
                            new DefaultLivePlaybackSpeedControl.Builder()
                                    .setFallbackMaxPlaybackSpeed(1.04f)
                                    .build())*/
                    .setTrackSelector(trackSelector)
                    .build();
        }
        //player = new SimpleExoPlayer.Builder(getContext()).build();


        playerView.setPlayer(player);


        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(getString(R.string.media_url_dash))
                //.setMimeType(MimeTypes.APPLICATION_MPD)
                .setMimeType(MimeTypes.APPLICATION_M3U8)
                //   .setLiveMinPlaybackSpeed(1.0F)
                // .setLiveMaxPlaybackSpeed(1.0f)
                .setLiveMaxPlaybackSpeed(1.02f)
                .build();
        player.setMediaItem(mediaItem);


         player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();


    }


    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void onMediaItemTransition(
            @Nullable MediaItem mediaItem, @Player.MediaItemTransitionReason int reason) {
        updateUiForPlayingMediaItem(mediaItem);
    }

    private void updateUiForPlayingMediaItem(MediaItem mediaItem) {
    }


    private class PlaybackStateListener implements Player.EventListener {

        @Override
        public void onPlaybackStateChanged(int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d(TAG, "changed state to " + stateString);
        }
    }
}