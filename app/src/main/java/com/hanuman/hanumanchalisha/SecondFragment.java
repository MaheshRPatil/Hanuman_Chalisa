package com.hanuman.hanumanchalisha;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

public class SecondFragment extends Fragment {

    private SeekBar seekBar;
    private TextView seekBarValue;
    private MediaPlayer mediaPlayer;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the MediaPlayer with your audio resource
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.shri_hanuman_chalisa);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        // Find views
        seekBar = rootView.findViewById(R.id.seekBar);
        seekBarValue = rootView.findViewById(R.id.seekBarValue);

        // Set a listener for SeekBar changes
        seekBar.setMax(mediaPlayer.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 900);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with the current seek bar value
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do something when user starts interacting with the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do something when user stops interacting with the SeekBar
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Release the MediaPlayer to avoid resource leaks
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}