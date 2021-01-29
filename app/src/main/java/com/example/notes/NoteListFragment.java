package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        SharedPreferences sP = requireActivity().getSharedPreferences(Data.SHARED_PREF_NOTE, Context.MODE_PRIVATE);
        int currentIndex = sP.getInt(Data.NOTE_NUMBER, 0);
        if (isLandscape) {
            showLandNote(currentIndex);
        } else {
            LinearLayout baseLayout = getActivity().findViewById(R.id.base_layout);
            baseLayout.setBackgroundColor(getContext().getColor(R.color.gray_background));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesData(view);
    }

    private void initNotesData(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        createAddButton(linearLayout);
        for (int i = 0; i < Data.notes.size(); i++) {
            AppCompatButton noteButton = getFragmentItems(linearLayout, i);
            noteButton.setOnClickListener(v -> showNote(v.getId()));
        }
    }

    private void showNote(int index) {
        setSharedPreferences(index);
        if (isLandscape) {
            showLandNote(index);
        } else {
            showPortNote(index);
        }
    }

    private void showLandNote(int index) {
        ViewNoteFragment viewNoteFragment = ViewNoteFragment.newInstance(index);
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_view_note, viewNoteFragment);
            ft.addToBackStack("NoteViewLand");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    private void showPortNote(int index) {
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(index);
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, editNoteFragment);
            ft.addToBackStack("NoteList");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    private AppCompatButton getFragmentItems(LinearLayout linearLayout, int i) {
        ContextThemeWrapper context = new ContextThemeWrapper(getContext(), R.style.ButtonNote);
        AppCompatButton noteButton = new AppCompatButton(context);
        noteButton.setGravity(0);
        String noteName = String.valueOf(Data.noteNames.get(i));
        String date = String.valueOf(Data.dates.get(i));
        String noteButtonText = String.format("%s\n%s", noteName, date);
        SpannableString noteButtonTextSpan = new SpannableString(noteButtonText);
        int len = date.length() + noteName.length() + 1;
        noteButtonTextSpan.setSpan(new RelativeSizeSpan(0.5f), noteName.length() + 1, len, 0);
        noteButtonTextSpan.setSpan(new ForegroundColorSpan(Color.GRAY), noteName.length() + 1, len, 0);
        noteButton.setText(noteButtonTextSpan);
        linearLayout.addView(noteButton);
        noteButton.setId(i);
        return noteButton;
    }

    private void setSharedPreferences(int index) {
        SharedPreferences sP = requireActivity().getSharedPreferences(Data.SHARED_PREF_NOTE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt(Data.NOTE_NUMBER, index);
        editor.apply();
    }

    private void createAddButton(LinearLayout linearLayout) {
        ContextThemeWrapper context = new ContextThemeWrapper(getContext(), R.style.ButtonAddNote);
        AppCompatButton addButton = new AppCompatButton(context);
        linearLayout.addView(addButton);
        addButton.setText("+");
        addButton.setTextColor(Color.WHITE);
        ViewGroup.LayoutParams params = addButton.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        addButton.setLayoutParams(params);
    }
}