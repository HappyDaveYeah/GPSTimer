package com.practicar.gpstimer.gpstimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by HappyDave on 24/07/2014.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver{

    private int delayTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Congrats!", Toast.LENGTH_LONG).show();
    }

    public void setTimer(Context context, int delayTime) {
        this.delayTime = delayTime - 1000;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);

    }
}
