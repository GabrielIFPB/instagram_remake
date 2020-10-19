package com.inteligenciadigital.instagramremake.main.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.inteligenciadigital.instagramremake.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = this.findViewById(R.id.main_toolbar);
        this.setSupportActionBar(toolbar);

        if (this.getSupportActionBar() != null) {
            Drawable drawable = this.getResources().getDrawable(R.drawable.ic_insta_camera);
            this.getSupportActionBar().setHomeAsUpIndicator(drawable);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}