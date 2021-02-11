package com.example.notes.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.data.CardData;
import com.example.notes.observe.Publisher;

import static com.example.notes.Data.VIEW_NOTE;

public class ViewNoteFragment extends Fragment {
    private Publisher publisher;
    private CardData cardData;

    public static ViewNoteFragment newInstance(CardData cardData) {
        ViewNoteFragment fragment = new ViewNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(VIEW_NOTE, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(VIEW_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = (LinearLayout) view;
        TextView noteNameView = new TextView(getContext());
        noteNameView.setTextSize(getResources().getDimension(R.dimen.text_size_10));
        noteNameView.setTextColor(Color.BLACK);
        TextView noteDateView = new TextView(getContext());
        noteDateView.setTextSize(getResources().getDimension(R.dimen.text_size_4));
        TextView noteView = new TextView(getContext());
        noteView.setTextColor(Color.BLACK);
        noteView.setTextSize(getResources().getDimension(R.dimen.text_size_7));
        if (noteDateView != null || noteDateView != null || noteView != null) {
            noteNameView.setText(cardData.getNoteName());
            noteDateView.setText(cardData.getNoteDate().toString());
            noteView.setText(cardData.getNote());
        }
        linearLayout.addView(noteNameView);
        linearLayout.addView(noteDateView);
        linearLayout.addView(noteView);
        linearLayout.setOnClickListener(v -> initEdit());
    }

    private void initEdit() {
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(cardData);
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.edit_land, editNoteFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}