package com.inteligenciadigital.instagramremake.main.camera.presentation;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inteligenciadigital.instagramremake.R;
import com.inteligenciadigital.instagramremake.common.view.AbstractFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GalleryFragment extends AbstractFragment<GalleryPresenter> implements GalleryView {

	@BindView(R.id.main_gallery_scroll_view)
	NestedScrollView nestedScrollView;

	@BindView(R.id.main_gallery_top)
	ImageView imageView;

	@BindView(R.id.main_gallery_recycler_view)
	RecyclerView recyclerView;

	private PictureAdapter pictureAdapter;

	private AddView addView;
	private Uri uri;

	public static GalleryFragment newInstance(AddView addView, GalleryPresenter galleryPresenter) {
		GalleryFragment galleryFragment = new GalleryFragment();
		galleryPresenter.setView(galleryFragment);
		galleryFragment.setPresenter(galleryPresenter);
		galleryFragment.addView(addView);
		return galleryFragment;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		this.pictureAdapter = new PictureAdapter();
		this.recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
		this.recyclerView.setAdapter(this.pictureAdapter);

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

//		requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

		this.presenter.findPictures(this.getContext());
	}

	private void addView(AddView addView) {
		this.addView = addView;
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_main_gallery;
	}

	@Override
	public void onPicturesLoaded(List<Uri> uris) {
		if (uris.size() > 0) {
			this.imageView.setImageURI(uris.get(0));
			this.uri = uris.get(0);
		}
		this.pictureAdapter.setPictures(uris, uri1 -> {
			this.uri = uri1;
			this.imageView.setImageURI(uri1);
			this.nestedScrollView.smoothScrollTo(0, 0);
		});
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_gallery, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.action_go) {
			this.addView.onImageLoaded(this.uri);
		}
		return super.onOptionsItemSelected(item);
	}

	private static class PostViewHolder extends RecyclerView.ViewHolder {

		private final ImageView imagePost;

		public PostViewHolder(@NonNull View itemView) {
			super(itemView);
			this.imagePost = itemView.findViewById(R.id.profile_image_grid);
		}

		public void bind(Uri uri) {
			this.imagePost.setImageURI(uri);
		}
	}

	private class PictureAdapter extends RecyclerView.Adapter<PostViewHolder> {

		private List<Uri> uris = new ArrayList<>();
		private GalleryItemClickListener listener;

		@NonNull
		@Override
		public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = getLayoutInflater().inflate(R.layout.item_profile_grid, parent, false);
			return new PostViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {
			postViewHolder.bind(this.uris.get(position));
			postViewHolder.imagePost.setOnClickListener(v -> {
				Uri uri = this.uris.get(position);
				this.listener.onClick(uri);
			});
		}

		@Override
		public int getItemCount() {
			return this.uris.size();
		}

		public void setPictures(List<Uri> uris, GalleryItemClickListener listener) {
			this.uris = uris;
			this.listener = listener;
		}
	}

	interface GalleryItemClickListener {
		void onClick(Uri uri);
	}
}
