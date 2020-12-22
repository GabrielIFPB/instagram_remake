package com.inteligenciadigital.instagramremake.register.presentation;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;
import com.inteligenciadigital.instagramremake.common.util.Strings;
import com.inteligenciadigital.instagramremake.register.datasource.RegisterDataSource;

public class RegisterPresenter implements Presenter<FirebaseUser> {

	private RegisterView registerView;
	private RegisterView.EmailView emailView;
	private RegisterView.NamePasswordView namePasswordView;
	private RegisterView.PhotoView photoView;

	private final RegisterDataSource dataSource;

	private String name;
	private String email;
	private Uri uri;


	public RegisterPresenter(RegisterDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegisterView(RegisterView registerView) {
		this.registerView = registerView;
	}

	public void setEmailView(RegisterView.EmailView emailView) {
		this.emailView = emailView;
	}

	public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView) {
		this.namePasswordView = namePasswordView;
	}

	public void setPhotoView(RegisterView.PhotoView photoView) {
		this.photoView = photoView;
	}

	public void setEmail(String email) {
		if (!Strings.emailValid(email)) {
			String invalid_email = this.emailView.getContext().getString(R.string.invalid_email);
			this.emailView.onFailureForm(invalid_email);
			return;
		}
		this.email = email;
		this.emailView.showProgressBar();
		this.registerView.showNextView(RegisterSteps.NAME_PASSWORD);
	}

	public void setNameAndPassword(String name, String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			String password_not_equal = this.namePasswordView.getContext().getString(R.string.password_not_equal);
			this.namePasswordView.onFailureForm(null, password_not_equal);
		}
		this.name = name;

		this.namePasswordView.showProgressBar();
		this.dataSource.createUser(name.toLowerCase(), this.email, password, this);
	}

	public void setUri(Uri uri) {
		this.uri = uri;
		if (this.photoView != null) {
			this.photoView.onImageCropped(this.uri);
			this.photoView.showProgressBar();

			dataSource.addPhoto(uri, new UpdatePhotoCallback());
		}
	}

	public void showPhotoView() {
		this.registerView.showNextView(RegisterSteps.PHOTO);
	}

	public void showCamera() {
		this.registerView.showCamera();
	}

	public void showGallery() {
		this.registerView.showGallery();
	}

	public void jumpRegistration() {
		this.registerView.onUserCreate();
	}

	@Override
	public void onSuccess(FirebaseUser userAuth) {
		this.registerView.showNextView(RegisterSteps.WELCOME);
	}

	@Override
	public void onError(String message) {
		this.namePasswordView.onFailureCreateUser(message);
	}

	@Override
	public void onComplete() {
		this.namePasswordView.hideProgressBar();
	}

	private class UpdatePhotoCallback implements Presenter<Boolean> {

		@Override
		public void onSuccess(Boolean userAuth) {
			registerView.onUserCreate();
		}

		@Override
		public void onError(String message) {

		}

		@Override
		public void onComplete() {

		}
	}
}
