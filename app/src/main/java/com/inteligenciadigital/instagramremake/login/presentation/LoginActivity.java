package com.inteligenciadigital.instagramremake.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.inteligenciadigital.instagramremake.R;

public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		TextInputLayout inputLayout = this.findViewById(R.id.login_edit_text_email_input);
		inputLayout.setError("Esse email é inválido");

		EditText editText = this.findViewById(R.id.login_edit_text_email);
		editText.setBackground(
				ContextCompat.getDrawable(LoginActivity.this,
						R.drawable.edit_text_background_error));
	}
}