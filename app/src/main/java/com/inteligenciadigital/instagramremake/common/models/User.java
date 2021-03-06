package com.inteligenciadigital.instagramremake.common.models;

import android.net.Uri;

public class User {

	private String uuid;
	private String name;
	private String email;
	private int following;
	private int followers;
	private int posts;
	private Uri uri;
	private String photoUrl;

	public String getUuid() {
		return this.uuid;
	}

	public Uri getUri() {
		return this.uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFollowing() {
		return this.following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public int getFollowers() {
		return this.followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getPosts() {
		return this.posts;
	}

	public void setPosts(int posts) {
		this.posts = posts;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (name != null ? !name.equals(user.name) : user.name != null) return false;
		return email != null ? email.equals(user.email) : user.email == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}
}
