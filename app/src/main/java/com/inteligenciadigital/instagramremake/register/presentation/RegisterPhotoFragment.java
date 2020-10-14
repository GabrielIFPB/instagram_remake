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
import com.inteligenciadigital.instagramremake.common.view.CustomDialog;

public class RegisterPhotoFragment extends Fragment {

	public RegisterPhotoFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_register_photo, container, false);
		// TODO: 13/10/2020 scroll gravity top
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		CustomDialog customDialog = new CustomDialog.Builder(this.getContext())
				.setTitle(R.string.photo_register_title)
				.addButton((v) -> {
					switch (v.getId()) {
						case R.string.take_picture:
							Log.i("TESTE", "take pic");
							break;
						case R.string.search_gallery:
							Log.i("TESTE", "take gallery");
							break;
					}
				}, R.string.take_picture, R.string.search_gallery)
				.build();
		customDialog.show();
	}
}
