package com.inteligenciadigital.instagramremake.register.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.common.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterEmailFragment extends AbstractFragment implements RegisterView.EmailView {

	@BindView(R.id.register_edit_text_email_input)
	TextInputLayout inputLayoutEmail;
	@BindView(R.id.register_edit_text_email)
	EditText editTextEmail;
	@BindView(R.id.register_button_next)
	LoadingButton buttonNext;

	public RegisterEmailFragment() {
	}

	@OnClick(R.id.register_text_view_email_login)
	public void onTextViewLoginClick() {
		if (this.isAdded() && this.getActivity() != null) {
			this.getActivity().finish();
		}
	}

	@OnClick(R.id.register_button_next)
	public void onButtonNextClick() {

	}

	@OnTextChanged(R.id.register_edit_text_email)
	public void onTextChanged(CharSequence s) {
		String email = this.editTextEmail.getText().toString();
		this.buttonNext.setEnabled(!email.isEmpty());
		this.editTextEmail.setBackground(this.findDrawable(R.drawable.edit_text_background));
		this.inputLayoutEmail.setError(null);
		this.inputLayoutEmail.setErrorEnabled(false);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_register_email;
	}

	@Override
	public void onFailureForm(String emailError) {

	}
}
