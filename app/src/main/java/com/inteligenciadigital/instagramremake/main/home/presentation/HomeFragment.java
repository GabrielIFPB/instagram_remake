package com.inteligenciadigital.instagramremake.main.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.main.presentation.MainView;
import com.inteligenciadigital.instagramremake.main.profile.presentation.ProfileFragment;

public class HomeFragment extends Fragment {

	private MainView mainView;

	public HomeFragment() {
	}

	public static HomeFragment newInstance(MainView mainView) {
		HomeFragment homeFragment = new HomeFragment();
		homeFragment.setMainView(mainView);
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
		// TODO: 18/10/2020 app:layout_scrollFlags="scroll" at toolbar
		View view = inflater.inflate(R.layout.fragment_main_home, container, false);

		RecyclerView recyclerView = view.findViewById(R.id.home_recycler);

		recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
		recyclerView.setAdapter(new PostAdapter());

		return view;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_profile, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	private class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

		private final int[] images = new int[]{
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
				R.drawable.ic_insta_add,
		};

		@NonNull
		@Override
		public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_post_list, parent, false));
		}

		@Override
		public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
			holder.bind(this.images[position]);
		}

		@Override
		public int getItemCount() {
			return this.images.length;
		}
	}

	private static class PostViewHolder extends RecyclerView.ViewHolder {

		private final ImageView imagePost;

		public PostViewHolder(@NonNull View itemView) {
			super(itemView);
			this.imagePost = itemView.findViewById(R.id.profile_image_grid);
		}

		public void bind(int image) {
			this.imagePost.setImageResource(image);
		}
	}
}
