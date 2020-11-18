package com.inteligenciadigital.instagramremake.register.datasource;

import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public interface RegisterDataSource {

	void createUser(String name, String email, String password, Presenter presenter);

	void addPhoto(Uri uri, Presenter presenter);
}
