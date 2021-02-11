package com.example.notes.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.data.CardData;
import com.example.notes.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import static com.example.notes.Data.VIEW_NOTE;

public class EditNoteFragment extends Fragment {
    private Publisher publisher;
    private CardData cardData;

    private TextInputEditText noteName;
    private TextInputEditText note;
    private TextView noteDate;

    public static EditNoteFragment newInstance(CardData cardData) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(VIEW_NOTE, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    private static EditNoteFragment newInstance() {
        EditNoteFragment fragment = new EditNoteFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
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
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        initEditText(view);
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        publisher.notify(cardData);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        cardData = collectData();
    }

    private CardData collectData() {
        String noteName = this.noteName.getText().toString();
        String note = this.note.getText().toString();
        Date noteDate = Calendar.getInstance().getTime();
        return new CardData(noteName, noteDate, note);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        EditText noteNameEdit = getActivity().findViewById(R.id.noteName_edit);
//        TextView noteDateEdit = getActivity().findViewById(R.id.noteDate_edit);
//        EditText noteEdit = getActivity().findViewById(R.id.note_edit);
//        AppCompatButton saveButton = getActivity().findViewById(R.id.save_button);
//        if (cardData.getNoteName() != null & cardData.getNoteDate() != null & cardData.getNote() != null) {
//            noteNameEdit.setText(cardData.getNoteName());
//            noteDateEdit.setText(cardData.getNoteDate().toString());
//            noteEdit.setText(cardData.getNote());
//        } else {
//            noteNameEdit.setHint(R.string.name_edit_hint);
//            noteEdit.setHint(R.string.note_hint);
//            noteDateEdit.setText(Calendar.getInstance().getTime().toString());
//        }
//        saveButton.setOnClickListener(v -> {
//            initEditText(noteNameEdit, noteDateEdit, noteEdit);
//        });
    }

    private void getCurrentDate(TextView noteDateEdit) {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        noteDateEdit.setText(String.format("%s.%s.%s", day, month, year));
    }

    private void initEditText(View view) {
    noteName = view.findViewById(R.id.noteName_edit);
    note = view.findViewById(R.id.note_edit);
    noteDate = view.findViewById(R.id.noteDate_edit);
//        String noteName = String.valueOf(noteNameEdit.getText());
//        String note = String.valueOf(noteEdit.getText());
//        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = Calendar.getInstance().getTime();
//        try {
//            date = format.parse(String.valueOf(noteDateEdit.getText()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cardData = new CardData(noteName, date, note);
//        cardData.setId(cardData.getId());
//        publisher.notify(cardData);
//        hideKeyboard();
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        fm.popBackStack();
    }

    private void populateView() {
        noteName.setText(cardData.getNoteName());
        note.setText(cardData.getNote());
        noteDate.setText(cardData.getNoteDate().toString());
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) getContext()).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}