package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.main.home.presentation.HomeFragment;

public class CameraFragment extends Fragment {

	public CameraFragment() {
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		// TODO: 18/10/2020 app:layout_scrollFlags="scroll" at toolbar
		View view = inflater.inflate(R.layout.fragment_main_gallery, container, false);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_profile, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
