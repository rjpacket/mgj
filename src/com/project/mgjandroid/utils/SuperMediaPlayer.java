package com.project.mgjandroid.utils;

import android.media.MediaPlayer;

public class SuperMediaPlayer extends MediaPlayer {
  
    public static SuperMediaPlayer Instance = new SuperMediaPlayer();  
      
    private SuperMediaPlayer() {  
        super();  
        this.setOnCompletionListener(new OnCompletionListener() {  
  
            @Override  
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
    }

    public void next(){  

    }  
}  