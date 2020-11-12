package com.inteligenciadigital.instagramremake.register.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.common.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterWelcomeFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.WelcomeView {

	@BindView(R.id.register_button_next)
	LoadingButton buttonNext;
	@BindView(R.id.register_text_view_welcome)
	TextView textViewWelcome;

	public RegisterWelcomeFragment() {
	}

	public static RegisterWelcomeFragment newInstance(RegisterPresenter presenter) {
		RegisterWelcomeFragment fragment = new RegisterWelcomeFragment();
		fragment.setPresenter(presenter);
		return fragment;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.buttonNext.setEnabled(true);
		this.textViewWelcome.setText(this.getString(R.string.welcome_to_instagram, presenter.getName()));
	}

	@OnClick(R.id.register_button_next)
	public void onButtonNextClick() {
		this.presenter.showPhotoView();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_register_welcome;
	}
}
