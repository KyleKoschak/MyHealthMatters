package com.main.myhealthmatters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Pattern;

public class OptionsActivity extends Activity {
    private Spinner intervalSpinner;
    private TextView durationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        intervalSpinner = (Spinner)findViewById(R.id.intervalSpinner);
        durationText = (TextView)findViewById(R.id.durationText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(adapter);
    }

    public void saveBtnOnClick(View v) {
        if ( durationText.getText().toString().matches("[0-9]+") && durationText.getText().toString().length() >= 2) {
            MainActivity.exerciseLength = Integer.parseInt(durationText.getText().toString());

            String i =  intervalSpinner.getSelectedItem().toString().substring(0,1);
            MainActivity.exerciseInterval = Integer.parseInt(i);

            finish();
        }
    }
}
