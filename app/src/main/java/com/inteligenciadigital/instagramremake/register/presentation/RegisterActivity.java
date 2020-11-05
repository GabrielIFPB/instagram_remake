package com.inteligenciadigital.instagramremake.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView {

	private RegisterPresenter presenter;

	public static void launch(Context context) {
		Intent intent = new Intent(context, RegisterActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setStatusBarDark();
	}

	@Override
	protected void onInject() {
		this.presenter = new RegisterPresenter();
		this.presenter.setRegisterView(this);

		this.showNextView(RegisterSteps.EMAIL);
	}

	@Override
	public void showNextView(RegisterSteps steps) {
		Fragment fragment = RegisterEmailFragment.newInstance(this.presenter);
		switch (steps) {
			case EMAIL:
				break;
			case NAME_PASSWORD:
				fragment = RegisterNamePasswordFragment.newInstance(this.presenter);
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
	protected int getLayout() {
		return R.layout.activity_register;
	}

//	@OnClick(R.id.register_text_view_email_login)
//	public void onTextViewRegisterClick() {
//		RegisterActivity.launch(this);
//	}
}