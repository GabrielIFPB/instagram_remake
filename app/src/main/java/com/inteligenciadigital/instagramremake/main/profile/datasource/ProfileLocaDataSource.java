package com.inteligenciadigital.instagramremake.main.profile.datasource;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.models.UserProfile;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.List;

public class ProfileLocaDataSource implements ProfileDataSource {

	@Override
	public void findUser(Presenter<UserProfile> presenter) {
		Database database = Database.getInstance();
		database.findUser(database.getUser().getUUID())
				.addOnSuccessListener((Database.OnSuccessListener<User>) user -> {
					database.findPosts(user.getUuid())
							.addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
								presenter.onSuccess(new UserProfile(user, posts));
								presenter.onComplete();
							});
				});
	}
}
