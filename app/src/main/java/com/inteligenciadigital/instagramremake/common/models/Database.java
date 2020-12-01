package com.inteligenciadigital.instagramremake.common.models;

import android.icu.text.MessagePattern;
import android.net.Uri;
import android.os.Handler;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Database {

	private static Database INSTANCE;
	private static Set<User> users;
	private static Set<Uri> storages;
	private static Set<UserAuth> usersAuth;
	private static HashMap<String, HashSet<Post>> posts;
	private static HashMap<String, HashSet<Feed>> feed;
	private static HashMap<String, HashSet<String>> followers;

	private OnSuccessListener onSuccessListener;
	private OnFailureListener onFailureListener;
	private OnCompleteListener onCompleteListener;
	private static UserAuth userAuth;

	static {
		users = new HashSet<>();
		usersAuth = new HashSet<>();
		storages = new HashSet<>();
		posts = new HashMap<>();
		feed = new HashMap<>();
		followers = new HashMap<>();

		init();
//		usersAuth.add(new UserAuth("gabriel@gmail.com", "123456"));
//		usersAuth.add(new UserAuth("joba@gmail.com", "1234"));
//		usersAuth.add(new UserAuth("juliana@gmail.com", "12345"));
//		usersAuth.add(new UserAuth("jose@gmail.com", "1234567"));
	}

	public static Database getInstance() {
		return new Database();
//		if (INSTANCE == null) {
//			INSTANCE = new Database();
//			INSTANCE.init();
//		}
//		return INSTANCE;
	}

	public static void init() {
		String email = "user1@gmail.com";
		String password = "123";
		String name = "user1";

		UserAuth userAuth = new UserAuth();
		userAuth.setEmail(email);
		userAuth.setPassword(password);

		usersAuth.add(userAuth);

		User user = new User();
		user.setUuid(userAuth.getUUID());
		user.setName(name);
		user.setEmail(email);

		users.add(user);
		Database.userAuth = userAuth;
	}

	public <T> Database addOnSuccessListener(OnSuccessListener<T> listener) {
		this.onSuccessListener = listener;
		return this;
	}

	public Database addOnFailureListener(OnFailureListener listener) {
		this.onFailureListener = listener;
		return this;
	}

	public Database addOnCompleteListener(OnCompleteListener listener) {
		this.onCompleteListener = listener;
		return this;
	}

	public Database findFeed(String uuid) {
		timeout(() -> {
			HashMap<String, HashSet<Feed>> feeds = Database.feed;
			HashSet<Feed> res = feeds.get(uuid);

			if (res == null)
				res = new HashSet<>();

			if (this.onSuccessListener != null)
				this.onSuccessListener.onSuccess(new ArrayList<>(res));

			if (this.onCompleteListener != null)
				this.onCompleteListener.onComplete();
		});
		return this;
	}

	public Database findPosts(String uuid) {
		timeout(() -> {
			HashMap<String, HashSet<Post>> posts = Database.posts;
			HashSet<Post> res = posts.get(uuid);

			if (res == null)
				res = new HashSet<>();

			if (this.onSuccessListener != null)
				this.onSuccessListener.onSuccess(new ArrayList<>(res));

			if (this.onCompleteListener != null)
				this.onCompleteListener.onComplete();
		});
		return this;
	}

	public Database findUser(String uuid) {
		timeout(() -> {
			Set<User> users = Database.users;
			User res = null;

			for (User user: users) {
				if (user.getUuid().equals(uuid)) {
					res = user;
					break;
				}
			}

			if (this.onSuccessListener != null && res != null)
				this.onSuccessListener.onSuccess(res);
			else if (this.onFailureListener != null)
				this.onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));

			if (this.onCompleteListener != null)
				this.onCompleteListener.onComplete();

		});
		return this;
	}

	public Database addPhoto(String uuid, Uri uri) {
		timeout(() ->{
			Set<User> users = Database.users;
			for (User user: users) {
				if (user.getUuid().equals(uuid)) {
					user.setUri(uri);
				}
			}
			storages.add(uri);
			this.onSuccessListener.onSuccess(true);
		});
		return this;
	}

	public Database createPost(String uuid, Uri uri, String caption) {
		timeout(() -> {
			HashMap<String, HashSet<Post>> postMap = Database.posts;

			HashSet<Post> posts = postMap.get(uuid);
			if (posts == null) {
				posts = new HashSet<>();
				postMap.put(uuid, posts);
			}

			Post post = new Post();
			post.setUri(uri);
			post.setCaption(caption);
			post.setTimestamp(System.currentTimeMillis());
			post.setUuid(String.valueOf(post.hashCode()));
			posts.add(post);

			HashMap<String, HashSet<String>> followersMap = Database.followers;

			HashSet<String> followers = followersMap.get(uuid);
			if (followers == null) {
				followers = new HashSet<>();
				followersMap.put(uuid, followers);
			} else {
				HashMap<String, HashSet<Feed>> feedMap = Database.feed;

				for (String follower: followers) {
					HashSet<Feed> feeds = feedMap.get(follower);

					if (feeds != null) {
						Feed feed = new Feed();
						feed.setUri(post.getUri());
						feed.setCaption(post.getCaption());

//						feed.setPublisher();
						feed.setTimestamp(post.getTimestamp());
						feeds.add(feed);
					}
				}

				HashSet<Feed> feedMe = feedMap.get(uuid);

				if (feedMe != null) {
					Feed feed = new Feed();
					feed.setUri(post.getUri());
					feed.setCaption(post.getCaption());

//						feed.setPublisher();
					feed.setTimestamp(post.getTimestamp());
					feedMe.add(feed);
				}
			}

			if (this.onSuccessListener != null)
				this.onSuccessListener.onSuccess(null);

			if (this.onCompleteListener != null)
				this.onCompleteListener.onComplete();

		});
		return this;
	}

	public Database createUser(String name, String email, String password) {
		timeout(() -> {
			UserAuth userAuth = new UserAuth();
			userAuth.setEmail(email);
			userAuth.setPassword(password);

			usersAuth.add(userAuth);

			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setUuid(userAuth.getUUID());

			boolean added = users.add(user);
			if (added) {
				Database.userAuth = userAuth;

				HashMap<String, HashSet<String>> followersMap = Database.followers;
				followersMap.put(userAuth.getUUID(), new HashSet<>());

				HashMap<String, HashSet<Feed>> feedMap = Database.feed;
				feedMap.put(userAuth.getUUID(), new HashSet<>());

				if (this.onSuccessListener != null)
					this.onSuccessListener.onSuccess(userAuth);
			} else {
				Database.userAuth = null;
				if (this.onFailureListener != null)
					this.onFailureListener.onFailure(new IllegalArgumentException("Usuário já existe"));
			}
			if (this.onCompleteListener != null)
				this.onCompleteListener.onComplete();
		});
		return this;
	}

	public Database login(String email, String password) {
		this.timeout(() -> {
			UserAuth userAuth = new UserAuth();
			userAuth.setEmail(email);
			userAuth.setPassword(password);

			if (usersAuth.contains(userAuth)) {
				Database.userAuth = userAuth;
				this.onSuccessListener.onSuccess(userAuth);
			} else {
				Database.userAuth = null;
				this.onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
			}
			this.onCompleteListener.onComplete();
		});
		return this;
	}

	private void timeout(Runnable r) {
		new Handler().postDelayed(r, 1000);
	}

	public interface OnSuccessListener<T> {
		void onSuccess(T response);
	}

	public interface OnFailureListener {
		void onFailure(Exception e);
	}

	public interface OnCompleteListener {
		void onComplete();
	}

	public UserAuth getUser() {
		return Database.userAuth;
	}
}
