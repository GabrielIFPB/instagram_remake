package com.inteligenciadigital.instagramremake.login.presentation;

import com.inteligenciadigital.instagramremake.login.datasource.LoginDataSource;

import android.os.Handler;

public class LoginPresenter {

	private final LoginView loginView;
	private final LoginDataSource dataSource;

	public LoginPresenter(LoginView loginView, LoginDataSource dataSource) {
		this.loginView = loginView;
		this.dataSource = dataSource;
	}

	public void login(String emial, String password) {
		this.loginView.showProgressBar();

		new Handler().postDelayed(() -> {
			this.loginView.hideProgressBar();
			this.loginView.onFailureForm("Error01", "Error02");
		}, 2000);
	}
}
