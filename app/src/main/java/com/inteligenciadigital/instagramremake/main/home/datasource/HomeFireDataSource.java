package com.inteligenciadigital.instagramremake.main.home.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.inteligenciadigital.instagramremake.common.models.Feed;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFireDataSource implements HomeDataSource {

	@Override
	public void findFeed(Presenter<List<Feed>> presenter) {
		String uuid = FirebaseAuth.getInstance().getUid();

		FirebaseFirestore.getInstance().collection("feeds")
				.document(uuid)
				.collection("posts")
				.orderBy("timestamp", Query.Direction.DESCENDING)
				.get()
				.addOnSuccessListener(queryDocumentSnapshots -> {
					List<Feed> feeds = new ArrayList<>();
					List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
					for (DocumentSnapshot documentSnapshot: documentSnapshots) {
						Feed feed = documentSnapshot.toObject(Feed.class);
						feeds.add(feed);
					}
					presenter.onSuccess(feeds);
				})
				.addOnFailureListener(e -> presenter.onError(e.getMessage()))
				.addOnCompleteListener(task -> presenter.onComplete());
	}
}
