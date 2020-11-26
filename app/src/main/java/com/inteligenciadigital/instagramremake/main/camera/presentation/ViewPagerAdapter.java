package com.inteligenciadigital.instagramremake.main.camera.presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter extends FragmentStatePagerAdapter {

	private final List<Fragment> fragments = new ArrayList<>(2);

	public ViewPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	public void add(Fragment fragment) {
		this.fragments.add(fragment);
	}
}
