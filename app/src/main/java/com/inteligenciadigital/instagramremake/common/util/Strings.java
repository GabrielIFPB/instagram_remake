package com.inteligenciadigital.instagramremake.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

	private static final String rex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	private static final Pattern VALID_EMAIL_ADDRESSS_REGEX =
			Pattern.compile(rex, Pattern.CASE_INSENSITIVE);

	public static boolean emailValid(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESSS_REGEX.matcher(email);
		return matcher.find();
	}
}
