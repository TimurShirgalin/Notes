package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListFragment extends Fragment {
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
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
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        NoteAdapter noteAdapter = new NoteAdapter(Data.noteNames, Data.dates);
        recyclerView.setAdapter(noteAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        noteAdapter.setListClickListener((view1, position) -> {
            showNote(position);
        });
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
            ft.addToBackStack(null);
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
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    private void setSharedPreferences(int index) {
        SharedPreferences sP = requireActivity().getSharedPreferences(Data.SHARED_PREF_NOTE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt(Data.NOTE_NUMBER, index);
        editor.apply();
    }
}