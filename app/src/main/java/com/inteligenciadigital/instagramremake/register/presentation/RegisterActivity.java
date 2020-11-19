package com.inteligenciadigital.instagramremake.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.component.MediaHelper;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;
import com.inteligenciadigital.instagramremake.main.presentation.MainActivity;
import com.inteligenciadigital.instagramremake.register.datasource.RegisterDataSource;
import com.inteligenciadigital.instagramremake.register.datasource.RegisterLocalDataSource;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends AbstractActivity implements RegisterView, MediaHelper.OnImageCroppedListener {

	@BindView(R.id.register_scroll_view)
	ScrollView scrollView;

	@BindView(R.id.register_root_container)
	FrameLayout rootContainer;

	@BindView(R.id.register_crop_image_view)
	CropImageView cropImageView;

	@BindView(R.id.register_button_crop)
	Button buttonCrop;

	private RegisterPresenter presenter;
	private MediaHelper mediaHelper;

	public static void launch(Context context) {
		Intent intent = new Intent(context, RegisterActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setStatusBarDark();
		this.mediaHelper = MediaHelper.getInstance(this);
		this.mediaHelper.cropView(cropImageView);
		this.mediaHelper.listener(this);
	}

	@Override
	protected void onInject() {
		RegisterDataSource dataSource = new RegisterLocalDataSource();
		this.presenter = new RegisterPresenter(dataSource);
		this.presenter.setRegisterView(this);

		this.showNextView(RegisterSteps.EMAIL);
	}

	@Override
	public void showNextView(RegisterSteps steps) {
		Fragment fragment = null;

//		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.scrollView.getLayoutParams();

		switch (steps) {
			case EMAIL:
//				layoutParams.gravity = Gravity.BOTTOM;
				fragment = RegisterEmailFragment.newInstance(this.presenter);
				break;
			case NAME_PASSWORD:
//				layoutParams.gravity = Gravity.BOTTOM;
				fragment = RegisterNamePasswordFragment.newInstance(this.presenter);
				break;
			case WELCOME:
//				layoutParams.gravity = Gravity.BOTTOM;
				fragment = RegisterWelcomeFragment.newInstance(this.presenter);
				break;
			case PHOTO:
				FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.scrollView.getLayoutParams();
				layoutParams.gravity = Gravity.TOP;
				this.scrollView.setLayoutParams(layoutParams);
				fragment = RegisterPhotoFragment.newInstance(this.presenter);
				break;
		}

		FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		if (manager.findFragmentById(R.id.register_fragment) == null) {
			transaction.add(R.id.register_fragment, fragment, steps.name());
		} else {
			transaction.replace(R.id.register_fragment, fragment, steps.name());
			transaction.addToBackStack(steps.name());
		}

		transaction.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
		this.cropViewEnabled(true);
		MediaHelper mediaHelper = MediaHelper.getInstance(this);
		mediaHelper.onActivityResult(requestCode, resultCode, data);
	}

	private void cropViewEnabled(boolean enable) {
		this.cropImageView.setVisibility(enable ? View.VISIBLE : View.GONE);
		this.scrollView.setVisibility(enable ? View.GONE : View.VISIBLE);
		this.buttonCrop.setVisibility(enable ? View.VISIBLE : View.GONE);
		int blackId = this.findColor(android.R.color.black);
		int whiteId = this.findColor(android.R.color.white);
		this.rootContainer.setBackgroundColor(enable ? blackId : whiteId);
	}

	@Override
	public void onUserCreate() {
		MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
	}

	@Override
	public void showCamera() {
		this.mediaHelper.chooserCamera();
	}

	@Override
	public void showGallery() {
		this.mediaHelper.chooserGallery();
	}

	@OnClick(R.id.register_button_crop)
	public void onButtonCropClick() {
		this.cropViewEnabled(false);
		MediaHelper.getInstance(this).cropImage(this.cropImageView);
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_register;
	}

	@Override
	public void onImageCropped(Uri uri) {
		this.presenter.setUri(uri);
	}

	@Override
	public void onImagePicked(Uri uri) {
		this.cropImageView.setImageUriAsync(uri);
	}
}