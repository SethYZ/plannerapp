package com.example.plannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class CategoryDetailActivity extends AppCompatActivity {

    private TextView textView_countdowntimer;
    private TextView textView_taskName, textView_duration;
    private Button button_start_pause, button_reset;
    private CountDownTimer mCountDownTimer;
    private CategoryList object;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        initView();
        getBundle();

        button_start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                button_start_pause.setText("start");
                button_start_pause.setVisibility(View.INVISIBLE);
                button_reset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        button_start_pause.setText("pause");
        button_reset.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 60000);
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textView_countdowntimer.setText(timeLeftFormatted);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        button_start_pause.setText("start");
        button_reset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillis = object.getDuration() * 60000L;
        updateCountDownText();
        button_reset.setVisibility(View.INVISIBLE);
        button_start_pause.setVisibility(View.VISIBLE);
    }

    private void getBundle() {
        object = (CategoryList)getIntent().getSerializableExtra("object");

        textView_taskName.setText(object.getTitle());
        textView_duration.setText(String.valueOf(object.getDuration()));
        mTimeLeftInMillis = object.getDuration() * 60000L;
    }

    private void initView() {
        button_start_pause = findViewById(R.id.button_start_pause);
        button_reset = findViewById(R.id.button_reset);
        textView_countdowntimer = findViewById(R.id.textView_countdowntimer);
        textView_duration = findViewById(R.id.textView_duration);
        textView_taskName = findViewById(R.id.textView_taskName);
    }
}