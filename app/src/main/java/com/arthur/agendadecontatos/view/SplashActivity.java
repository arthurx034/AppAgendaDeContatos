package com.arthur.agendadecontatos.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.arthur.agendadecontatos.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logoImage);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_scale);
        logo.startAnimation(animation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, Login.class));
            finish();
        }, 2000); // 2 segundos de splash
    }
}
