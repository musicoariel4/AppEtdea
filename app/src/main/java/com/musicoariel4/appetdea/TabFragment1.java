package com.musicoariel4.appetdea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import static android.content.ContentValues.TAG;

public class TabFragment1 extends Fragment {

    private PlayerView playerView1;
    private SimpleExoPlayer player1;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private  int centinela = 0;
    private ImageView img_search;


    public TabFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        playerView1 = rootView.findViewById(R.id.video_view1);
         img_search= (ImageView) rootView.findViewById(R.id.imageView1);
        img_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(getActivity(),EtdeActivity2.class);
                // A web page URL
                messageIntent.setData(Uri.parse("http://www.etdea.edu.co/"));
                startActivity(messageIntent);
            }
        });
        return rootView;

    }


    private void initializePlayer() {
        RenderersFactory audioOnlyRenderersFactory =
                (handler, videoListener, audioListener, textOutput, metadataOutput)
                        -> new Renderer[] {
                        new MediaCodecAudioRenderer(
                                getContext(), MediaCodecSelector.DEFAULT, handler, audioListener)
                };
         player1 = new SimpleExoPlayer.Builder(getContext(), audioOnlyRenderersFactory).build();
       // player1 = new SimpleExoPlayer.Builder(getContext()).build();
        playerView1.setPlayer(player1);

     //   MediaItem mediaItem = MediaItem.fromUri(getString(R.string.SonoraEstero));
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(getString(R.string.SonoraEstero))
                .setMimeType(MimeTypes.AUDIO_MPEG)
                .setLiveMaxPlaybackSpeed(1.02f)
                .build();

        player1.setMediaItem(mediaItem);
        player1.setPlayWhenReady(playWhenReady);
        player1.seekTo(currentWindow, playbackPosition);
        player1.prepare();
    }
  /*  private void pausePlayer(){
        player1.setPlayWhenReady(false);
        player1.getPlaybackState();
    }
    private void startPlayer(){
        player1.setPlayWhenReady(true);
        player1.getPlaybackState();
    }*/
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player1 == null)) {
            initializePlayer();
        }
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView1.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }
    private void releasePlayer() {
        if (player1 != null) {
            playWhenReady = player1.getPlayWhenReady();
            playbackPosition = player1.getCurrentPosition();
            currentWindow = player1.getCurrentWindowIndex();
            player1.release();
            player1 = null;
        }
    }
    public void onMediaItemTransition(
            @Nullable MediaItem mediaItem, @Player.MediaItemTransitionReason int reason) {
        updateUiForPlayingMediaItem(mediaItem);
    }

    private void updateUiForPlayingMediaItem(MediaItem mediaItem) {
    }


}