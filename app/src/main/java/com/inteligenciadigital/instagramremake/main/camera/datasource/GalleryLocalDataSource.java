package com.inteligenciadigital.instagramremake.main.camera.datasource;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class GalleryLocalDataSource implements GalleryDataSource {

	@Override
	public void findPictures(Context context, Presenter presenter) {
		List<String> images = null;

		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		String[] projection = {
				MediaStore.MediaColumns.DATA,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.MediaColumns.DATE_ADDED
		};

		if (context != null && context.getContentResolver() != null) {
			Cursor cursor = context.getContentResolver().query(uri, projection, null, null,
					MediaStore.Images.Media.DATE_ADDED);

			if (cursor != null) {
				images = new ArrayList<>();

				int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
				int columnIndexFolder = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
				int columnIndexDateAdded = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);

				int i = 0;
				while (cursor.moveToNext() && i < 10) {
					String absolutePathImage = cursor.getString(columnIndexData);
					String folder = cursor.getString(columnIndexFolder);
					String dataAdded = cursor.getString(columnIndexDateAdded);

					Log.d("TESTE FOLDER", folder);
					Log.d("TESTE dataAdded", dataAdded);
					images.add(absolutePathImage);
					i++;
				}
			}
		}

		if (images != null && !images.isEmpty())
			presenter.onSuccess(images);
	}
}
