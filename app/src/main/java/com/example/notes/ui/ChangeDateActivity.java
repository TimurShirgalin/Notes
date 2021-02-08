package com.example.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;

public class ChangeDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_date);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            DateChangeFragment dateChangeFragment = new DateChangeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_date_change, dateChangeFragment).commit();
        }
    }
}