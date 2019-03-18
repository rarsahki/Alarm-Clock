package com.example.android.perfectclock;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static com.example.android.perfectclock.MainActivity.mediaPlayer;
import static com.example.android.perfectclock.Notification_Initiation.CHANNEL_ID;
import static com.example.android.perfectclock.PatternLock.pattern_status;

//import android.support.v4.app.NotificationCompat;

/**
 * Created by User on 05-12-2018.
 */

public class AlarmAlertService extends Service {
    public static int volume;
    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = getSharedPreferences("RINGTONE",MODE_PRIVATE);
        mediaPlayer = MediaPlayer.create(this,Uri.parse(sharedPreferences.getString("ringtone",Uri.parse("android.resource://com.example.android.perfectclock/"+R.raw.alarm).toString())));
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,PatternLock.class),0);
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Notification notification = new NotificationCompat.Builder(getBaseContext(),CHANNEL_ID).setContentIntent(pendingIntent).
                        setContentTitle("Alarm").setContentText("Its Time").setSmallIcon(R.drawable.addicon).build();
                startForeground(1,notification);
            }
        }).start();
        return START_REDELIVER_INTENT;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(pattern_status == false){
            Intent intent1 = new Intent(getBaseContext(),AlarmAlertService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent1);
            }else{
                startService(intent1);
            }
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
