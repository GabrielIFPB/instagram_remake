package com.inteligenciadigital.instagramremake.login.presentation;

public interface LoginView {

	void onFailureForm(String emaiError, String passwordError);

	void onUserLogged();
}
