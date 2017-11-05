package com.main.myhealthmatters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class HistoryActivity extends Activity {

    TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        history = (TextView)findViewById(R.id.historyText);

        try {
            FileInputStream fin = openFileInput("history.txt");
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            history.setText(temp);

            fin.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException e) {}
        /*
        try {
            FileInputStream fis = getApplicationContext().openFileInput("history.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            //BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            //String line;
            //while ((line = bufferedReader.readLine()) != null) {
                //sb.append(line).append("\n");
            //}
            //history.setText(sb.toString());
        } catch (FileNotFoundException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (IOException e) {

        }*/
    }

    public void backOnClick(View v) {
        finish();
    }
}
