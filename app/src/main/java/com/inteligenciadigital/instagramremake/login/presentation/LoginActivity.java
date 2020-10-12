package com.inteligenciadigital.instagramremake.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.inteligenciadigital.instagramremake.R;

public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final EditText editTextEmail = this.findViewById(R.id.login_edit_text_email);
		final EditText editTextPassword = this.findViewById(R.id.login_edit_text_password);

		editTextEmail.addTextChangedListener(watcher);
		editTextPassword.addTextChangedListener(watcher);

		this.findViewById(R.id.login_button_enter).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TextInputLayout inputLayoutEmail = findViewById(R.id.login_edit_text_email_input);
				inputLayoutEmail.setError("Esse email é inválido");

				editTextEmail.setBackground(
						ContextCompat.getDrawable(LoginActivity.this,
								R.drawable.edit_text_background_error));

				TextInputLayout inputLayoutPassword = findViewById(R.id.login_edit_text_password_input);
				inputLayoutPassword.setError("Senha incorreta");

				editTextPassword.setBackground(
						ContextCompat.getDrawable(LoginActivity.this,
								R.drawable.edit_text_background_error));
			}
		});
	}

	private TextWatcher watcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			if (!charSequence.toString().isEmpty())
				findViewById(R.id.login_button_enter).setEnabled(true);
			else
				findViewById(R.id.login_button_enter).setEnabled(false);
		}

		@Override
		public void afterTextChanged(Editable editable) {

		}
	};
}