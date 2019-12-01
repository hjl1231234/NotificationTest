package com.example.notificationtest;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class PlayMediaPlayer extends AppCompatActivity {

    //实例化播放内核
    android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
    //获得播放源访问入口
    AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.Ready_For_War); // 注意这里的区别

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//给MediaPlayer设置播放源
        try {
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
//设置准备就绪状态监听
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 开始播放
                mediaPlayer.start();
            }
        });
//准备播放
        mediaPlayer.prepareAsync();
    }
}
