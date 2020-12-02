package com.inteligenciadigital.instagramremake.common.component;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private final Camera camera;
	private final SurfaceHolder holder;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		this.camera = camera;
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(@NonNull SurfaceHolder holder) {
		try {
			this.camera.setPreviewDisplay(holder);
			this.camera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
		if (holder.getSurface() == null) return;

		try {
			this.camera.stopPreview();
			this.camera.setPreviewDisplay(holder);
			this.camera.setDisplayOrientation(90);

			Camera.Parameters parameters = this.camera.getParameters();
			int w = width;
			int h = height;

			List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPictureSizes();

			for (Camera.Size size: supportedPreviewSizes) {
				w = size.width;
				h = size.height;
				break;
			}

			 parameters.setPictureSize(w, h);
			 parameters.setPictureSize(w, h);
			 this.camera.setParameters(parameters);

			 this.camera.startPreview();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

	}
}
