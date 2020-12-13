package com.inteligenciadigital.instagramremake.main.search.datasource;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.List;

public class SearchLocalDataSource implements SearchDataSource {

	@Override
	public void findUser(String query, Presenter<List<User>> presenter) {
		Database database = Database.getInstance();
		database.findUsers(database.getUser().getUUID(), query)
				.addOnSuccessListener(presenter::onSuccess)
				.addOnFailureListener(e -> presenter.onError(e.getMessage()) )
				.addOnCompleteListener(presenter::onComplete);
	}
}
