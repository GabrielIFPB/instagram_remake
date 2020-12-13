package com.inteligenciadigital.instagramremake.main.search.presentation;

import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.main.presentation.MainView;
import com.inteligenciadigital.instagramremake.main.search.datasource.SearchDataSource;

import java.util.List;

public class SearchPresenter implements Presenter<List<User>> {

	private final SearchDataSource dataSource;
	private MainView.SearchView searchView;

	public SearchPresenter(SearchDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setView(MainView.SearchView searchView) {
		this.searchView = searchView;
	}

	public void findUsers(String newText) {
		dataSource.findUser(newText, this);
	}

	@Override
	public void onSuccess(List<User> response) {
		this.searchView.showUsers(response);
	}

	@Override
	public void onError(String message) {

	}

	@Override
	public void onComplete() {
	}
}
