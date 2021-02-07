package com.example.notes.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Data;
import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.data.CardData;
import com.example.notes.data.CardSource;
import com.example.notes.data.CardSourceImpl;
import com.example.notes.observe.Publisher;

public class NoteListFragment extends Fragment {
    private boolean isLandscape;
    private CardSource data;
    private CardData cardData;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesData(view);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        SharedPreferences sP = requireActivity().getSharedPreferences(Data.SHARED_PREF_NOTE, Context.MODE_PRIVATE);
        int currentIndex = sP.getInt(Data.NOTE_NUMBER, 0);
        if (isLandscape) {
            showViewNote(currentIndex);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.item_menu, menu);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    private void initNotesData(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        data = new CardSourceImpl().init();
        noteAdapter = new NoteAdapter(data, this);
        recyclerView.setAdapter(noteAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        noteAdapter.setListClickListener((view1, position) -> {
            showNote(position);
        });
    }

    private void showNote(int position) {
        setSharedPreferences(position);
        if (isLandscape) {
            showViewNote(position);
        } else {
            showEditView(position);
        }
    }

    private void showViewNote(int position) {
        cardData = data.getCardData(position);
        ViewNoteFragment viewNoteFragment = ViewNoteFragment.newInstance(cardData);
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_view_note, viewNoteFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
        publisher.notify1();
        publisher.subscribe(cardData -> {
            data.change(position, cardData);
            noteAdapter.notifyItemChanged(position);
            recyclerView.scrollToPosition(position);
            showViewNote(position);
        });
    }

    private void showEditView(int position) {
        cardData = data.getCardData(position);
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(cardData);
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.edit_land, editNoteFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
        publisher.notify1();
        publisher.subscribe(cardData -> {
            data.change(position, cardData);
            noteAdapter.notifyItemChanged(position);
            recyclerView.scrollToPosition(position);
            if (isLandscape) {
                showViewNote(position);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note_item:
                cardData = new CardData(null, null, null);
                EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(cardData);
                if (getActivity() != null) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.edit_land, editNoteFragment)
                            .addToBackStack(null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
                publisher.notify1();
                publisher.subscribe(cardData -> {
                    data.addCardData(cardData);
                    noteAdapter.notifyItemInserted(data.size() - 1);
                    recyclerView.scrollToPosition(data.size() - 1);
                });
                return true;
            case R.id.deleteAll:
                data.deleteAll();
                noteAdapter.notifyDataSetChanged();
                if (isLandscape) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Fragment fragment = fm.findFragmentById(R.id.fragment_view_note);
                    if (fragment != null) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment)
                                .commit();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSharedPreferences(int index) {
        SharedPreferences sP = requireActivity().getSharedPreferences(Data.SHARED_PREF_NOTE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt(Data.NOTE_NUMBER, index);
        editor.apply();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = noteAdapter.getCardPosition();
        cardData = data.getCardData(position);
        switch (item.getItemId()) {
            case R.id.menu_change:
                showEditView(position);
                return true;
            case R.id.menu_delete:
                data.delete(position);
                noteAdapter.notifyItemRemoved(position);
                if (isLandscape) {
                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    Fragment fragment = fm.findFragmentById(R.id.fragment_view_note);
                    if (fragment != null) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment).commit();
                    }
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }
}