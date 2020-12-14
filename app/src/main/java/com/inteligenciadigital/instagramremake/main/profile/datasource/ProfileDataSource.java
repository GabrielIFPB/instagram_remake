package com.inteligenciadigital.instagramremake.main.profile.datasource;

import com.inteligenciadigital.instagramremake.common.models.UserProfile;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public interface ProfileDataSource {

	void findUser(String user, Presenter<UserProfile> presenter);

	void follow(String user);

	void unfollow(String user);
}
