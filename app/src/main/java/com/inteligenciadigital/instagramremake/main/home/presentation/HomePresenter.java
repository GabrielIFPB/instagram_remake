package com.inteligenciadigital.instagramremake.main.home.presentation;

import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.main.home.datasource.HomeDataSource;
import com.inteligenciadigital.instagramremake.main.presentation.MainView;

import java.util.List;

public class HomePresenter implements Presenter<List<Feed>> {

	private final HomeDataSource dataSource;
	private MainView.HomeView homeView;

	public HomePresenter(HomeDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setView(MainView.HomeView homeView) {
		this.homeView = homeView;
	}

	public void findFeed() {
		this.homeView.showProgressBar();
		this.dataSource.findFeed(this);
	}

	@Override
	public void onSuccess(List<Feed> response) {
		this.homeView.showFeed(response);
	}

	@Override
	public void onError(String message) {

	}

	@Override
	public void onComplete() {
		this.homeView.hideProgressBar();
	}
}
