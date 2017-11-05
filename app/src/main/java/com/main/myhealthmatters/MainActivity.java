package com.main.myhealthmatters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static int exerciseLength;
    public static int exerciseInterval;

    // Views
    TextView timer;
    TextView exerciseName;
    ImageView exerciseImage;
    Button startButton;

    private boolean started = false;    // If start was pressed
    private String currentExercise;     // Randomly selected exercise
    private int random;                 // Random int
    private int id;
    private CountDownTimer cdt;
    Date completedTime;
    File file;
    String filename = "history.txt";
    String output;
    FileOutputStream outputStream;
    String history;

    private String[] exercises = {"Forward Lunge", "Backward Lunge", "Crunch", "Push Up", "Squat",
                                    "Calf raise", "Burpee", "Wall sit", "Arm circles", "Flutter Kick",
                                    "Side Plank", "Bicycle", "High Knees", "Punch", "Plank Jacks",
                                    "Butt Kicks", "Jumping Jacks", "Fast Feet Shuffle", "Vertical Jump",
                                    "Skaters", "Mountain Climbers", "Invisible jump rope",
                                    "Side Kick", "Kick Full Back", "Roll up", "Donkey Kicks",
                                    "Hip bridge", "Bridge Leg Lifts", "Elbow Plank",
                                    "Reverse Crunches", "Windshield Wipers", "Single Leg Stretch",
                                    "Double Leg Stretch", "Spine Stretch", "Double Leg Kick",
                                    "Side Kick Kneeling", "Pilates Swimming", "Crisscross", "Spinal Balance",
                                    "Catcow", "Heel Drops", "Heel Slides", "Superman Plank",
                                    "Triangle Pose", "Downward Dog Pose", "Standing forward bend pose"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));

        MainActivity.exerciseLength = 30;
        MainActivity.exerciseInterval = 60;

        timer = (TextView)findViewById(R.id.timer);
        exerciseName = (TextView)findViewById(R.id.exerciseName);
        exerciseImage = (ImageView)findViewById(R.id.exerciseImage);
        startButton = (Button)findViewById(R.id.startBtn);
        file = new File(getApplicationContext().getFilesDir(), filename);

        random = (int)(Math.random() * exercises.length);
        currentExercise = exercises[random];
        Context context = exerciseImage.getContext();
        id = context.getResources().getIdentifier(fileName(currentExercise), "drawable", getApplicationContext().getPackageName());

        exerciseName.setText(currentExercise);
        exerciseImage.setImageResource(id);

        try {
            FileInputStream fin = openFileInput("history.txt");
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            history = temp;

            fin.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException e) {}
    }


    /**
     * Start button is pressed
     * @param v
     */
    public void startBtnOnClick(View v) {
        if (!started) {
            started = true;
            cdt = new CountDownTimer(MainActivity.exerciseLength * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer.setText("Time: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    timer.setText("done!");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    completedTime = Calendar.getInstance().getTime();
                    v.vibrate(1000);

                    output = history + "\n" + currentExercise + " " + completedTime;

                    try {
                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(output.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            startButton.setText("Stop");
        } else {
            cdt.cancel();
            started = false;
            startButton.setText("Start");
        }

    }

    /**
     * Skip button is pressed
     * @param v
     */
    public void skipBtnOnClick(View v) {
        Context context = exerciseImage.getContext();
        random = (int)(Math.random() * exercises.length);
        currentExercise = exercises[random];


        // This stops the timer
        if (cdt != null)
            cdt.cancel();
        cdt = new CountDownTimer(MainActivity.exerciseLength * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("Time: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("done!");
            }
        }.start();
        cdt.cancel();
        started = false;
        startButton.setText("Start");
        // End of timer


        // get ID
        id = context.getResources().getIdentifier(fileName(currentExercise), "drawable", getApplicationContext().getPackageName());

        // Set views
        exerciseName.setText(currentExercise);
        exerciseImage.setImageResource(id);
    }

    private String fileName(String exerciseName) {
        String result = exerciseName.toLowerCase();
        result = result.replaceAll("\\s+","");
        return result;
    }

    public void optionsBtnOnClick(View v) {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void historyOnClick(View v) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
