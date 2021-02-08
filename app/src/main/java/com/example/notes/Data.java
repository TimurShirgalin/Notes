package com.example.notes;

import java.util.ArrayList;

public class Data {
    public static final String VIEW_NOTE = "viewNote";
    public static final String SHARED_PREF_NOTE = "sharedPref";
    public static final String NOTE_NUMBER = "noteNumber";
    public static ArrayList<String> noteNames = new ArrayList<>();
    public static ArrayList<String> dates = new ArrayList<>();
    public static ArrayList<String> notes = new ArrayList<>();

    public static void setData() {
        noteNames.add("Заметка1");
        noteNames.add("Заметка2");
        noteNames.add("Заметка3");
        dates.add("4.1.2021");
        dates.add("12.1.2021");
        dates.add("24.1.2021");
        notes.add("Однажды, в студеную зимнюю пору");
        notes.add("Я из лесу вышел, был сильный мороз");
        notes.add("Гляжу, поднимается медленно в гору");
    }

    public static void addNote(String noteName, String date, String note) {
        noteNames.add(noteName);
        dates.add(date);
        notes.add(note);
    }

    public static void clearData() {
        noteNames.clear();
        dates.clear();
        notes.clear();
    }

    public static void remove(int position) {
        noteNames.remove(position);
        dates.remove(position);
        notes.remove(position);
    }

    public static void change(int position, String noteName, String date, String note) {
        noteNames.set(position, noteName);
        dates.set(position, date);
        notes.set(position, note);
    }
}
