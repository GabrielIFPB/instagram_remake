package com.inteligenciadigital.instagramremake.common.models;

import android.net.Uri;
import android.os.Handler;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class Database {

	private static Set<User> users;
	private static Set<Uri> storages;
	private static Set<UserAuth> usersAuth;
	private static Database INSTANCE;

	private OnSuccessListener onSuccessListener;
	private OnFailureListener onFailureListener;
	private OnCompleteListener onCompleteListener;
	private UserAuth userAuth;

	static {
		users = new HashSet<>();
		usersAuth = new HashSet<>();
		storages = new HashSet<>();

//		usersAuth.add(new UserAuth("gabriel@gmail.com", "123456"));
//		usersAuth.add(new UserAuth("joba@gmail.com", "1234"));
//		usersAuth.add(new UserAuth("juliana@gmail.com", "12345"));
//		usersAuth.add(new UserAuth("jose@gmail.com", "1234567"));
	}

	public static Database getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Database();
		return INSTANCE;
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

	public Database addPhoto(String uuid, Uri uri) {
		timeout(() ->{
			Database.users = Database.users;
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
				this.userAuth = userAuth;
				this.onSuccessListener.onSuccess(userAuth);
			} else {
				this.userAuth = null;
				this.onFailureListener.onFailure(new IllegalArgumentException("Usuário já existe"));
			}
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
				this.userAuth = userAuth;
				this.onSuccessListener.onSuccess(userAuth);
			} else {
				this.userAuth = null;
				this.onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
			}
			this.onCompleteListener.onComplete();
		});
		return this;
	}

	private void timeout(Runnable r) {
		new Handler().postDelayed(r, 2000);
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
		return this.userAuth;
	}
}
