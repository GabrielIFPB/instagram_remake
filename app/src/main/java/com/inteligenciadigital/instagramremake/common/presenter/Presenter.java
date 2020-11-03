package com.inteligenciadigital.instagramremake.common.presenter;

import com.inteligenciadigital.instagramremake.common.models.UserAuth;

public interface Presenter {

	void onSuccess(UserAuth userAuth);

	void onError(String message);

	void onComplete();
}
