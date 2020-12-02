package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.main.camera.datasource.AddDataSource;

public class AddPresenter implements Presenter<Void> {

	private AddCaptionView addCaptionView;
	private AddDataSource dataSource;

	public AddPresenter(AddCaptionView addCaptionView, AddDataSource dataSource) {
		this.addCaptionView = addCaptionView;
		this.dataSource = dataSource;
	}

	public void createPost(Uri uri, String caprion) {
		this.addCaptionView.showProgressBar();
		this.dataSource.savePost(uri,caprion, this);
	}

	@Override
	public void onSuccess(Void userAuth) {
		this.addCaptionView.postSaved();
	}

	@Override
	public void onError(String message) {

	}

	@Override
	public void onComplete() {
		this.addCaptionView.hideProgressBar();
	}
}
