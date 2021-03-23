package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;
import com.inteligenciadigital.instagramremake.main.camera.datasource.AddDataSource;
import com.inteligenciadigital.instagramremake.main.camera.datasource.AddFireDataSource;
import com.inteligenciadigital.instagramremake.main.camera.datasource.AddLocalDataSource;

import butterknife.BindView;

public class AddCaptionActivity extends AbstractActivity implements AddCaptionView {

	@BindView(R.id.main_add_caption_image_view)
	ImageView imageView;

	@BindView(R.id.main_add_caption_edit_text)
	EditText editText;

	@BindView(R.id.add_progress)
	ProgressBar progressBar;

	private Uri uri;
	private AddPresenter presesnter;

	public static void launch(Context context, Uri uri) {
		Intent intent = new Intent(context, AddCaptionActivity.class);
		intent.putExtra("uri", uri);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toolbar toolbar = this.findViewById(R.id.toolbar);
		this.setSupportActionBar(toolbar);

		if (this.getSupportActionBar() != null) {
			Drawable drawable = this.findDrawable(R.drawable.ic_arrow_back);
			this.getSupportActionBar().setHomeAsUpIndicator(drawable);
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
			this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}
	}

	@Override
	protected void onInject() {
		uri = this.getIntent().getExtras().getParcelable("uri");
		this.imageView.setImageURI(this.uri);

		AddDataSource dataSource = new AddFireDataSource();
		this.presesnter = new AddPresenter(this, dataSource);
	}

	@Override
	public void showProgressBar() {
		this.progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgressBar() {
		this.progressBar.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.menu_share, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
			case R.id.action_share:
				this.presesnter.createPost(this.uri, this.editText.getText().toString());
//				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_add_caption;
	}

	@Override
	public void postSaved() {
		this.finish();
	}
}