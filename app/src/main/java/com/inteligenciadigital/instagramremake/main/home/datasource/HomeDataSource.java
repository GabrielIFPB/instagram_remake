package com.inteligenciadigital.instagramremake.main.home.datasource;

import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.List;

public interface HomeDataSource {

	void findFeed(Presenter<List<Feed>> presenter);
}
