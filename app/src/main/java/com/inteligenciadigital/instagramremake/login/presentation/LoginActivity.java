package com.inteligenciadigital.instagramremake.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.inteligenciadigital.instagramremake.R;

public class LoginActivity extends AppCompatActivity {

	private TestButton buttonEnter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final EditText editTextEmail = this.findViewById(R.id.login_edit_text_email);
		final EditText editTextPassword = this.findViewById(R.id.login_edit_text_password);

		editTextEmail.addTextChangedListener(watcher);
		editTextPassword.addTextChangedListener(watcher);

		this.buttonEnter = this.findViewById(R.id.login_button_enter);
		this.buttonEnter.setOnClickListener(v -> {
			this.buttonEnter.showProgress(true);
			new Handler().postDelayed(() -> {
				this.buttonEnter.showProgress(false);

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

			}, 4000);
		});
	}

	private TextWatcher watcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			if (!charSequence.toString().isEmpty())
				buttonEnter.setEnabled(true);
			else
				buttonEnter.setEnabled(false);
		}

		@Override
		public void afterTextChanged(Editable editable) {

		}
	};
}