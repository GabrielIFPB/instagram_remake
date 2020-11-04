package com.inteligenciadigital.instagramremake.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView {

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
		RegisterEmailFragment fragment = new RegisterEmailFragment();
		FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		transaction.add(R.id.register_fragment, fragment, "fragment1");
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