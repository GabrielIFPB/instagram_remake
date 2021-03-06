package com.inteligenciadigital.instagramremake.main.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.component.CustomDialog;
import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.main.presentation.MainView;
import com.inteligenciadigital.instagramremake.main.profile.presentation.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends AbstractFragment<HomePresenter> implements MainView.HomeView {

	private MainView mainView;
	private FeedAdapter feedAdapter;

	@BindView(R.id.home_recycler)
	RecyclerView recyclerView;

	public HomeFragment() {
	}

	public static HomeFragment newInstance(MainView mainView, HomePresenter homePresenter) {
		HomeFragment homeFragment = new HomeFragment();
		homeFragment.setMainView(mainView);
		homeFragment.setPresenter(homePresenter);
		homePresenter.setView(homeFragment);
		return homeFragment;
	}

	private void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);
		this.feedAdapter = new FeedAdapter();

		this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
		this.recyclerView.setAdapter(this.feedAdapter);

		return view;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settongs:
				CustomDialog customDialog = new CustomDialog.Builder(this.getContext())
						.setTitle(R.string.logout)
						.addButton((v) -> {
							switch (v.getId()) {
								case R.string.logout_action:
									FirebaseAuth.getInstance().signOut();
									mainView.logout();
									break;
								case R.string.cancel:
									break;
							}
						}, R.string.logout_action, R.string.cancel).build();
				customDialog.show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		super.onResume();
		this.presenter.findFeed();
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
		return R.layout.fragment_main_home;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_profile, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void showFeed(List<Feed> feeds) {
		this.feedAdapter.setFeed(feeds);
		this.feedAdapter.notifyDataSetChanged();
	}

	private class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

		private List<Feed> feeds = new ArrayList<>();

		@NonNull
		@Override
		public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new FeedViewHolder(getLayoutInflater().inflate(R.layout.item_post_list, parent, false));
		}

		@Override
		public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
			holder.bind(this.feeds.get(position));
		}

		@Override
		public int getItemCount() {
			return this.feeds.size();
		}

		public void setFeed(List<Feed> feeds) {
			this.feeds = feeds;
		}
	}

	private static class FeedViewHolder extends RecyclerView.ViewHolder {

		private final ImageView imagePost;
		private final ImageView imageUser;
		private final TextView textViewCaption;
		private final TextView textViewUsername;

		public FeedViewHolder(@NonNull View itemView) {
			super(itemView);
			this.imagePost = itemView.findViewById(R.id.profile_image_list);
			this.imageUser = itemView.findViewById(R.id.home_container_user_photo);
			this.textViewCaption = itemView.findViewById(R.id.home_container_user_caption);
			this.textViewUsername = itemView.findViewById(R.id.home_container_user_username);
		}

		public void bind(Feed feed) {
			Glide.with(this.itemView.getContext()).load(feed.getPhotoUrl()).into(this.imagePost);
//			this.imagePost.setImageURI(feed.getUri());
			this.textViewCaption.setText(feed.getCaption());

			User user = feed.getPublisher();
			if (user != null) {
				Glide.with(this.itemView.getContext()).load(user.getPhotoUrl()).into(this.imageUser);
//				this.imageUser.setImageURI(user.getUri());
				this.textViewUsername.setText(user.getName());
			}
		}
	}
}
