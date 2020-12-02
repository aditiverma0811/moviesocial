package com.apps.anant.mocial.Activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;

import com.apps.anant.mocial.R;
import com.apps.anant.mocial.TypeWriter;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        final TypeWriter tv_moto = (TypeWriter) findViewById(R.id.textView_moto);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/arista_regulartrial.ttf");
        tv_moto.setTypeface(myCustomFont);
        tv_moto.setText("");
        tv_moto.setCharacterDelay(70);
        tv_moto.animateText("Where movies bring you close. .");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator anim = ObjectAnimator.ofInt(tv_moto,"textColor", ResourcesCompat.getColor(getResources(), R.color.colorLove, null), Color.WHITE,ResourcesCompat.getColor(getResources(), R.color.colorLove, null));
                anim.setDuration(500);
                anim.setEvaluator(new ArgbEvaluator());
                anim.setRepeatMode(Animation.ABSOLUTE);
                anim.setRepeatCount(0);
                anim.start();
                Intent submitintent = new Intent(Splash.this,Login.class);
                submitintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Splash.this.startActivity(submitintent);
                finish();
            }
        },2000);

    }
}
