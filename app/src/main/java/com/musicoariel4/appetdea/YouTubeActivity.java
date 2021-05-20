package com.musicoariel4.appetdea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {
     YouTubePlayerView youTubePlayerView;
    String API_KEY="AIzaSyCS5oQJ2XuDwbm6Z6AZIicXvLFaRaO01Mk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtube_view);

        youTubePlayerView.initialize(API_KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
         if(!b){
             youTubePlayer.cueVideo("si9yBaR1swI");
         }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
           if(youTubeInitializationResult.isUserRecoverableError()){
               youTubeInitializationResult.getErrorDialog(this,1).show();
           }else
           { String error ="Error al inicializar You tube"+youTubeInitializationResult.toString();
               Toast.makeText(getApplication(),error,Toast.LENGTH_LONG).show();
           }
    }

    protected  void onActivityResult(int requestCod, int resultcode, Intent data){

        if(resultcode==1){
            getYoutubePlayerProvider().initialize(API_KEY,this);

        }
    }
     protected  YouTubePlayer.Provider getYoutubePlayerProvider(){
        return youTubePlayerView;
     }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}