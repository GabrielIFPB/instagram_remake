package com.inteligenciadigital.instagramremake.main.profile.presentation;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.models.UserProfile;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.main.presentation.MainView;
import com.inteligenciadigital.instagramremake.main.profile.datasource.ProfileDataSource;

import java.util.List;

public class ProfilePresenter implements Presenter<UserProfile> {

	private final ProfileDataSource datasource;
	private MainView.ProfileView view;

	public ProfilePresenter(ProfileDataSource profileDataSource) {
		this.datasource = profileDataSource;
	}

	@Override
	public void onSuccess(UserProfile userProfile) {
		User user = userProfile.getUser();
		List<Post> posts = userProfile.getPosts();

		boolean editProfile = user.getUuid().equals(Database.getInstance().getUser().getUUID());

		String following = String.valueOf(user.getFollowing());
		String followers = String.valueOf(user.getFollowers());
		String userPosts = String.valueOf(user.getPosts());

		this.view.showData(user.getName(), following, followers, userPosts, editProfile);

		this.view.showPosts(posts);

		if (user.getUri() != null) {
			this.view.showPhoto(user.getUri());
		}
	}

	@Override
	public void onError(String message) {

	}

	@Override
	public void onComplete() {
		this.view.hideProgressBar();
	}

	public void setView(MainView.ProfileView view) {
		this.view = view;
	}

	public void findUser(String user) {
		this.view.showProgressBar();
		this.datasource.findUser(user, this);
	}
}
