package com.inteligenciadigital.instagramremake.login.presentation;

import android.content.Context;

import com.inteligenciadigital.instagramremake.common.view.ViewProgressBar;

public interface LoginView extends ViewProgressBar {

	void onFailureForm(String emailError, String passwordError);

	void onUserLogged();
}
