package com.inteligenciadigital.instagramremake.register.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.inteligenciadigital.instagramremake.R;

public class RegisterActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window windows = this.getWindow();
			windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			windows.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
		}
	}
}