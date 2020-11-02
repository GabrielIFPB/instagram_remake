package com.inteligenciadigital.instagramremake.login.presentation;

import com.inteligenciadigital.instagramremake.common.view.ViewProgressBar;

public interface LoginView extends ViewProgressBar {

	void onFailureForm(String emaiError, String passwordError);

	void onUserLogged();
}
