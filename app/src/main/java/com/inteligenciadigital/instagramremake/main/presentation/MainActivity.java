package com.inteligenciadigital.instagramremake.main.presentation;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.view.AbstractActivity;
import com.inteligenciadigital.instagramremake.main.camera.presentation.CameraFragment;
import com.inteligenciadigital.instagramremake.main.home.datasource.HomeDataSource;
import com.inteligenciadigital.instagramremake.main.home.datasource.HomeLocalDataSource;
import com.inteligenciadigital.instagramremake.main.home.presentation.HomeFragment;
import com.inteligenciadigital.instagramremake.main.home.presentation.HomePresenter;
import com.inteligenciadigital.instagramremake.main.profile.datasource.ProfileDataSource;
import com.inteligenciadigital.instagramremake.main.profile.datasource.ProfileLocaDataSource;
import com.inteligenciadigital.instagramremake.main.profile.presentation.ProfileFragment;
import com.inteligenciadigital.instagramremake.main.profile.presentation.ProfilePresenter;
import com.inteligenciadigital.instagramremake.main.search.presentation.SearchFragment;

public class MainActivity extends AbstractActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener {

	public static final int LOGIN_ACTIVITY = 10;
	public static final int REGISTER_ACTIVITY = 11;
    private static final String ACT_SOURCE = "ACT_SOURCE";

    private Fragment homeFragment;
    private Fragment profileFragment;
    private Fragment cameraFragment;
    private Fragment searchFragment;
    private Fragment active;

    private HomePresenter homePresenter;
    private ProfilePresenter profilePresenter;

    public MainActivity() {
    }

    public static void launch(Context context, int source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACT_SOURCE, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = this.findViewById(R.id.main_toolbar);
        this.setSupportActionBar(toolbar);
        this.scrollToolbarEnabled(false);

        if (this.getSupportActionBar() != null) {
            Drawable drawable = this.getResources().getDrawable(R.drawable.ic_insta_camera);
            this.getSupportActionBar().setHomeAsUpIndicator(drawable);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onInject() {
        HomeDataSource homeDataSource = new HomeLocalDataSource();
        ProfileDataSource profileDataSource = new ProfileLocaDataSource();

        this.profilePresenter = new ProfilePresenter(profileDataSource);
        this.homePresenter = new HomePresenter(homeDataSource);

        this.homeFragment = HomeFragment.newInstance(this, this.homePresenter);
        this.profileFragment = ProfileFragment.newInstance(this, this.profilePresenter);
        this.cameraFragment = new CameraFragment();
        this.searchFragment = new SearchFragment();
        
        this.active = this.homeFragment;

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_fragment, this.profileFragment)
                .hide(this.profileFragment).commit();

        fragmentManager.beginTransaction().add(R.id.main_fragment, this.cameraFragment)
                .hide(this.cameraFragment).commit();

        fragmentManager.beginTransaction().add(R.id.main_fragment, this.searchFragment)
                .hide(this.searchFragment).commit();

        fragmentManager.beginTransaction().add(R.id.main_fragment, this.homeFragment)
                .show(this.homeFragment).commit();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BottomNavigationView navigationView = this.findViewById(R.id.main_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY) {
                this.getSupportFragmentManager().beginTransaction().hide(active).show(this.profileFragment).commit();
                this.active = this.profileFragment;
                this.scrollToolbarEnabled(true);
                profilePresenter.findUser();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.menu_bottom_home:
                fragmentManager.beginTransaction().hide(active).show(this.homeFragment).commit();
                this.active = this.homeFragment;
                this.homePresenter.findFeed();
                this.scrollToolbarEnabled(false);
                return true;
            case R.id.menu_bottom_search:
                fragmentManager.beginTransaction().hide(active).show(this.searchFragment).commit();
	            this.active = this.searchFragment;
	            return true;
	        case R.id.menu_bottom_add:
		        fragmentManager.beginTransaction().hide(active).show(this.cameraFragment).commit();
		        this.active = this.cameraFragment;
		        return true;
	        case R.id.menu_bottom_profile:
		        fragmentManager.beginTransaction().hide(active).show(this.profileFragment).commit();
		        this.active = this.profileFragment;
		        this.profilePresenter.findUser();
                this.scrollToolbarEnabled(true);
		        return true;
        }
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void scrollToolbarEnabled(boolean enabled) {
        Toolbar toolbar = this.findViewById(R.id.main_toolbar);
        AppBarLayout appBarLayout = this.findViewById(R.id.main_appbar);

        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

        if (enabled) {
            layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        } else {
            layoutParams.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
        }
        appBarLayout.setLayoutParams(appBarLayoutParams);
    }

    @Override
    public void showProgressBar() {
        this.findViewById(R.id.main_progress).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.findViewById(R.id.main_progress).setVisibility(View.GONE);
    }
}