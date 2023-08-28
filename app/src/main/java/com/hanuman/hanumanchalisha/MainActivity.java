package com.hanuman.hanumanchalisha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    MediaPlayer player;

    private SeekBar seekBar;

    // Enum to map resource IDs
    enum FragmentType {
        HOME(R.id.home),
        SETTINGS(R.id.settings);

        private final int resourceId;

        FragmentType(int resourceId) {
            this.resourceId = resourceId;
        }

        public int getResourceId() {
            return resourceId;
        }
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            for (FragmentType fragmentType : FragmentType.values()) {
                if (fragmentType.getResourceId() == item.getItemId()) {
                    replaceFragment(fragmentType);
                    return true;
                }
            }
            return false;
        });

        // Initialize seekBar with its corresponding view from the layout
        seekBar = findViewById(R.id.seekBar);

        bottomNavigationView.setSelectedItemId(FragmentType.HOME.getResourceId());
    }

    private boolean replaceFragment(FragmentType fragmentType) {
        Fragment fragment;
        switch (fragmentType) {
            case HOME:
                fragment = firstFragment;
                break;
            case SETTINGS:
                startActivity(new Intent(getApplicationContext(), AudioActivity.class));
                overridePendingTransition(0,0);
                return true;
            default:
                throw new IllegalArgumentException("Unknown fragment type");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
        return false;
    }

    // Audio control methods

    public void play(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.shri_hanuman_chalisa); // Replace with your audio resource
            player.setOnCompletionListener(mp -> stopPlayer());
        }
        player.start();
    }

    public void pause(View v) {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
