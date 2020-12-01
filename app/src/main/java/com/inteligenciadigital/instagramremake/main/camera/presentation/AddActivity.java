package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;

import butterknife.BindView;

public class AddActivity extends AbstractActivity implements AddView {

	@BindView(R.id.add_viewpager)
	ViewPager viewPager;

	@BindView(R.id.add_tab_layout)
	TabLayout tabLayout;

	public static void launch(Context context) {
		Intent intent = new Intent(context, AddActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toolbar toolbar = this.findViewById(R.id.toolbar);
		this.setSupportActionBar(toolbar);

		if (this.getSupportActionBar() != null) {
			Drawable drawable = this.findDrawable(R.drawable.ic_close);
			this.getSupportActionBar().setHomeAsUpIndicator(drawable);
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
			this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}

		this.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewPager) {
			@Override
			public void onTabSelected(@NonNull TabLayout.Tab tab) {
				super.onTabSelected(tab);
				viewPager.setCurrentItem(tab.getPosition());
				Log.d("TESTE", "" + tab.getPosition());
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onInject() {
		ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
		this.viewPager.setAdapter(adapter);

		GalleryFragment galleryFragment = new GalleryFragment();//.newInstance(this);
		adapter.add(galleryFragment);

		CameraFragment cameraFragment = CameraFragment.newInstance(this);
		adapter.add(cameraFragment);

		adapter.notifyDataSetChanged();

		this.tabLayout.setupWithViewPager(this.viewPager);

		TabLayout.Tab tabLeft = this.tabLayout.getTabAt(0);
		if (tabLeft != null)
			tabLeft.setText(getString(R.string.gallery));

		TabLayout.Tab tabRight = this.tabLayout.getTabAt(1);
		if (tabRight != null)
			tabRight.setText(getString(R.string.photo));

		this.viewPager.setCurrentItem(adapter.getCount() - 1);
	}

	@Override
	public void onImageLoaded(Uri uri) {
		AddCaptionActivity.launch(this, uri);
		this.finish();
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_add;
	}
}