package com.example.notes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Notes notes = new Notes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNotesData();
    }

    private void setNotesData() {
        notes.setId("note1");
        notes.setId("note2");
        notes.setId("note3");
        notes.setDates("04.01.2021 15:19");
        notes.setDates("12.01.2021 21:56");
        notes.setDates("24.01.2021 12:08");
        notes.setNotes("Однажды, в студеную зимнюю пору");
        notes.setNotes("Я из лесу вышел; был сильный мороз");
        notes.setNotes("Гляжу, поднимается медленно в гору");
        notes.setNoteNames("Заметка 1");
        notes.setNoteNames("Заметка 2");
        notes.setNoteNames("Заметка 3");
    }
}