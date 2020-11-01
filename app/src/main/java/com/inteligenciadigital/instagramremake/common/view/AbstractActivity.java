package com.inteligenciadigital.instagramremake.common.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.inteligenciadigital.instagramremake.common.util.Drawables;

import butterknife.ButterKnife;

public abstract class AbstractActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(this.getLayout());
		ButterKnife.bind(this);
	}

	protected abstract @LayoutRes int getLayout();

	public Drawable findDrawable(@DrawableRes int drawableId) {
		return Drawables.getDrawable(this, drawableId);
	}
}
