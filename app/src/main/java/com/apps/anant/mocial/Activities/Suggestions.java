package com.apps.anant.mocial.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.anant.mocial.R;

public class Suggestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
