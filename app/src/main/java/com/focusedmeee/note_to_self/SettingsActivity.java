package com.focusedmeee.note_to_self;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.zip.Inflater;
import java.util.List;

/**
 * Created by user pc on 29-Dec-16.
 */

public class SettingsActivity extends AppCompatActivity {
    public static final String SOUND_KEY = "sound";
    public static final String ANIM_OPTION_KEY = "anim option";
    private static final String TAGz = "Focusedmeee";

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private boolean mSound;
    private int mAnimOption;

    public static final int FAST = 0;
    public static final int SLOW = 1;
    public static final int NONE = 2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.settings_activity_layout);

        mPrefs = this.getSharedPreferences(MainActivity.NOTE_TO_SELF_PREFS_ID, MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mSound = mPrefs.getBoolean(SOUND_KEY, true);

        CheckBox checkBoxSound = (CheckBox)findViewById(R.id.checkBoxSound);

        checkBoxSound.setChecked(mSound);

        checkBoxSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAGz, "mSound == " + mSound);
                Log.i(TAGz, "isChecked == " + isChecked);

                mSound = !mSound;
                mEditor.putBoolean(SOUND_KEY, mSound);
            }
        });

        mAnimOption = mPrefs.getInt(ANIM_OPTION_KEY, FAST);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        switch (mAnimOption){
            case FAST:
                radioGroup.check(R.id.rbtFast);
                break;
            case SLOW:
                radioGroup.check(R.id.rbtSlow);
                break;
            case NONE:
                radioGroup.check(R.id.rbtNone);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.findViewById(checkedId).getId()){
                    case R.id.rbtFast:
                        mAnimOption = FAST;
                        break;
                    case R.id.rbtSlow:
                        mAnimOption = SLOW;
                        break;
                    case R.id.rbtNone:
                        mAnimOption = NONE;
                        break;
                }

                mEditor.putInt(ANIM_OPTION_KEY, mAnimOption);
            }
        });

    //    checkSensors();
    }

    @Override
    public void onPause(){
        super.onPause();
        mEditor.commit();
    }

    private void checkSensors() {
        SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        LinearLayout layout = new LinearLayout(this);
        ListView listView = new ListView(this);
        layout.addView(listView);

        SensorAdapter adapter = new SensorAdapter(this);
        listView.setAdapter(adapter);

        for (Sensor sensor : sensors) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Sensor Name: %s\n", sensor.getName()));
            stringBuilder.append(String.format("Sensor Vendor: %s\n", sensor.getVendor()));
            stringBuilder.append(String.format("Sensor Type: %s\n", sensor.getStringType()));
            stringBuilder.append(String.format("Sensor Version: %s\n", sensor.getVersion()));
            adapter.AddSensor(stringBuilder.toString());
        }
        this.setContentView(layout);


    }

    class SensorAdapter extends BaseAdapter {

        private List<String> sensorList = new ArrayList<String>();
        private Context mContext;

        public SensorAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return sensorList.size();
        }

        @Override
        public String getItem(int position) {
            return sensorList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView txtSensor = new TextView(mContext);
            txtSensor.setText(sensorList.get(position));

            return txtSensor;
        }

        public void AddSensor(String sensor) {
            sensorList.add(sensor);
            notifyDataSetChanged();
        }
    }


}
