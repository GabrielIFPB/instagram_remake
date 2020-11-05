package com.inteligenciadigital.instagramremake.register.presentation;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.util.Strings;

public class RegisterPresenter {

	private RegisterView registerView;
	private RegisterView.EmailView emailView;
	private RegisterView.NamePasswordView namePasswordView;

	private String name;
	private String email;
	private String password;

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
	}
}
