package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.component.CameraPreview;
import com.inteligenciadigital.instagramremake.common.component.MediaHelper;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraFragment extends AbstractFragment {

	@BindView(R.id.camera_progress)
	ProgressBar progressBar;

	@BindView(R.id.camera_surface)
	FrameLayout frameLayout;

	@BindView(R.id.container_preview)
	LinearLayout linearLayout;

	@BindView(R.id.camera_image_view_picture)
	Button button;

	private MediaHelper mediaHelper;
	private Camera camera;
	private AddView addView;

	public CameraFragment() {
	}

	public static CameraFragment newInstance(AddView addView) {
		CameraFragment cameraFragment = new CameraFragment();
		cameraFragment.setAddView(addView);
		return cameraFragment;
	}

	private void setAddView(AddView addView) {
		this.addView = addView;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

		if (this.getContext() != null ) {
			this.mediaHelper = MediaHelper.getInstance(this);
			if (this.mediaHelper.checkCameraHardware(this.getContext())) {
				this.camera = this.mediaHelper.getCameraInstance(this, this.getContext());
				if (this.camera != null) {
					CameraPreview cameraPreview = new CameraPreview(this.getContext(), this.camera);
					this.frameLayout.addView(cameraPreview);
				}
			}
		}

		return view;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		this.addView.dispose();
	}

	@OnClick(R.id.camera_image_view_picture)
	public void onCameraButtonClick() {
		this.progressBar.setVisibility(View.VISIBLE);
		this.button.setVisibility(View.GONE);
		this.camera.takePicture(null, null, (data, camera) -> {
			this.progressBar.setVisibility(View.GONE);
			this.button.setVisibility(View.VISIBLE);
//erro
			Uri uri = this.mediaHelper.saveCameraFile(data);
			if (uri != null)
				this.addView.onImageLoaded(uri);
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (this.camera != null)
			this.camera.release();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_main_camera;
	}
}
