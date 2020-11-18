package com.inteligenciadigital.instagramremake.register.datasource;

import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.UserAuth;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {

	@Override
	public void createUser(String name, String email, String password, Presenter presenter) {
		Database.getInstance().createUser(name, email, password)
				.addOnSuccessListener((Database.OnSuccessListener<UserAuth>) presenter::onSuccess)
				.addOnFailureListener(e -> presenter.onError(e.getMessage()))
				.addOnCompleteListener(presenter::onComplete);
	}

	@Override
	public void addPhoto(Uri uri, Presenter presenter) {
		Database database = Database.getInstance();
		database.addPhoto(database.getUser().getUUID(), uri)
			.addOnSuccessListener((Database.OnSuccessListener<Boolean>) presenter::onSuccess);
	}
}
