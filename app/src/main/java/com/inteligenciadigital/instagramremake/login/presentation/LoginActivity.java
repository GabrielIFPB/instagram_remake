package com.inteligenciadigital.instagramremake.login.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.UserAuth;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;
import com.inteligenciadigital.instagramremake.common.component.LoadingButton;
import com.inteligenciadigital.instagramremake.login.datasource.LoginDataSource;
import com.inteligenciadigital.instagramremake.login.datasource.LoginFireDataSource;
import com.inteligenciadigital.instagramremake.login.datasource.LoginLocalDataSource;
import com.inteligenciadigital.instagramremake.main.presentation.MainActivity;
import com.inteligenciadigital.instagramremake.register.presentation.RegisterActivity;

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

	public static void launch(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setStatusBarDark();

		//FirebaseAuth.getInstance().signOut();
		String user = FirebaseAuth.getInstance().getUid();
		if (user != null)
			this.onUserLogged();
	}

	@Override
	protected void onInject() {
		LoginDataSource dataSource = new LoginFireDataSource();
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

	@Override
	public void onUserLogged() {
		MainActivity.launch(this, MainActivity.LOGIN_ACTIVITY);
		this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@OnClick(R.id.login_button_enter)
	public void onButtonEnterClick() {
		String email = this.editTextEmail.getText().toString();
		String password = this.editTextPassword.getText().toString();
		this.loginPresenter.login(email, password);
	}

	@OnClick(R.id.login_text_view_register)
	public void onTextViewRegisterClick() {
		RegisterActivity.launch(this);
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
	public void onFailureForm(String emailError, String passwordError) {
		if (emailError != null) {
			this.inputLayoutEmail.setError(emailError);
			this.editTextEmail.setBackground(this.findDrawable(R.drawable.edit_text_background_error));
		}

		if (passwordError != null) {
			this.inputLayoutPassword.setError(passwordError);
			this.editTextPassword.setBackground(this.findDrawable(R.drawable.edit_text_background_error));
		}
	}
}