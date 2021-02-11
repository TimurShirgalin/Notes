package com.example.notes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {
    public static class Fields {
        public final static String NOTE_NAME = "noteName";
        public final static String NOTE_DATE = "noteDate";
        public final static String NOTE = "note";
    }

    public static CardData toCardData(String id, Map<String, Object> doc) {
        Timestamp time = (Timestamp)doc.get(Fields.NOTE_DATE);
        CardData answer = new CardData((String) doc.get(Fields.NOTE_NAME), time.toDate(), (String) doc.get(Fields.NOTE));
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(CardData cardData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NOTE_NAME, cardData.getNoteName());
        answer.put(Fields.NOTE_DATE, cardData.getNoteDate());
        answer.put(Fields.NOTE, cardData.getNote());
        return answer;
    }
}
