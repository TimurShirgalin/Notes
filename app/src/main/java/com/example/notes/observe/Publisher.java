package com.example.notes.observe;

import com.example.notes.data.CardData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    private List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notify(CardData cardData) {
        for (Observer observer : observers) {
            observer.updateCardData(cardData);
            unsubscribe(observer);
        }
    }

    public void notify1() {
        observers.clear();
    }
}
