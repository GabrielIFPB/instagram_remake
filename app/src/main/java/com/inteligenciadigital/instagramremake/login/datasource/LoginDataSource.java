package com.inteligenciadigital.instagramremake.login.datasource;

import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public interface LoginDataSource {

	void login(String email, String password, Presenter presenter);
}
