package com.example.plannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryDetailActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    ArrayList<CategoryList> categoryList = new ArrayList<>();
    private TextView textView_countdowntimer;
    private TextView textView_taskName, textView_duration;
    private Button button_start_pause, button_reset, editDurationBtn;
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
        adapter = new CategoryAdapter(categoryList);

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

        editDurationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mTextView_duration = Integer.parseInt(textView_duration.getText().toString());
                if (mTextView_duration > 0) {
                    object.setDuration(mTextView_duration);
                    adapter.notifyDataSetChanged();
                    if (mTimerRunning) {
                        mTimeLeftInMillis = object.getDuration() * 60000L;
                        mCountDownTimer.cancel();
                        updateCountDownText();
                        startTimer();
                    }else{
                        mTimeLeftInMillis = object.getDuration() * 60000L;
                        updateCountDownText();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Number should be bigger than 0", Toast.LENGTH_SHORT).show();
                }
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
        editDurationBtn = findViewById(R.id.editDurationBtn);
    }
}