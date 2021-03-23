package com.inteligenciadigital.instagramremake.main.profile.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.models.Post;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.models.UserProfile;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class ProfileFireDataSource implements ProfileDataSource {

	@Override
	public void findUser(String uid, Presenter<UserProfile> presenter) {
		FirebaseFirestore.getInstance()
				.collection("users")
				.document(uid)
				.get()
				.addOnSuccessListener(documentSnapshot -> {
					User user = documentSnapshot.toObject(User.class);
					FirebaseFirestore.getInstance()
							.collection("posts")
							.document(uid)
							.collection("posts")
							.orderBy("timestamp", Query.Direction.DESCENDING)
							.get()
							.addOnSuccessListener(queryDocumentSnapshots -> {

								List<Post> posts = new ArrayList<>();
								List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
								for (DocumentSnapshot document : documents) {
									Post post = document.toObject(Post.class);
									posts.add(post);
								}

								FirebaseFirestore.getInstance()
										.collection("followers")
										.document(uid)
										.collection("followers")
										.document(FirebaseAuth.getInstance().getUid())
										.get()
										.addOnSuccessListener(documentSnapshot1 -> {
											boolean following = documentSnapshot1.exists();
											presenter.onSuccess(new UserProfile(user, posts, following));
											presenter.onComplete();
										})
										.addOnFailureListener(e -> presenter.onError(e.getMessage()));
							}).addOnFailureListener(e -> presenter.onError(e.getMessage()));
				}).addOnFailureListener(e -> presenter.onError(e.getMessage()));
	}

	@Override
	public void follow(String uid) {
		DocumentReference toFollow = FirebaseFirestore.getInstance()
				.collection("users")
				.document(uid);


		toFollow.get()
				.addOnCompleteListener(task -> {
					User user = task.getResult().toObject(User.class);

					FirebaseFirestore.getInstance().runTransaction(transaction -> {
						int followers = user.getFollowers() + 1;
						transaction.update(toFollow, "followers", followers);
						return null;
					});

					DocumentReference fromFollower = FirebaseFirestore.getInstance()
							.collection("users")
							.document(FirebaseAuth.getInstance().getUid());


					fromFollower.get()
							.addOnCompleteListener(task1 -> {
								User myUser = task1.getResult().toObject(User.class);

								FirebaseFirestore.getInstance().runTransaction(transaction -> {
									int following = myUser.getFollowing() + 1;
									transaction.update(fromFollower, "following", following);
									return null;
								});

								FirebaseFirestore.getInstance()
										.collection("followers")
										.document(uid)
										.collection("followers")
										.document(FirebaseAuth.getInstance().getUid())
										.set(myUser);
							});

					FirebaseFirestore.getInstance()
							.collection("posts")
							.document(uid)
							.collection("posts")
							.orderBy("timestamp", Query.Direction.DESCENDING)
							.limit(10)
							.get()
							.addOnCompleteListener(task1 -> {
								if (task1.isSuccessful()) {
									List<DocumentSnapshot> documents = task1.getResult().getDocuments();

									for (DocumentSnapshot document : documents) {
										Post post = document.toObject(Post.class);

										Feed feed = new Feed();
										feed.setPhotoUrl(post.getPhotoUrl());
										feed.setCaption(post.getCaption());
										feed.setTimestamp(post.getTimestamp());
										feed.setUuid(document.getId());
										feed.setPublisher(user);

										FirebaseFirestore.getInstance()
												.collection("feeds")
												.document(FirebaseAuth.getInstance().getUid())
												.collection("posts")
												.document(document.getId())
												.set(feed);
									}
								}
							});
				});
	}

	@Override
	public void unfollow(String uid) {
		DocumentReference toUnfollow = FirebaseFirestore.getInstance().collection("users")
				.document(uid);

		toUnfollow.get()
				.addOnCompleteListener(task -> {
					User user = task.getResult().toObject(User.class);

					FirebaseFirestore.getInstance().runTransaction(transaction -> {
						int followers = user.getFollowers() - 1;
						transaction.update(toUnfollow, "followers", followers);
						return null;
					});

					DocumentReference fromFollower = FirebaseFirestore.getInstance().collection("users")
							.document(FirebaseAuth.getInstance().getUid());

					FirebaseFirestore.getInstance().runTransaction(transaction -> {
						User follower = transaction.get(fromFollower).toObject(User.class);
						int following = follower.getFollowing() - 1;
						transaction.update(fromFollower, "following", following);
						return null;
					});

					FirebaseFirestore.getInstance().collection("followers")
							.document(uid)
							.collection("followers")
							.document(FirebaseAuth.getInstance().getUid())
							.delete();

					FirebaseFirestore.getInstance().collection("feeds")
							.document(FirebaseAuth.getInstance().getUid())
							.collection("posts")
							.whereEqualTo("publisher.uuid", uid)
							.get()
							.addOnCompleteListener(taskRes -> {
								if (taskRes.isSuccessful()) {
									List<DocumentSnapshot> documents = taskRes.getResult().getDocuments();

									for (DocumentSnapshot document : documents) {
										DocumentReference documentReference = document.getReference();
										documentReference.delete();
									}
								}
							});
				});
	}
}
