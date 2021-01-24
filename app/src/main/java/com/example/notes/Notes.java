package com.example.notes;

import java.util.ArrayList;

public class Notes {
    private ArrayList<String> notes = new ArrayList();
    private ArrayList<String> dates = new ArrayList();
    private ArrayList<String> noteNames = new ArrayList();
    private ArrayList<String> id = new ArrayList();

    public Notes() {
    }

    public String getNotes(int id) {
        return notes.get(id);
    }

    public void setNotes(String note) {
        notes.add(note);
    }

    public String getDates(int id) {
        return dates.get(id);
    }

    public void setDates(String date) {
        dates.add(date);
    }

    public String getNoteNames(int id) {
        return noteNames.get(id);
    }

    public void setNoteNames(String noteName) {
        noteNames.add(noteName);
    }

    public int getArrayLenth() {
        return id.size();
    }

    public void setId(String i) {
        id.add(i);
    }

    public void setNotesData() {
        setId("note1");
        setId("note2");
        setId("note3");
        setDates("04.01.2021 15:19");
        setDates("12.01.2021 21:56");
        setDates("24.01.2021 12:08");
        setNotes("Однажды, в студеную зимнюю пору");
        setNotes("Я из лесу вышел; был сильный мороз");
        setNotes("Гляжу, поднимается медленно в гору");
        setNoteNames("Заметка 1");
        setNoteNames("Заметка 2");
        setNoteNames("Заметка 3");
    }
}
