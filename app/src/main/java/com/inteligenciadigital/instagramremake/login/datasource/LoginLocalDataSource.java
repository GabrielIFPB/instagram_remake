package com.inteligenciadigital.instagramremake.login.datasource;

import com.inteligenciadigital.instagramremake.common.models.Database;
import com.inteligenciadigital.instagramremake.common.models.UserAuth;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {

	@Override
	public void login(String email, String password, Presenter presenter) {
		Database.getInstance().login(email, password)
				.addOnSuccessListener(new Database.OnSuccessListener<UserAuth>() {
					@Override
					public void onSuccess(UserAuth response) {
						presenter.onSuccess(response);
					}
				})
				.addOnFailureListener(new Database.OnFailureListener() {
					@Override
					public void onFailure(Exception e) {
						presenter.onError(e.getMessage());
					}
				})
				.addOnCompleteListener(new Database.OnCompleteListener() {
					@Override
					public void onComplete() {
						presenter.onComplete();
					}
				});
	}
}
