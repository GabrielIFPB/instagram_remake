package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.net.Uri;

import com.inteligenciadigital.instagramremake.common.view.ViewProgressBar;

import java.util.List;

public interface GalleryView extends ViewProgressBar {

	void onPicturesLoaded(List<Uri> uris);
}
