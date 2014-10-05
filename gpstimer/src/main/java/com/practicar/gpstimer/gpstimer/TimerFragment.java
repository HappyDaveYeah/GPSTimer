package com.practicar.gpstimer.gpstimer;



import android.content.Context;
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


public class TimerFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Button btnStart;
    private TextView timeTV;
    private SharedPreferences pref;
    private AlarmBroadcastReceiver alarmBroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmBroadcastReceiver = new AlarmBroadcastReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        timeTV = (TextView) view.findViewById(R.id.time_tv);
        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity().getApplicationContext();
                int delayTime = Integer.parseInt(pref.getString(SettingsActivity.KEY_PREF_DELAY, "")) * 1000;
                if(alarmBroadcastReceiver != null){
                    alarmBroadcastReceiver.setTimer(context, delayTime);
                }else{
                    Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        getActivity().registerReceiver(this.alarmBroadcastReceiver, new IntentFilter(AlarmBroadcastReceiver.ACTION));
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        pref.registerOnSharedPreferenceChangeListener(this);
        changeTimeTextView();
        super.onResume();
    }

    @Override
    public void onPause() {
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        pref.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(SettingsActivity.KEY_PREF_DELAY)) {
            changeTimeTextView();
        }
    }

    private void changeTimeTextView() {
        timeTV.setText(pref.getString(SettingsActivity.KEY_PREF_DELAY, ""));
    }
}
