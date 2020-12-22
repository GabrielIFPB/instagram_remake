package com.inteligenciadigital.instagramremake.register.datasource;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

public class RegisterFireDataSource implements RegisterDataSource {

	@Override
	public void createUser(String name, String email, String password, Presenter presenter) {
		FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
				.addOnSuccessListener(authResult -> {
					User user = new User();
					user.setEmail(email);
					user.setName(name);
					user.setUuid(authResult.getUser().getUid());

					FirebaseFirestore.getInstance().collection("users")
							.document(authResult.getUser().getUid())
							.set(user)
							.addOnSuccessListener(aVoid -> presenter.onSuccess(authResult.getUser()))
							.addOnCompleteListener(task -> presenter.onComplete());
				})
				.addOnFailureListener(e -> {
					presenter.onError(e.getMessage());
					presenter.onComplete();
				});
	}

	@Override
	public void addPhoto(Uri uri, Presenter presenter) {
		String uuid = FirebaseAuth.getInstance().getUid();
		if (uuid == null || uri == null || uri.getLastPathSegment() == null)
			return;

		StorageReference storageReference = FirebaseStorage.getInstance().getReference();
		StorageReference imageReference = storageReference.child("images/")
				.child(uuid)
				.child(uri.getLastPathSegment());

		imageReference.putFile(uri)
				.addOnSuccessListener(taskSnapshot -> {
					long totalByteCount = taskSnapshot.getTotalByteCount();
					Log.d("TESTE", "File uploaded size: " + totalByteCount);
					imageReference.getDownloadUrl()
							.addOnSuccessListener(uriResponse -> {

								DocumentReference documentReference = FirebaseFirestore.getInstance()
										.collection("users").document(uuid);

								documentReference.get()
										.addOnSuccessListener(documentSnapshot -> {
											User user = documentSnapshot.toObject(User.class);
											user.setPhotoUrl(uriResponse.toString());

											documentReference.set(user)
													.addOnSuccessListener(aVoid -> {
														presenter.onSuccess(true);
														presenter.onComplete();
													});
										});

							});
				});
	}
}
