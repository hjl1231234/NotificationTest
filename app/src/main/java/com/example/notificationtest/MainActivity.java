package com.example.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Random;

import static android.app.Notification.VISIBILITY_SECRET;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:
                //懒跳转逻辑
                Intent intent = new Intent(this, NotificationActivity.class);
//                PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{inte?nt}, 0);
                PendingIntent pendingIntent = PendingIntent.getActivities(this, 1, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);

//创建manager
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //创建build
                Notification.Builder builder = new Notification.Builder(this);
                builder.setAutoCancel(true)
                        .setContentInfo("info")
                        .setContentTitle("title")
                        .setContentText("text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        //懒跳转关键写法
                        .setContentIntent(pendingIntent);
//8.0以上版本适配
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //创建channel
                    NotificationChannel channel = new NotificationChannel("1", "my_channel", NotificationManager.IMPORTANCE_DEFAULT);
                    //设置提示灯
                    channel.enableLights(true);
                    channel.setLightColor(Color.green(1));
                    //显示图标
                    channel.canShowBadge();
                    channel.setShowBadge(true);
                    //锁屏是否显示通知（系统应用可用，基本用不到）
                    channel.setLockscreenVisibility(VISIBILITY_SECRET);
                    //通知时是否有闪光灯（需要手机硬件支持）闪光时的颜色
                    channel.enableLights(true);
                    channel.shouldShowLights();
                    channel.setLightColor(Color.BLUE);
                    //是否允许震动
                    channel.enableVibration(true);
                    //获取系统通知响铃的配置参数（一般用系统默认的即可）
                    AudioAttributes audioAttributes = channel.getAudioAttributes();
//                    channel.setSound(Uri.fromFile(new File("src/main/res/music/Ready For War.mp3")), audioAttributes); //设置通知铃声
                    //设置震动频率（100ms，100ms，100ms 三次震动）
                    channel.setVibrationPattern(new long[]{500, 500, 500});

//                    InputStream ins = getResources().openRawResource();


//manager channel builder三者的作用
                    manager.createNotificationChannel(channel);
                    builder.setChannelId("1");
                }
                //创建notification
                Notification notification = builder.build();
                manager.notify(1, notification);

                break;
            default:
                break;
        }
    }
}
