package com.inteligenciadigital.instagramremake.main.profile.datasource;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.models.UserProfile;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.List;

public class ProfileLocaDataSource implements ProfileDataSource {

	@Override
	public void findUser(String uuid, Presenter<UserProfile> presenter) {
		Database database = Database.getInstance();
		database.findUser(uuid)
				.addOnSuccessListener((Database.OnSuccessListener<User>) user1 -> {
					database.findPosts(uuid)
							.addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
								database.following(database.getUser().getUUID(), uuid)
										.addOnSuccessListener((Database.OnSuccessListener<Boolean>) following -> {
											presenter.onSuccess(new UserProfile(user1, posts, following));
											presenter.onComplete();
										});
							});
				});
	}
}
