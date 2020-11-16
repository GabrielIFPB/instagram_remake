package com.inteligenciadigital.instagramremake.common.component;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MediaHelper {

	private static final int CAMERA_CODE = 174;
	private static final int GALLERY_CODE = 175;

	private static MediaHelper INSTANCE;

	private Activity activity;
	private Fragment fragment;

	private Uri mCropImageUri;
	private CropImageView cropImageView;

	private OnImageCroppedListener listener;
	private Uri mSavedImageUri;

	public static MediaHelper getInstance(Activity activity) {
		if (INSTANCE == null)
			INSTANCE = new MediaHelper();
		INSTANCE.setActivity(activity);
		return INSTANCE;
	}

	private void setActivity(Activity activity) {
		this.activity = activity;
	}

	public static MediaHelper getInstance(Fragment fragment) {
		if (INSTANCE == null)
			INSTANCE = new MediaHelper();
		INSTANCE.setFragment(fragment);
		return INSTANCE;
	}

	private void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	public void chooserGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		this.activity.startActivityForResult(intent, GALLERY_CODE);
	}

	public void chooserCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		PackageManager packageManager = this.getContext().getPackageManager();
		if (intent.resolveActivity(packageManager) != null) {
			File photoFile = null;
			try {
				photoFile = this.createImageFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (photoFile != null) {
				this.mCropImageUri = FileProvider.getUriForFile(
						this.getContext(),
						"com.inteligenciadigital.instagramremake.fileprovider",
						photoFile
				);
//				this.mCropImageUri = CropImage.getPickImageResultUri(this.getContext(), intent);

				intent.putExtra(MediaStore.EXTRA_OUTPUT, this.mCropImageUri);
				this.activity.startActivityForResult(intent, CAMERA_CODE);
			}
		}
	}

	public MediaHelper cropView(CropImageView cropImageView) {
		this.cropImageView = cropImageView;
		this.cropImageView.setAspectRatio(1, 1);
		this.cropImageView.setFixedAspectRatio(true);
		this.cropImageView.setOnCropImageCompleteListener((view, result) -> {
			Uri uri = result.getUri();
			if (uri != null && listener != null) {
				listener.onImageCropped(uri);
				this.cropImageView.setVisibility(View.GONE);
			}
		});
		return this;
	}

	public MediaHelper listener(OnImageCroppedListener listener) {
		this.listener = listener;
		return this;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
			if (CropImage.isReadExternalStoragePermissionsRequired(this.getContext(), this.mCropImageUri)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (this.activity != null) {
						this.activity.requestPermissions(
									new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
									CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
								);
					} else {
						this.fragment.requestPermissions(
								new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
								CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
						);
					}
				}
			} else {
//				Bundle extras = data.getExtras();
//				Bitmap imageBitmap = (Bitmap) extras.get("data");
//				cropImageView.setImageBitmap(imageBitmap);

				this.startCropImageActivity();
			}
		} else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
			this.mCropImageUri = CropImage.getPickImageResultUri(this.getContext(), data);
			this.startCropImageActivity();
		}
	}

	private void startCropImageActivity() {
		this.cropImageView.setImageUriAsync(this.mCropImageUri);
	}

	private Context getContext() {
		if (this.fragment != null && this.fragment.getActivity() != null)
			return this.fragment.getActivity();
		return this.activity;
	}

	public void cropImage() {
		File getImage = this.getContext().getExternalCacheDir();
		if (getImage != null)
			this.mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpg"));
		this.cropImageView.saveCroppedImageAsync(this.mSavedImageUri);
	}

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";

		File storageDir = this.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);
	}

	public interface OnImageCroppedListener {
		void onImageCropped(Uri uri);
	}
}
