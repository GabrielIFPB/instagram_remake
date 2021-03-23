package com.inteligenciadigital.instagramremake.main.camera.datasource;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.io.File;
import java.util.List;

public class AddFireDataSource implements AddDataSource {

	@Override
	public void savePost(Uri uri, String caption, Presenter presenter) {
		String host = uri.getHost();

		if (host == null) { // for gallery
			uri = Uri.fromFile(new File(uri.toString()));
		}

		String uid = FirebaseAuth.getInstance().getUid();

		StorageReference storageReference = FirebaseStorage.getInstance().getReference();
		StorageReference imageStorageReference = storageReference.child("images/")
				.child(uid)
				.child(uri.getLastPathSegment());

		imageStorageReference.putFile(uri)
				.addOnSuccessListener(taskSnapshot -> {
					imageStorageReference.getDownloadUrl()
							.addOnSuccessListener(uriResponse -> {
								Post post = new Post();
								post.setPhotoUrl(uriResponse.toString());
								post.setCaption(caption);
								post.setTimestamp(System.currentTimeMillis());

								DocumentReference postRef = FirebaseFirestore.getInstance()
										.collection("posts")
										.document(uid)
										.collection("posts")
										.document();

								postRef.set(post)
										.addOnSuccessListener(aVoid -> {
											FirebaseFirestore.getInstance().collection("followers")
													.document(uid)
													.collection("followers")
													.get()
													.addOnCompleteListener(task ->{
														if (!task.isSuccessful()) {
															return;
														}
														List<DocumentSnapshot> documents = task.getResult().getDocuments();
														for (DocumentSnapshot documentSnapshot: documents) {
															User user = documentSnapshot.toObject(User.class);
															Feed feed = new Feed();
															feed.setPhotoUrl(post.getPhotoUrl());
															feed.setCaption(post.getCaption());
															feed.setTimestamp(post.getTimestamp());
															feed.setUuid(postRef.getId());
															feed.setPublisher(user);

															FirebaseFirestore.getInstance()
																	.collection("feeds")
																	.document(user.getUuid())
																	.collection("posts")
																	.document(postRef.getId())
																	.set(feed);
														}
													});


											String id = postRef.getId();

											Feed feed = new Feed();
											feed.setPhotoUrl(post.getPhotoUrl());
											feed.setCaption(post.getCaption());
											feed.setTimestamp(post.getTimestamp());
											feed.setUuid(id);

											FirebaseFirestore.getInstance().collection("users")
													.document(uid)
													.get()
													.addOnCompleteListener(task -> {
														User user = task.getResult().toObject(User.class);
														feed.setPublisher(user);
														FirebaseFirestore.getInstance()
																.collection("feeds")
																.document(uid)
																.collection("posts")
																.document(id)
																.set(feed);
													});

											presenter.onSuccess(null);
											presenter.onComplete();
										})
										.addOnFailureListener(e -> presenter.onError(e.getMessage()));
							})
							.addOnFailureListener(e -> presenter.onError(e.getMessage()));
				})
				.addOnFailureListener(e -> presenter.onError(e.getMessage()));
	}
}
