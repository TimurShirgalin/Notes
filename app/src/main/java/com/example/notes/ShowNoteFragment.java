package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

public class ShowNoteFragment extends Fragment {

    static final String INDEX = "index";
    private int index;

    public static ShowNoteFragment newInstance(int index) {
        ShowNoteFragment fragment = new ShowNoteFragment();
        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatTextView textViewNoteName = view.findViewById(R.id.textView_Note_name);
        String note = NotesData.notes.get(index);
        String noteName = NotesData.noteNames.get(index);
        String date = NotesData.dates.get(index);
        String text = String.format("%s\n%s\n\n%s", noteName, date, note);
        SpannableString noteText = new SpannableString(text);
        noteText.setSpan(new RelativeSizeSpan(0.4f), noteName.length() + 1,
                date.length() + noteName.length() + 1, 0);
        noteText.setSpan(new RelativeSizeSpan(0.7f), noteText.length() - note.length() - 2,
                noteText.length(), 0);
        noteText.setSpan(new ForegroundColorSpan(Color.GRAY), noteName.length() + 1,
                date.length() + noteName.length() + 1, 0);
        textViewNoteName.setText(noteText);
        AppCompatButton buttonEditDate = view.findViewById(R.id.button_edit_date);
        buttonEditDate.setOnClickListener(v -> initDateChange(index));
    }

    private void initDateChange(int index) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(getActivity(), ChangeDateActivity.class);
            intent.putExtra(DateChangeFragment.KEY_CHANGE, index);
            startActivity(intent);
        }
    }
}