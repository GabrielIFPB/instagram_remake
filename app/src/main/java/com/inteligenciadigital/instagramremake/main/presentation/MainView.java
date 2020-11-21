package com.inteligenciadigital.instagramremake.main.presentation;

import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.view.ViewProgressBar;

import java.util.List;

public interface MainView extends ViewProgressBar {

	void scrollToolbarEnabled(boolean enabled);

	public interface ProfileView extends ViewProgressBar {

		void showPhoto(Uri uri);

		void showData(String name, String following, String followers, String posts);

		void showPosts(List<Post> posts);
	}
}
