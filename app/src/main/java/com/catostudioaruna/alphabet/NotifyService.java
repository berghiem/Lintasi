package com.catostudioaruna.alphabet;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.catostudioaruna.alphabet.view.MainActivity;

import java.util.Calendar;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotifyService extends IntentService {
    private String[] quotes;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;



    public NotifyService(){
        super("NotifyService");
    };
    public NotifyService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent inten2t) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 22);
        c.set(Calendar.MINUTE, 20);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent receiver = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 11, receiver, 0);

        Log.i("lintasiservice","alarm");
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() , AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
