package com.example.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

public class NoteList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Object Context;

    public NoteList() {
    }

    public static NoteList newInstance(String param1, String param2) {
        NoteList fragment = new NoteList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesData(view);
    }

    private void initNotesData(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        Notes notes = new Notes();
        notes.setNotesData();
        for (int i = 0; i < notes.getArrayLenth(); i++) {
            if (getContext() != null) {
                ContextThemeWrapper context = new ContextThemeWrapper(getContext(), R.style.ButtonNote);
                Button noteButton = new Button(context);
                String text = notes.getNoteNames(i) + "\n" + notes.getDates(i);
                noteButton.setText(text);
                noteButton.setGravity(0);
                linearLayout.addView(noteButton);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }
}