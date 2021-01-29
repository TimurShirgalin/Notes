package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NoteListFragment extends Fragment {

    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesData(view);
    }

    private void initNotesData(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        for (int i = 0; i < NotesData.notes.size(); i++) {
            ContextThemeWrapper context = new ContextThemeWrapper(getContext(), R.style.ButtonNote);
            AppCompatButton noteButton = new AppCompatButton(context);
            noteButton.setGravity(0);
            String noteName = String.valueOf(NotesData.noteNames.get(i));
            String date = String.valueOf(NotesData.dates.get(i));
            String noteButtonText = String.format("%s\n%s", noteName, date);
            SpannableString noteButtonTextSpan = new SpannableString(noteButtonText);
            noteButtonTextSpan.setSpan(new RelativeSizeSpan(0.5f), noteName.length() + 1,
                    date.length() + noteName.length() + 1, 0);
            noteButtonTextSpan.setSpan(new ForegroundColorSpan(Color.GRAY), noteName.length() + 1,
                    date.length() + noteName.length() + 1, 0);
            noteButton.setText(noteButtonTextSpan);
            linearLayout.addView(noteButton);
            noteButton.setId(i);
            noteButton.setOnClickListener(v -> showNote(v.getId()));
        }
    }

    private void showNote(int index) {
        if (isLandscape) {
            showLandNote(index);
        } else {
            showPortNote(index);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showLandNote(0);
        }
    }

    private void showLandNote(int index) {
        ShowNoteFragment showNoteFragment = ShowNoteFragment.newInstance(index);
        FragmentActivity fa = getActivity();
        if (fa != null) {
            FragmentManager fm = fa.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_Note, showNoteFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    private void showPortNote(int index) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(getActivity(), ShowNoteActivity.class);
            intent.putExtra(ShowNoteFragment.INDEX, index);
            startActivity(intent);
        }
    }
}