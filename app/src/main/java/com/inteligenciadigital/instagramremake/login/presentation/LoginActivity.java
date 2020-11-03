package com.inteligenciadigital.instagramremake.login.presentation;

import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;
import com.inteligenciadigital.instagramremake.common.view.LoadingButton;
import com.inteligenciadigital.instagramremake.login.datasource.LoginDataSource;
import com.inteligenciadigital.instagramremake.login.datasource.LoginLocalDataSource;
import com.inteligenciadigital.instagramremake.main.presentation.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AbstractActivity implements LoginView {

	@BindView(R.id.login_button_enter)
	LoadingButton buttonEnter;
	@BindView(R.id.login_edit_text_email)
	EditText editTextEmail;
	@BindView(R.id.login_edit_text_password)
	EditText editTextPassword;
	@BindView(R.id.login_edit_text_email_input)
	TextInputLayout inputLayoutEmail;
	@BindView(R.id.login_edit_text_password_input)
	TextInputLayout inputLayoutPassword;

	private LoginPresenter loginPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window windows = this.getWindow();
			windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			windows.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
		}
	}

	@Override
	protected void onInject() {
		LoginDataSource dataSource = new LoginLocalDataSource();
		this.loginPresenter = new LoginPresenter(this, dataSource);
	}

	@Override
	public void showProgressBar() {
		this.buttonEnter.showProgress(true);
	}

	@Override
	public void hideProgressBar() {
		this.buttonEnter.showProgress(false);
	}

	@OnClick(R.id.login_button_enter)
	public void onButtonEnterClick() {
		String email = this.editTextEmail.getText().toString();
		String password = this.editTextPassword.getText().toString();
		this.loginPresenter.login(email, password);
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_login;
	}

	@OnTextChanged({R.id.login_edit_text_email, R.id.login_edit_text_password})
	public void onTextChanged(CharSequence s) {
		String email = this.editTextEmail.getText().toString();
		String password = this.editTextPassword.getText().toString();
		boolean enabled = !email.isEmpty() && !password.isEmpty();
		this.buttonEnter.setEnabled(enabled);
		this.cleanErrorEmail(s);
		this.cleanErrorPassword(s);
	}

	private void cleanErrorEmail(CharSequence s) {
		if (s.hashCode() == this.editTextEmail.getText().hashCode()) {
			this.editTextEmail.setBackground(this.findDrawable(R.drawable.edit_text_background));
			this.inputLayoutEmail.setError(null);
			this.inputLayoutEmail.setErrorEnabled(false);
		}
	}

	private void cleanErrorPassword(CharSequence s) {
		if (s.hashCode() == this.editTextPassword.getText().hashCode()) {
			this.editTextPassword.setBackground(this.findDrawable(R.drawable.edit_text_background));
			this.inputLayoutPassword.setError(null);
			this.inputLayoutPassword.setErrorEnabled(false);
		}
	}

	@Override
	public void onFailureForm(String emaiError, String passwordError) {
		if (emaiError != null) {
			this.inputLayoutEmail.setError(emaiError);
			this.editTextEmail.setBackground(this.findDrawable(R.drawable.edit_text_background_error));
		}

		if (passwordError != null) {
			this.inputLayoutPassword.setError(passwordError);
			this.editTextPassword.setBackground(this.findDrawable(R.drawable.edit_text_background_error));
		}
	}

	@Override
	public void onUserLogged() {
		// TODO: fazer depois
		MainActivity.launch(this);
	}
}