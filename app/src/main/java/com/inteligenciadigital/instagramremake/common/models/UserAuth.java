package com.inteligenciadigital.instagramremake.common.models;

import java.util.Objects;

public class UserAuth {

	private String email;
	private String password;

	public UserAuth() {
	}

	public UserAuth(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUUID() {
		return String.valueOf(this.hashCode());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserAuth userAuth = (UserAuth) o;

		if (!email.equals(userAuth.email)) return false;
		return password.equals(userAuth.password);
	}

	@Override
	public int hashCode() {
		int result = email.hashCode();
		result = 31 * result + password.hashCode();
		return result;
	}
}
