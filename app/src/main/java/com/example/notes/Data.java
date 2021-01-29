package com.example.notes;

import java.util.ArrayList;

public class Data {
    public static final String VIEW_NOTE = "viewNote";
    public static final String SHARED_PREF_NOTE = "sharedPref";
    public static final String NOTE_NUMBER = "noteNumber";
    protected static ArrayList<String> noteNames = new ArrayList<>();
    protected static ArrayList<String> dates = new ArrayList<>();
    protected static ArrayList<String> notes = new ArrayList<>();

    protected static void setData() {
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
}
