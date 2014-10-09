package com.practicar.gpstimer.gpstimer;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class TimerFragment extends Fragment {
    private Button btnStart;
    private TextView counterTV;
    private TextView coordTV;
    private TextView timeTV;
    private SharedPreferences pref;
    private int delayTime;
    private static final int PERIOD = 1000;
    private static final int FACTOR_MS = 1000;
    private static final String ACTION = "com.practicar.gpstimer.gpstimer.AlarmBroadcastReceiver";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        counterTV = (TextView) view.findViewById(R.id.counter_tv);
        coordTV = (TextView) view.findViewById(R.id.coord_tv);
        timeTV = (TextView) view.findViewById(R.id.time_tv);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimer();
            }
        });
    }

    private BroadcastReceiver alarmBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            delayTime = delayTime - PERIOD;
            counterTV.setText(String.valueOf(delayTime / FACTOR_MS));
            if (delayTime > 0) sendAlarm();
            else {
                //TODO: getCoord + Hora Actual
                Toast.makeText(context, "Time is finished!", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void setTimer() {
        delayTime = Integer.parseInt(pref.getString(SettingsActivity.KEY_PREF_DELAY, "")) * FACTOR_MS;
        sendAlarm();
    }

    private void sendAlarm() {
        Context context = getActivity().getApplicationContext();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + PERIOD, pendingIntent);
    }

    @Override
    public void onResume() {
        getActivity().registerReceiver(this.alarmBroadcastReceiver, new IntentFilter(ACTION));
        changeTimeToPref();
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(this.alarmBroadcastReceiver);
        super.onPause();
    }

    private void changeTimeToPref() {
        counterTV.setText(pref.getString(SettingsActivity.KEY_PREF_DELAY, ""));
    }
}
