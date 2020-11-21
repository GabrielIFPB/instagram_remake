package com.inteligenciadigital.instagramremake.main.home.datasource;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.List;

public class HomeLocalDataSource implements HomeDataSource {

	@Override
	public void findFeed(Presenter<List<Feed>> presenter) {
		Database database = Database.getInstance();
		database.findFeed(database.getUser().getUUID())
				.addOnSuccessListener(presenter::onSuccess)
				.addOnFailureListener(e -> presenter.onError(e.getMessage()))
				.addOnCompleteListener(presenter::onComplete);
	}
}
