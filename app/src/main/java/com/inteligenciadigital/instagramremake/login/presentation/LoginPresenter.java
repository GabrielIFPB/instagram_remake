package com.inteligenciadigital.instagramremake.login.presentation;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.models.UserAuth;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.common.util.Strings;
import com.inteligenciadigital.instagramremake.login.datasource.LoginDataSource;

public class LoginPresenter implements Presenter {

	private final LoginView loginView;
	private final LoginDataSource dataSource;

	public LoginPresenter(LoginView loginView, LoginDataSource dataSource) {
		this.loginView = loginView;
		this.dataSource = dataSource;
	}

	public void login(String email, String password) {
		String context = this.loginView.getContext().getString(R.string.invalid_email);
		if (!Strings.emailValid(email)) {
			this.loginView.onFailureForm(context, null);
			return;
		}

		this.loginView.showProgressBar();
		this.dataSource.login(email, password, this);
	}

	@Override
	public void onSuccess(UserAuth userAuth) {
		this.loginView.onUserLogged();
	}

	@Override
	public void onError(String message) {
		this.loginView.onFailureForm(null, message);
	}

	@Override
	public void onComplete() {
		this.loginView.hideProgressBar();
	}
}
