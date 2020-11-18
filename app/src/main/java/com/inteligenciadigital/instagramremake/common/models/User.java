package com.inteligenciadigital.instagramremake.common.models;

import android.net.Uri;

public class User {

	private String uuid;
	private String name;
	private String email;
	private Uri uri;

	public String getUuid() {
		return uuid;
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
