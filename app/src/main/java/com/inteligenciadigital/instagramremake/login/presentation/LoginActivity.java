package com.inteligenciadigital.instagramremake.login.presentation;

import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;
import com.inteligenciadigital.instagramremake.common.view.LoadingButton;

import butterknife.BindView;

public class LoginActivity extends AbstractActivity implements LoginView {

	@BindView(R.id.login_button_enter) LoadingButton buttonEnter;
	@BindView(R.id.login_edit_text_email) EditText editTextEmail;
	@BindView(R.id.login_edit_text_password) EditText editTextPassword;
	@BindView(R.id.login_edit_text_email_input) TextInputLayout inputLayoutEmail;
	@BindView(R.id.login_edit_text_password_input) TextInputLayout inputLayoutPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window windows = this.getWindow();
			windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			windows.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
		}

		editTextEmail.addTextChangedListener(watcher);
		editTextPassword.addTextChangedListener(watcher);

		this.buttonEnter.setOnClickListener(v -> {
			this.buttonEnter.showProgress(true);
			new Handler().postDelayed(() -> {
				this.buttonEnter.showProgress(false);

			}, 4000);
		});
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_login;
	}

	private TextWatcher watcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			buttonEnter.setEnabled(!charSequence.toString().isEmpty());
		}

		@Override
		public void afterTextChanged(Editable editable) {

		}
	};

	@Override
	public void onFailureForm(String emaiError, String passwordError) {
		if (emaiError != null) {
			this.inputLayoutEmail.setError(emaiError);
			this.editTextEmail.setBackground(this.findDrawable(R.drawable.edit_text_background_error));
		}
	}

	@Override
	public void onUserLogged() {
		// TODO: fazer depois
	}
}