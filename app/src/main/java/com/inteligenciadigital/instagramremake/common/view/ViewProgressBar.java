package com.inteligenciadigital.instagramremake.common.view;

import android.content.Context;

public interface ViewProgressBar {

	Context getContext();

	void showProgressBar();

	void hideProgressBar();

	void setStatusBarDark();
}
