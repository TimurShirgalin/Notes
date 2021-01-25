package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class NoteListFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesData(view);
    }

    private void initNotesData(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notesNames = getResources().getStringArray(R.array.NoteNames);
        String[] dates = getResources().getStringArray(R.array.Dates);
        for (int i = 0; i < notesNames.length; i++) {
            ContextThemeWrapper context = new ContextThemeWrapper(getContext(), R.style.ButtonNote);
            AppCompatButton noteButton = new AppCompatButton(context);
            noteButton.setGravity(0);
            String notesName = notesNames[i];
            String date = dates[i];
            noteButton.setText(String.format("%s\n%s", notesName, date));
            linearLayout.addView(noteButton);
            noteButton.setId(i);
            final int g = i;
            noteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNote(g);
                }
            });
        }
    }

    private void showNote(int index) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(context, ShowNoteActivity.class);
            intent.putExtra(ShowNoteFragment.INDEX, index);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }
}