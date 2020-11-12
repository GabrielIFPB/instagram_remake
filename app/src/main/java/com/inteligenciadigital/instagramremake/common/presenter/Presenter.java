package com.inteligenciadigital.instagramremake.common.presenter;

import com.inteligenciadigital.instagramremake.common.models.UserAuth;

public interface Presenter<T> {

	void onSuccess(T userAuth);

	void onError(String message);

	void onComplete();
}
