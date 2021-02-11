package com.example.notes.data;

public interface CardSource {
    CardSource init(CardSourceResponse cardSourceResponse);
    CardData getCardData(int position);
    int size();
    void addCardData(CardData cardData);
    void deleteAll();
    void delete(int position);
    void change(int position, CardData cardData);
}
