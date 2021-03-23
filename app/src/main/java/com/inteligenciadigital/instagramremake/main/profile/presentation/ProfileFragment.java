package com.inteligenciadigital.instagramremake.main.profile.presentation;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.main.presentation.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends AbstractFragment<ProfilePresenter> implements MainView.ProfileView {

	@BindView(R.id.profile_recycler)
	RecyclerView recyclerView;

	@BindView(R.id.profile_image_icon)
	CircleImageView imageViewProfile;

	@BindView(R.id.profile_text_view_username)
	TextView textViewUserName;

	@BindView(R.id.profile_text_view_following_count)
	TextView textViewFollowing;

	@BindView(R.id.profile_text_view_followers_count)
	TextView textViewFollowers;

	@BindView(R.id.profile_text_view_post_count)
	TextView textViewPost;

	@BindView(R.id.profile_navigation_tabs)
	BottomNavigationView bottomNavigationView;

	@BindView(R.id.profile_button_edit_profile)
	Button button;

	private MainView mainView;

	private PostAdapter postAdapter;

	public ProfileFragment() {
	}

	public static ProfileFragment newInstance(MainView mainView, ProfilePresenter profilePresenter) {
		ProfileFragment profileFragment = new ProfileFragment();
		profileFragment.setPresenter(profilePresenter);
		profileFragment.setMainView(mainView);
		profilePresenter.setView(profileFragment);
		return profileFragment;
	}

	private void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setHasOptionsMenu(true);
		this.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
			switch (menuItem.getItemId()) {
				case R.id.menu_profile_grid:
					this.recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
					return true;
				case R.id.menu_profile_list:
					this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
					return true;
			}
			return false;
		});
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);
		this.postAdapter = new PostAdapter();

		this.recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
		this.recyclerView.setAdapter(this.postAdapter);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		this.presenter.findUser();
	}

	@Override
	public void showProgressBar() {
		this.mainView.showProgressBar();
	}

	@Override
	public void hideProgressBar() {
		this.mainView.hideProgressBar();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_main_profile;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_profile, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (!this.presenter.getUser().equals(FirebaseAuth.getInstance().getUid()))
					this.mainView.disposeProfileDetail();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void showPhoto(String uri) {
		Glide.with(this.getContext()).load(uri).into(this.imageViewProfile);
	}

	@Override
	public void showData(String name, String following, String followers, String posts, boolean editProfile, boolean follow) {
		this.textViewUserName.setText(name);
		this.textViewFollowers.setText(followers);
		this.textViewFollowing.setText(following);
		this.textViewPost.setText(posts);

		if (editProfile) {
			this.button.setText(R.string.edit_profile);
			this.button.setTag(null);
		} else if (follow) {
			this.button.setText(R.string.unfollow);
			this.button.setTag(false);
		} else {
			this.button.setText(R.string.follow);
			this.button.setTag(true);
		}
	}

	@OnClick(R.id.profile_button_edit_profile)
	public void onButtonFollowClick() {
		Boolean follow = (Boolean) this.button.getTag();
		if (follow != null) {
			if (follow)
				this.button.setText(R.string.unfollow);
			else
				this.button.setText(R.string.follow);
			this.presenter.follow(follow);
			this.button.setTag(!follow);
		}
	}

	@Override
	public void showPosts(List<Post> posts) {
		this.postAdapter.setPosts(posts);
		this.postAdapter.notifyDataSetChanged();
	}

	private class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

		private List<Post> posts = new ArrayList<>();

		@NonNull
		@Override
		public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_profile_grid, parent, false));
		}

		@Override
		public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
			holder.bind(this.posts.get(position));
		}

		@Override
		public int getItemCount() {
			return this.posts.size();
		}

		public void setPosts(List<Post> posts) {
			this.posts = posts;
		}
	}

	private static class PostViewHolder extends RecyclerView.ViewHolder {

		private final ImageView imagePost;

		public PostViewHolder(@NonNull View itemView) {
			super(itemView);
			this.imagePost = itemView.findViewById(R.id.profile_image_grid);
		}

		public void bind(Post post) {
			Glide.with(this.itemView.getContext()).load(post.getPhotoUrl()).into(this.imagePost);
		}
	}
}