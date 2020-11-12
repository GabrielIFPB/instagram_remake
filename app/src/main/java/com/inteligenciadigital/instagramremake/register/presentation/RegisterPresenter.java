package com.inteligenciadigital.instagramremake.register.presentation;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.models.UserAuth;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.common.util.Strings;
import com.inteligenciadigital.instagramremake.register.datasource.RegisterDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {

	private RegisterView registerView;
	private RegisterView.EmailView emailView;
	private RegisterView.NamePasswordView namePasswordView;

	private final RegisterDataSource dataSource;

	private String name;
	private String email;
	private String password;

	public RegisterPresenter(RegisterDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void showPhotoView() {
		this.registerView.showNextView(RegisterSteps.PHOTO);
	}

	public void setRegisterView(RegisterView registerView) {
		this.registerView = registerView;
	}

	public void setEmailView(RegisterView.EmailView emailView) {
		this.emailView = emailView;
	}

	public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView) {
		this.namePasswordView = namePasswordView;
	}

	public void setEmail(String email) {
		if (!Strings.emailValid(email)) {
			String invalid_email = this.emailView.getContext().getString(R.string.invalid_email);
			this.emailView.onFailureForm(invalid_email);
			return;
		}
		this.email = email;
		this.registerView.showNextView(RegisterSteps.NAME_PASSWORD);
	}

	public void setNameAndPassword(String name, String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			String password_not_equal = this.namePasswordView.getContext().getString(R.string.password_not_equal);
			this.namePasswordView.onFailureForm(null, password_not_equal);
		}
		this.name = name;
		this.password = password;

		this.namePasswordView.showProgressBar();
		this.dataSource.createUser(this.name, this.email, this.password, this);
	}

	@Override
	public void onSuccess(UserAuth userAuth) {
		this.registerView.showNextView(RegisterSteps.WELCOME);
	}

	@Override
	public void onError(String message) {
		this.namePasswordView.onFailureCreateUser(message);
	}

	@Override
	public void onComplete() {
		this.namePasswordView.hideProgressBar();
	}
}
