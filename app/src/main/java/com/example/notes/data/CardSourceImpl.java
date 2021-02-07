package com.example.notes.data;

import com.example.notes.Data;

import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource {
    private List<CardData> dataSource;

    public CardSourceImpl() {
        if (Data.noteNames.size() == 0) {
            Data.setData();
        }
        dataSource = new ArrayList<>();
    }

    public CardSourceImpl init() {
        for (int i = 0; i < Data.noteNames.size(); i++) {
            dataSource.add(new CardData(Data.noteNames.get(i), Data.dates.get(i), Data.notes.get(i)));
        }
        return this;
    }

    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    public int size() {
        return dataSource.size();
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
        Data.addNote(cardData.getNoteName(), cardData.getNoteDate(), cardData.getNote());
    }

    @Override
    public void deleteAll() {
        dataSource.clear();
        Data.clearData();
    }

    @Override
    public void delete(int position) {
        dataSource.remove(position);
        Data.remove(position);
    }

    @Override
    public void change(int position, CardData cardData) {
        dataSource.set(position, cardData);
        Data.change(position, cardData.getNoteName(), cardData.getNoteDate(), cardData.getNote());
    }
}
