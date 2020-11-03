package com.inteligenciadigital.instagramremake.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.inteligenciadigital.instagramremake.common.util.Drawables;

import butterknife.ButterKnife;

public abstract class AbstractActivity extends AppCompatActivity implements ViewProgressBar {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(this.getLayout());
		ButterKnife.bind(this);
		this.onInject();
	}

	protected abstract @LayoutRes int getLayout();

	@Override
	public void showProgressBar() {}

	@Override
	public void hideProgressBar() {}

	public Drawable findDrawable(@DrawableRes int drawableId) {
		return Drawables.getDrawable(this, drawableId);
	}

	@Override
	public Context getContext() {
		return this.getBaseContext();
	}

	protected abstract void onInject();
}
