package com.example.notes;

import java.util.ArrayList;

public class Notes {
    private ArrayList notes = new ArrayList();
    private ArrayList dates = new ArrayList();
    private ArrayList noteNames = new ArrayList();
    private ArrayList id = new ArrayList();

    public Notes() {
    }

    public void getNotes(int id) {
        notes.get(id);
    }

    public void setNotes(String note) {
        notes.add(note);
    }

    public void getDates(int id) {
        dates.get(id);
    }

    public void setDates(String date) {
        dates.add(date);
    }

    public void getNoteNames(int id) {
        noteNames.get(id);
    }

    public void setNoteNames(String noteName) {
        noteNames.add(noteName);
    }

    public void setId(String i) {
        id.add(i);
    }
}
