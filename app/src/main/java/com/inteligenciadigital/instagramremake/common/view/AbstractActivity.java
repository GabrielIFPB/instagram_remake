package com.inteligenciadigital.instagramremake.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.util.Colors;
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

	@Override
	public void setStatusBarDark() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window windows = this.getWindow();
			windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			windows.setStatusBarColor(this.findColor(R.color.colorAccent));
		}
	}

	public Drawable findDrawable(@DrawableRes int drawableId) {
		return Drawables.getDrawable(this, drawableId);
	}

	public int findColor(@ColorRes int colorId) {
		return Colors.getColor(this, colorId);
	}

	@Override
	public Context getContext() {
		return this.getBaseContext();
	}

	protected abstract void onInject();
}
