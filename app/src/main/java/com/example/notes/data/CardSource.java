package com.example.notes.data;

public interface CardSource {
    CardData getCardData(int position);
    int size();
    void addCardData(CardData cardData);
    void deleteAll();
    void delete(int position);
    void change(int position, CardData cardData);
}
