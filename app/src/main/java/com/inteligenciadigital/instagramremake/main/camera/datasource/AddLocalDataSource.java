package com.inteligenciadigital.instagramremake.main.camera.datasource;

import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public class AddLocalDataSource implements AddDataSource {

	@Override
	public void savePost(Uri uri, String caption, Presenter presenter) {
		Database database = Database.getInstance();
		database.createPost(
				database.getUser().getUUID(), uri, caption)
			.addOnSuccessListener((Database.OnSuccessListener<Void>) presenter::onSuccess)
			.addOnFailureListener(e -> presenter.onError(e.getMessage()))
			.addOnCompleteListener(presenter::onComplete);
	}
}
