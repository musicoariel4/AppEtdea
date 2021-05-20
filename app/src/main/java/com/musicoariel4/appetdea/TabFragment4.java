package com.musicoariel4.appetdea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.exoplayer2.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

import com.google.android.youtube.player.YouTubePlayerView;


public class TabFragment4 extends Fragment  {


    private static final String TAG ="" ;
    YouTubePlayerView mYouTubePlayerView;
    Button btnPlay;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;


    public TabFragment4() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent messageIntent = new Intent(getActivity(),YouTubeActivity.class);

        startActivity(messageIntent);

    }








}