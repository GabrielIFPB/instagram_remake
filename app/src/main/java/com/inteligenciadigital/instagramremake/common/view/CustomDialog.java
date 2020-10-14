package com.inteligenciadigital.instagramremake.common.view;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.inteligenciadigital.instagramremake.R;

public class CustomDialog extends Dialog {

	private LinearLayout dialogLinearLayout;
	private LinearLayout.LayoutParams layoutParams;

	private TextView titleView;
	private TextView[] textViews;
	private int titleId;

	public CustomDialog(@NonNull Context context) {
		super(context);
	}

	public CustomDialog(@NonNull Context context, int themeResId) {
		super(context, themeResId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_custom);

		this.layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		this.titleView = this.findViewById(R.id.dialog_title);
		this.dialogLinearLayout = this.findViewById(R.id.dialog_container);
		layoutParams.setMargins(30, 50, 30, 50);
	}

	@Override
	public void setTitle(int titleId) {
		this.titleId = titleId;
	}

	@Override
	public void show() {
		super.show();

		this.titleView.setText(this.titleId);

		for (TextView textView: this.textViews)
			this.dialogLinearLayout.addView(textView, this.layoutParams);
	}

	private void addButton(final View.OnClickListener listener, @StringRes int... texts) {
		this.textViews = new TextView[texts.length];

		for (int i = 0; i < texts.length; i++) {
			TextView textView = new TextView(new ContextThemeWrapper(this.getContext(), R.style.InstaTextViewBaseDialog), null, 0);
			textView.setId(texts[i]);
			textView.setText(texts[i]);
			textView.setOnClickListener((v) -> {
				listener.onClick(v);
				dismiss();
			});
			this.textViews[i] = textView;
		}
	}

	public static class Builder {

		private final Context context;
		private int titleId;
		private View.OnClickListener listener;
		private int[] texts;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setTitle(@StringRes int titleId) {
			this.titleId = titleId;
			return this;
		}

		public Builder addButton(View.OnClickListener listener, @StringRes int... texts) {
			this.listener = listener;
			this.texts = texts;
			return this;
		}

		public CustomDialog build() {
			CustomDialog customDialog = new CustomDialog(context);
			customDialog.setTitle(this.titleId);
			customDialog.addButton(this.listener, this.texts);
			return customDialog;
		}
	}
}
