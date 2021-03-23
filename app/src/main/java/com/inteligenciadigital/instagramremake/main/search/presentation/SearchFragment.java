package com.inteligenciadigital.instagramremake.main.search.presentation;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;
import com.inteligenciadigital.instagramremake.main.presentation.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchFragment extends AbstractFragment<SearchPresenter> implements MainView.SearchView {

	@BindView(R.id.search_recycler)
	RecyclerView recyclerView;

	private UserAdapter userAdapter;
	private MainView mainView;

	public SearchFragment() {
	}

	public static SearchFragment newInstance(MainView mainView, SearchPresenter presenter) {
		SearchFragment searchFragment = new SearchFragment();
		presenter.setView(searchFragment);
		searchFragment.setMainView(mainView);
		searchFragment.setPresenter(presenter);
		return searchFragment;
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

		this.userAdapter = new UserAdapter();
		this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
		this.recyclerView.setAdapter(this.userAdapter);

		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_main_search;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_search, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);

		SearchManager searchManager = (SearchManager) this.getContext().getSystemService(Context.SEARCH_SERVICE);

		SearchView searchView = null;
		if (searchItem != null) {
			searchView = (SearchView) searchItem.getActionView();
		}

		if (searchView != null) {
			searchView.setSearchableInfo(
					searchManager.getSearchableInfo(((AppCompatActivity) this.getContext()).getComponentName()));
			searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> Log.i("TESTE", hasFocus + ""));
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					if (!newText.isEmpty())
						presenter.findUsers(newText);
					return false;
				}
			});

			searchItem.expandActionView();
		}
	}

	@Override
	public void showUsers(List<User> users) {
		this.userAdapter.setUser(users, user -> this.mainView.showProfile(user.getUuid()));
	}

	private static class PostViewHolder extends RecyclerView.ViewHolder {

		private final ImageView imageUser;
		private final TextView textViewUsername;
		private final TextView textViewName;

		public PostViewHolder(@NonNull View itemView) {
			super(itemView);
			this.imageUser = itemView.findViewById(R.id.main_search_imageview_user);
			this.textViewUsername = itemView.findViewById(R.id.main_search_text_view_username);
			this.textViewName = itemView.findViewById(R.id.main_search_text_view_name);
		}

		public void bind(User user) {
			Glide.with(this.itemView.getContext()).load(user.getPhotoUrl()).into(this.imageUser);
			this.textViewName.setText(user.getName());
			this.textViewUsername.setText(user.getName());
		}
	}

	private class UserAdapter extends RecyclerView.Adapter<PostViewHolder> {

		private List<User> users = new ArrayList<>();
		private ItemClickListener listener;

		@NonNull
		@Override
		public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = getLayoutInflater().inflate(R.layout.item_user_list, parent, false);
			return new PostViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
			holder.bind(this.users.get(position));
			holder.itemView.setOnClickListener(v -> {
				this.listener.onClick(this.users.get(position));
			});
		}

		@Override
		public int getItemCount() {
			return this.users.size();
		}

		public void setUser(List<User> users, ItemClickListener listener) {
			this.users = users;
			this.listener = listener;
		}
	}

	interface ItemClickListener {
		void onClick(User user);
	}
}
