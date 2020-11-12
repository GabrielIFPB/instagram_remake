package com.inteligenciadigital.instagramremake.register.presentation;

import android.content.Context;

import com.inteligenciadigital.instagramremake.common.view.ViewProgressBar;

public interface RegisterView {

	void showNextView(RegisterSteps steps);

	void onUserCreate();

	interface EmailView {

		Context getContext();

		void onFailureForm(String emailError);
	}

	interface NamePasswordView extends ViewProgressBar {

		Context getContext();

		void onFailureForm(String nameError, String passwordError);

		void onFailureCreateUser(String message);
	}

	interface WelcomeView {}

	interface PhotoView {}
}
