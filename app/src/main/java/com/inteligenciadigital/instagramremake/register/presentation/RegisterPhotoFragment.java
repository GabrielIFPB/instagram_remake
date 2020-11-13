package com.inteligenciadigital.instagramremake.register.presentation;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.component.CustomDialog;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.common.component.LoadingButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

	@BindView(R.id.register_button_next)
	LoadingButton buttonNext;

	@BindView(R.id.register_camera_icon)
	ImageView imageViewCropped;

	public RegisterPhotoFragment() {
	}

	public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
		RegisterPhotoFragment fragment = new RegisterPhotoFragment();
		presenter.setPhotoView(fragment);
		fragment.setPresenter(presenter);
		return fragment;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		this.buttonNext.setEnabled(true);
	}

	@OnClick(R.id.register_button_next)
	public void onButtonNextClick() {
		CustomDialog customDialog = new CustomDialog.Builder(this.getContext())
				.setTitle(R.string.photo_register_title)
				.addButton((v) -> {
					switch (v.getId()) {
						case R.string.take_picture:
							this.presenter.showCamera();
							Log.i("TESTE", "take pic");
							break;
						case R.string.search_gallery:
							this.presenter.showGallery();
							Log.i("TESTE", "take gallery");
							break;
					}
				}, R.string.take_picture, R.string.search_gallery)
				.build();
		customDialog.show();
	}

	@OnClick(R.id.register_button_jump)
	public void onButtonJumpClick() {
		this.presenter.jumpRegistration();
	}

	@Override
	public void onImageCropped(Uri uri) {
		if (this.getContext() != null) {
			ContentResolver resolver = this.getContext().getContentResolver();
			if (resolver != null) {
				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
					this.imageViewCropped.setImageBitmap(bitmap);
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("TESTE", e.getMessage(), e);
				}
			}
		}
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_register_photo;
	}
}
