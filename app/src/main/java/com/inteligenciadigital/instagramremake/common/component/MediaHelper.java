package com.inteligenciadigital.instagramremake.common.component;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MediaHelper {

	private static final int CAMERA_CODE = 174;
	private static final int GALLERY_CODE = 175;

	private static WeakReference<MediaHelper> INSTANCE;

	private Activity activity;
	private Fragment fragment;

	private Uri mCropImageUri;

	private OnImageCroppedListener listener;
	private Uri mSavedImageUri;

	public static MediaHelper getInstance(Activity activity) {
		createInstance();
		INSTANCE.get().setActivity(activity);
		return INSTANCE.get();
	}

	public static MediaHelper getInstance(Fragment fragment) {
		createInstance();
		INSTANCE.get().setFragment(fragment);
		return INSTANCE.get();
	}

	private void setActivity(Activity activity) {
		this.activity = activity;
	}

	private static void createInstance() {
		if (INSTANCE == null) {
			MediaHelper mediaHelper = new MediaHelper();
			INSTANCE = new WeakReference<>(mediaHelper);
		}
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

				this.saveReferenceImageUri();

				intent.putExtra(MediaStore.EXTRA_OUTPUT, this.mCropImageUri);
				this.activity.startActivityForResult(intent, CAMERA_CODE);
			}
		}
	}

	private File createImageFile() throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		String imageFileName = "JPEG_" + timestamp + "_";

		File storageDir = this.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return File.createTempFile(
				imageFileName, /* prefix */
				".jpg",  /* suffix */
				storageDir      /* directory */
		);
	}

	private void saveReferenceImageUri() {
		SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("camera_image", 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("url", this.mCropImageUri.toString());
		editor.apply();
	}

	public MediaHelper cropView(CropImageView cropImageView) {
		cropImageView = cropImageView;
		cropImageView.setAspectRatio(1, 1);
		cropImageView.setFixedAspectRatio(true);
		cropImageView.setOnCropImageCompleteListener((view, result) -> {
			Uri uri = result.getUri();
			if (uri != null && listener != null) {
				listener.onImageCropped(uri);
			}
		});
		return this;
	}

	public MediaHelper listener(OnImageCroppedListener listener) {
		this.listener = listener;
		return this;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("camera_image", 0);
		String url = sharedPreferences.getString("url", null);

		if (this.mCropImageUri == null && url != null)
			this.mCropImageUri = Uri.parse(url);

		if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
			if (CropImage.isReadExternalStoragePermissionsRequired(this.getContext(), this.mCropImageUri)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (this.activity != null) {
						this.activity.requestPermissions(
								new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
								CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
						);
					} else {
						this.fragment.requestPermissions(
								new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
								CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
						);
					}
				}
			} else {
				this.listener.onImagePicked(this.mCropImageUri);
			}
		} else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
			this.mCropImageUri = CropImage.getPickImageResultUri(this.getContext(), data);
			this.listener.onImagePicked(this.mCropImageUri);
		}
	}

	private Context getContext() {
		if (this.fragment != null && this.fragment.getActivity() != null)
			return this.fragment.getActivity();
		return this.activity;
	}

	public void cropImage(CropImageView cropImageView) {
		File getImage = this.getContext().getExternalCacheDir();
		if (getImage != null)
			this.mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpg"));
		cropImageView.saveCroppedImageAsync(this.mSavedImageUri);
	}

	public boolean checkCameraHardware(Context context) {
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	public Camera getCameraInstance() {
		Camera camera = null;
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (this.getContext() != null && this.getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
					if (this.activity != null)
						this.activity.requestPermissions(new String[] { Manifest.permission.CAMERA }, 300);
					else
						this.fragment.requestPermissions(new String[] { Manifest.permission.CAMERA }, 300);
				}
			}
			camera = Camera.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return camera;
	}

	public void saveCameraFile(byte[] data) {
		File pictureFile = this.createCameraFile(true);

		if (pictureFile == null) {
			Log.d("TESTE", "Error creating media file, check storage permission");
			return;
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);

			Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);

			ExifInterface exifInterface = new ExifInterface(pictureFile.toString());

			Log.d("TESTE", exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
			if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6"))
				realImage = this.rotate(realImage, 90);
			else if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8"))
				realImage = this.rotate(realImage, 270);
			else if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3"))
				realImage = this.rotate(realImage, 180);
			else if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0"))
				realImage = this.rotate(realImage, 90);

			realImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

			fileOutputStream.close();

			Matrix matrix = new Matrix();
			File outputMediaFile = this.createCameraFile(false);
			if (outputMediaFile == null) {
				Log.d("TESTE", "Error creating media file, check storage permissions");
				return;
			}

			Log.d("TESTE", realImage.getWidth() + " " + realImage.getHeight());
			Bitmap result = Bitmap.createBitmap(realImage, 0, 0,
					realImage.getWidth(), realImage.getWidth(), matrix, true);

			fileOutputStream = new FileOutputStream(outputMediaFile);
			result.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
			fileOutputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Bitmap rotate(Bitmap bitmap, int degree) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.setRotate(degree);

		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}

	private File createCameraFile(boolean temp) {
		if (this.getContext() == null) return null;

		File mediaStorageDir = this.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		if (mediaStorageDir != null && !mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("TESTE", "failed to create directory");
				return null;
			}
		}
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		return new File(mediaStorageDir.getPath() + File.separator + (temp ? "TEMP_" : "IMG_") + timestamp + ".jpg");
	}

	public interface OnImageCroppedListener {
		void onImageCropped(Uri uri);

		void onImagePicked(Uri mCropImageUri);
	}
}
