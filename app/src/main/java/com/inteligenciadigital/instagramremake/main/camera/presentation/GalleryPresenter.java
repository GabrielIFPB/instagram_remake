package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.content.Context;
import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.main.camera.datasource.GalleryDataSource;

import java.util.ArrayList;
import java.util.List;

public class GalleryPresenter implements Presenter<List<String>> {

	private final GalleryDataSource dataSource;
	private GalleryView galleryView;

	public GalleryPresenter(GalleryDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void findPictures(Context context) {
		this.galleryView.showProgressBar();
		this.dataSource.findPictures(context, this);
	}

	public void setView(GalleryView galleryView) {
		this.galleryView = galleryView;
	}

	@Override
	public void onSuccess(List<String> response) {
		List<Uri> uris = new ArrayList<>();

		for (String res: response) {
			Uri uri = Uri.parse(res);
			uris.add(uri);
		}
		this.galleryView.onPicturesLoaded(uris);
	}

	@Override
	public void onError(String message) {

	}

	@Override
	public void onComplete() {
		this.galleryView.hideProgressBar();
	}
}
