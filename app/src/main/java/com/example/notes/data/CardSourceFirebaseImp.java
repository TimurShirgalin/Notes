package com.example.notes.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImp implements CardSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardSourceFirebaseImp]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    private List<CardData> cardsData = new ArrayList<>();

    @Override
    public CardSource init(final CardSourceResponse cardSourceResponse) {
        collection.orderBy(CardDataMapping.Fields.NOTE_DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                CardData cardData = CardDataMapping.toCardData(id, doc);
                                cardsData.add(cardData);
                            }
                            cardSourceResponse.initialized(CardSourceFirebaseImp.this);
                        } else {
                            Log.d(TAG, "success " + cardsData.size() + " qnt");
                            // do something
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                        //do something
                    }
                });
                return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null) {
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void addCardData(CardData cardData) {
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void deleteAll() {
        for (CardData cardsDatum : cardsData) {
            collection.document(cardsDatum.getId()).delete();
        }
        cardsData = new ArrayList<>();
    }

    @Override
    public void delete(int position) {
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

    @Override
    public void change(int position, CardData cardData) {
        String id = cardData.getId();
        collection.document(id).set(CardDataMapping.toDocument(cardData));
        cardsData.set(position, cardData);
    }
}
