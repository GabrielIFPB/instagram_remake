package com.inteligenciadigital.instagramremake.common.presenter;

public interface Presenter<T> {

	void onSuccess(T userAuth);

	void onError(String message);

	void onComplete();
}
