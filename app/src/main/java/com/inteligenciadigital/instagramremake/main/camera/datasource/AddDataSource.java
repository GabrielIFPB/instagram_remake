package com.inteligenciadigital.instagramremake.main.camera.datasource;

import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public interface AddDataSource {

	void savePost(Uri uri, String caption, Presenter presenter);
}
