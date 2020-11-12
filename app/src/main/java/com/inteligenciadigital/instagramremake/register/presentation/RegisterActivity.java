package com.inteligenciadigital.instagramremake.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;
import com.inteligenciadigital.instagramremake.main.presentation.MainActivity;
import com.inteligenciadigital.instagramremake.register.datasource.RegisterDataSource;
import com.inteligenciadigital.instagramremake.register.datasource.RegisterLocalDataSource;

import butterknife.BindView;

public class RegisterActivity extends AbstractActivity implements RegisterView {

	@BindView(R.id.register_scroll_view)
	ScrollView scrollView;

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
		RegisterDataSource dataSource = new RegisterLocalDataSource();
		this.presenter = new RegisterPresenter(dataSource);
		this.presenter.setRegisterView(this);

		this.showNextView(RegisterSteps.EMAIL);
	}

	@Override
	public void showNextView(RegisterSteps steps) {
		Fragment fragment = null;

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.scrollView.getLayoutParams();

		switch (steps) {
			case EMAIL:
				layoutParams.gravity = Gravity.BOTTOM;
				fragment = RegisterEmailFragment.newInstance(this.presenter);
				break;
			case NAME_PASSWORD:
				layoutParams.gravity = Gravity.BOTTOM;
				fragment = RegisterNamePasswordFragment.newInstance(this.presenter);
				break;
			case WELCOME:
				layoutParams.gravity = Gravity.BOTTOM;
				fragment = RegisterWelcomeFragment.newInstance(this.presenter);
				break;
			case PHOTO:
				layoutParams.gravity = Gravity.TOP;
				fragment = RegisterPhotoFragment.newInstance(this.presenter);
				break;
		}
		this.scrollView.setLayoutParams(layoutParams);

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
	public void onUserCreate() {
		MainActivity.launch(this);
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