package com.inteligenciadigital.instagramremake.register.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.common.view.CustomDialog;
import com.inteligenciadigital.instagramremake.common.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

	@BindView(R.id.register_button_next)
	LoadingButton buttonNext;

	public RegisterPhotoFragment() {
	}

	public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
		RegisterPhotoFragment fragment = new RegisterPhotoFragment();
		fragment.setPresenter(presenter);
		return fragment;
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_register_photo;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		this.buttonNext.setEnabled(true);

//		CustomDialog customDialog = new CustomDialog.Builder(this.getContext())
//				.setTitle(R.string.photo_register_title)
//				.addButton((v) -> {
//					switch (v.getId()) {
//						case R.string.take_picture:
//							Log.i("TESTE", "take pic");
//							break;
//						case R.string.search_gallery:
//							Log.i("TESTE", "take gallery");
//							break;
//					}
//				}, R.string.take_picture, R.string.search_gallery)
//				.build();
//		customDialog.show();
	}

	@OnClick(R.id.register_button_next)
	public void onButtonNextClick() {

	}

	@OnClick(R.id.register_button_jump)
	public void onButtonJumpClick() {
		this.presenter.jumpRegistration();
	}
}
