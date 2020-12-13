package com.inteligenciadigital.instagramremake.main.search.datasource;

import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.List;

public interface SearchDataSource {

	void findUser(String query, Presenter<List<User>> presenter);
}
