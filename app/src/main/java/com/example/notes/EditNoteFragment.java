package com.example.notes;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import static com.example.notes.Data.VIEW_NOTE;

public class EditNoteFragment extends Fragment {

    private int index;

    public static EditNoteFragment newInstance(int index) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putInt(VIEW_NOTE, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(VIEW_NOTE);
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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayout baseLayout = getActivity().findViewById(R.id.base_layout);
            baseLayout.setBackgroundColor(Color.WHITE);
        }
        AppCompatButton saveButton = createSaveButton(linearLayout);
        AppCompatEditText editTextNoteName = createEditView(linearLayout, 30, Data.noteNames);
        createTextViewDate(linearLayout);
        AppCompatEditText editTextNote = createEditView(linearLayout, 21, Data.notes);
        saveButton.setOnClickListener(v -> {
            initEditText(editTextNoteName, editTextNote);
        });

//        AppCompatTextView textViewNoteName = new AppCompatTextView(getContext());
//        textViewNoteName.setTextSize(30);
//        textViewNoteName.setTextColor(Color.BLACK);
//        String note = Data.notes.get(index);
//        String noteName = Data.noteNames.get(index);
//        String date = Data.dates.get(index);
//        String text = String.format("%s\n%s\n\n%s", noteName, date, note);
//        SpannableString noteText = new SpannableString(text);
//        noteText.setSpan(new RelativeSizeSpan(0.4f), noteName.length() + 1,
//                date.length() + noteName.length() + 1, 0);
//        noteText.setSpan(new RelativeSizeSpan(0.7f), noteText.length() - note.length() - 2,
//                noteText.length(), 0);
//        noteText.setSpan(new ForegroundColorSpan(Color.GRAY), noteName.length() + 1,
//                date.length() + noteName.length() + 1, 0);
//        linearLayout.addView(textViewNoteName);
//        textViewNoteName.setText(noteText);
    }

    private void initEditText(AppCompatEditText editTextNoteName, AppCompatEditText editTextNote) {
        String noteName = String.valueOf(editTextNoteName.getText());
        Data.noteNames.set(index, noteName);
        String note = String.valueOf(editTextNote.getText());
        Data.notes.set(index, note);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fm.popBackStack();
    }

    private void createTextViewDate(LinearLayout linearLayout) {
        AppCompatTextView textViewDate = new AppCompatTextView(getContext());
        textViewDate.setTextSize(12);
        textViewDate.setTextColor(Color.GRAY);
        linearLayout.addView(textViewDate);
        textViewDate.setText(Data.dates.get(index));
    }

    private AppCompatEditText createEditView(LinearLayout linearLayout, int i, ArrayList<String> noteNames) {
        AppCompatEditText editTextNoteName = new AppCompatEditText(getContext());
        editTextNoteName.setTextSize(i);
        editTextNoteName.setTextColor(Color.BLACK);
        linearLayout.addView(editTextNoteName);
        editTextNoteName.setText(noteNames.get(index));
        return editTextNoteName;
    }

    private AppCompatButton createSaveButton(LinearLayout linearLayout) {
        ContextThemeWrapper context = new ContextThemeWrapper(getContext(), R.style.ButtonAddNote);
        AppCompatButton saveButton = new AppCompatButton(context);
        linearLayout.addView(saveButton);
        saveButton.setText("Save");
        saveButton.setTextColor(Color.WHITE);
        ViewGroup.LayoutParams params = saveButton.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        saveButton.setLayoutParams(params);
        return saveButton;
    }
}