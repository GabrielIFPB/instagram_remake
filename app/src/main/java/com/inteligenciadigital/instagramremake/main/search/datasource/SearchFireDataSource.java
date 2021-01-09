package com.inteligenciadigital.instagramremake.main.search.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.inteligenciadigital.instagramremake.common.models.User;
import com.inteligenciadigital.instagramremake.common.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class SearchFireDataSource implements SearchDataSource {

	@Override
	public void findUser(String query, Presenter<List<User>> presenter) {
		FirebaseFirestore.getInstance().collection("users")
				.whereEqualTo("name", query)
				.get()
				.addOnSuccessListener(queryDocumentSnapshots -> {
					List<User> users = new ArrayList<>();
					List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();

					for (DocumentSnapshot documentSnapshot: documentSnapshots) {
						User user = documentSnapshot.toObject(User.class);
						if (!user.getUuid().equals(FirebaseAuth.getInstance().getUid())) {
							users.add(user);
						}
					}
					presenter.onSuccess(users);
				})
				.addOnFailureListener(e -> presenter.onError(e.getMessage()))
				.addOnCompleteListener(task -> presenter.onComplete());
	}
}
