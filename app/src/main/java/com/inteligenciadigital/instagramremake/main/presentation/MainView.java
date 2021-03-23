package com.inteligenciadigital.instagramremake.main.presentation;

import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.view.ViewProgressBar;

import java.util.List;

public interface MainView extends ViewProgressBar {

	void scrollToolbarEnabled(boolean enabled);

	void showProfile(String user);

	void disposeProfileDetail();

	void logout();

	interface ProfileView extends ViewProgressBar {

		void showPhoto(String uri);

		void showData(String name, String following, String followers,
		              String posts, boolean editProfile, boolean follow);

		void showPosts(List<Post> posts);
	}

	interface HomeView extends ViewProgressBar {

		void showFeed(List<Feed> response);
	}

	interface SearchView {

		void showUsers(List<User> users);
	}
}
