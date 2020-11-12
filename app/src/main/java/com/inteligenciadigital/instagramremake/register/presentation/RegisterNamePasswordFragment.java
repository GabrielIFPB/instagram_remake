package com.inteligenciadigital.instagramremake.register.presentation;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.common.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterNamePasswordFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.NamePasswordView {

	@BindView(R.id.register_edit_text_name)
	EditText editTextName;
	@BindView(R.id.register_edit_text_name_input)
	TextInputLayout inputLayoutName;
	@BindView(R.id.register_edit_text_password)
	EditText editTextPassword;
	@BindView(R.id.register_edit_text_password_input)
	TextInputLayout inputLayoutPassword;
	@BindView(R.id.register_edit_text_password_confirm)
	EditText editTextPasswordConfirm;
	@BindView(R.id.register_edit_text_password_confirm_input)
	TextInputLayout inputLayoutPasswordConfirm;

	@BindView(R.id.register_button_name_next)
	LoadingButton buttonNext;

	public RegisterNamePasswordFragment() {
	}

	public static RegisterNamePasswordFragment newInstance(RegisterPresenter presenter) {
		RegisterNamePasswordFragment fragment = new RegisterNamePasswordFragment();
		fragment.setPresenter(presenter);
		presenter.setNamePasswordView(fragment);
		return fragment;
	}

	@Override
	public void showProgressBar() {
		this.buttonNext.showProgress(true);
	}

	@Override
	public void hideProgressBar() {
		this.buttonNext.showProgress(false);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_register_name_password;
	}

	@Override
	public void onFailureForm(String nameError, String passwordError) {
		if (nameError != null) {
			this.inputLayoutName.setError(nameError);
			this.editTextName.setBackground(this.findDrawable(R.drawable.edit_text_background_error));
		}
		if (passwordError != null) {
			this.inputLayoutPassword.setError(passwordError);
			this.editTextPassword.setBackground(this.findDrawable(R.drawable.edit_text_background_error));
		}
	}

	@Override
	public void onFailureCreateUser(String message) {
		Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
	}

	@OnClick(R.id.register_text_view_login)
	public void onTextViewLoginClick() {
		if (this.isAdded() && this.getActivity() != null)
			this.getActivity().finish();
	}

	@OnClick(R.id.register_button_name_next)
	public void onButtonNextClick() {
		String name = this.editTextName.getText().toString();
		String password = this.editTextPassword.getText().toString();
		String confirmPassword = this.editTextPasswordConfirm.getText().toString();
		this.presenter.setNameAndPassword(name, password, confirmPassword);
	}

	@OnTextChanged({
			R.id.register_edit_text_name,
			R.id.register_edit_text_password,
			R.id.register_edit_text_password_confirm
	})
	public void onTextChanged(CharSequence s) {
		String name = this.editTextName.getText().toString();
		String password = this.editTextPassword.getText().toString();
		String confirmPassword = this.editTextPasswordConfirm.getText().toString();
		boolean isValid = !name.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty();
		this.buttonNext.setEnabled(isValid);

		this.editTextName.setBackground(this.findDrawable(R.drawable.edit_text_background));
		this.inputLayoutName.setError(null);
		this.inputLayoutName.setErrorEnabled(false);

		this.editTextPassword.setBackground(this.findDrawable(R.drawable.edit_text_background));
		this.inputLayoutPassword.setError(null);
		this.inputLayoutPassword.setErrorEnabled(false);

		this.editTextPasswordConfirm.setBackground(this.findDrawable(R.drawable.edit_text_background));
		this.inputLayoutPasswordConfirm.setError(null);
		this.inputLayoutPasswordConfirm.setErrorEnabled(false);
	}
}
