package com.practicar.gpstimer.gpstimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by HappyDave on 24/07/2014.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver{
    private static int PERIOD = 1000;
    public static final String ACTION = "com.practicar.gpstimer.gpstimer.AlarmBroadcastReceiver";
    private int delayTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("delayTime [onReceive]: ", String.valueOf(this.delayTime));
        if (this.delayTime > 0) {
            Toast.makeText(context, "Congrats! " + this.delayTime, Toast.LENGTH_SHORT).show();
            this.delayTime = delayTime - 1000;
            sendAlarm(context);
        }
        else {
            Toast.makeText(context, "Time is finished!", Toast.LENGTH_LONG).show();
        }
    }

    public void setTimer(Context context, int time) {
        this.delayTime = time - 1000;
        sendAlarm(context);
    }

    private void sendAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + PERIOD, pendingIntent);
    }
}
