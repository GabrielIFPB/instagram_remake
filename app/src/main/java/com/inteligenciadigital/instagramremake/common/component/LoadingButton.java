package com.inteligenciadigital.instagramremake.common.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.inteligenciadigital.instagramremake.R;

public class LoadingButton extends FrameLayout {

	private AppCompatButton button;
	private ProgressBar progressBar;
	private String text;

	public LoadingButton(@NonNull Context context) {
		super(context);
		this.setup(context, null);
	}

	public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		this.setup(context, attrs);
	}

	public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.setup(context, attrs);
	}

	private void setup(Context context, AttributeSet attrs) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.button_loading, this, true);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0);

		this.text = typedArray.getString(R.styleable.LoadingButton_text);

		typedArray.recycle();

		this.button = (AppCompatButton) this.getChildAt(0);
		this.button.setText(text);
		this.button.setEnabled(false);

		this.progressBar = (ProgressBar) this.getChildAt(1);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			Drawable wrap = DrawableCompat.wrap(this.progressBar.getIndeterminateDrawable());
			DrawableCompat.setTint(wrap, ContextCompat.getColor(context, android.R.color.white));
			this.progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrap));
		} else {
			this.progressBar.getIndeterminateDrawable().setColorFilter(
					ContextCompat.getColor(context, android.R.color.white), PorterDuff.Mode.SRC_IN
			);
		}
	}

	@Override
	public void setOnClickListener(@Nullable OnClickListener l) {
		this.button.setOnClickListener(l);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.button.setEnabled(enabled);
	}

	public void showProgress(boolean enabled) {
		this.progressBar.setVisibility(enabled ? VISIBLE : GONE);
		this.button.setText(enabled ? "" : this.text);
	}
}
