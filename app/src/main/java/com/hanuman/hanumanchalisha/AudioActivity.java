package com.hanuman.hanumanchalisha;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.hanuman.hanumanchalisha.R;

import java.util.Timer;
import java.util.TimerTask;

public class AudioActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton, pauseButton, stopButton;
    private SeekBar seekBar;
    private TextView textView;
    private Handler handler = new Handler();
    private Timer seekBarTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        stopButton = findViewById(R.id.stop_button);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.tv);

        mediaPlayer = MediaPlayer.create(this, R.raw.shri_hanuman_chalisa); // Replace with your audio resource

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startSeekBarUpdate();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                stopSeekBarUpdate();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(AudioActivity.this, R.raw.shri_hanuman_chalisa); // Recreate MediaPlayer
                startSeekBarUpdate();
            }
        });

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Nothing needed here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Nothing needed here
            }
        });
    }

    private void startSeekBarUpdate() {
        if (seekBarTimer == null) {
            seekBarTimer = new Timer();
            seekBarTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (mediaPlayer.isPlaying()) {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }
            }, 0, 1000); // Update every 1 second
        }
    }

    private void stopSeekBarUpdate() {
        if (seekBarTimer != null) {
            seekBarTimer.cancel();
            seekBarTimer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopSeekBarUpdate();
        handler.removeCallbacksAndMessages(null);
    }
}