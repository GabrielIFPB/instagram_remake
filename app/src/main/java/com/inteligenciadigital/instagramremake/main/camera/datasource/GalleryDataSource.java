package com.inteligenciadigital.instagramremake.main.camera.datasource;

import android.content.Context;

import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public interface GalleryDataSource {

	void findPictures(Context context, Presenter presenter);
}
