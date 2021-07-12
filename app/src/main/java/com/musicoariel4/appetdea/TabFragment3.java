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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;


public class TabFragment3 extends Fragment {

    private PlaybackStateListener playbackStateListener;
    private static final String TAG = TabFragment3.class.getName();
    private PlayerView playerView3;
    private SimpleExoPlayer player3;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private  int centinela = 0;
    private ImageView img_search;

    private GridView lv1;
    private String[] paises = {"Sonora Mantacera", "Rafael", "Sandro", "Guillermo Buitrago",
            "Los angeles Negros", "BobyCruz", "Leonardo Fabio"};
    private String[] habitantes = {"https://eduvision.com.co/wp-content/uploads/2020/07/MOMENTOS-DE-BOHEMIA-SONORA-MATANCERA.mp4",
            "https://eduvision.com.co/wp-content/uploads/2020/07/momentosdebohemia_3.mp4",
            "https://eduvision.com.co/wp-content/uploads/2020/12/MOMENTOS-DE-BOHEMIA-SANDRO-DE-AMERICA.mp4",
            "https://eduvision.com.co/wp-content/uploads/2020/12/MOMENTOSDEBOHEMIAGUILLERMOBUITRAGO.mp4",
            "https://eduvision.com.co/wp-content/uploads/2020/09/vMOMENTOS-DE-BOHEMIA-LOS-ÀNGELES-NEGROS.mp4",
            "https://eduvision.com.co/wp-content/uploads/2020/09/MOMENTOS-DE-BOHEMIA-RICHIE-RAY-&-BOBBY-CRUZ.mp4",
            "https://eduvision.com.co/wp-content/uploads/2020/06/MOMENTOS-DE-BOHEMIA_leonardo-1.mp4"};
   private String urlBohemia ="http://eduvision.com.co/wp-content/uploads/2020/07/momentosdebohemia_3.mp4";
   //public String urlBohemia= "";
   public String urlBohemia1="";

    public TabFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        playbackStateListener = new PlaybackStateListener();
        final View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
        lv1 = (GridView) rootView.findViewById(R.id.list1);

        playerView3 = rootView.findViewById(R.id.video_view3);
        img_search= (ImageView) rootView.findViewById(R.id.imageView3);



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



   public void initializePlayer() {
       ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,paises);
       lv1.setAdapter(adapter1);

       ExtractorsFactory mp4ExtractorFactory =
               () -> new Extractor[] {new Mp4Extractor()};

       player3 = new SimpleExoPlayer.Builder(getContext(), mp4ExtractorFactory).build();
       playerView3.setPlayer(player3);

       lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


               MediaItem mediaItem =
                       new MediaItem.Builder()
                               .setUri(habitantes[i])
                               .setMimeType(MimeTypes.APPLICATION_MP4)
                            //  .setLiveMinPlaybackSpeed(1.0F)
                            //  .setLiveMaxPlaybackSpeed(1.0f)
                               .build();
        player3.setMediaItem(mediaItem);


         player3.setPlayWhenReady(playWhenReady);
        player3.seekTo(currentWindow, playbackPosition);
               player3.addListener(playbackStateListener);
               player3.prepare();

   }


});
    }


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
        if ((Util.SDK_INT < 24 || player3 == null)) {
            initializePlayer();
        }
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView3.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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
        if (player3 != null) {
            playWhenReady = player3.getPlayWhenReady();
            playbackPosition = player3.getCurrentPosition();
            currentWindow = player3.getCurrentWindowIndex();
            player3.removeListener(playbackStateListener);
            player3.release();
            player3 = null;
          //  urlBohemia=urlBohemia1;
        }

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