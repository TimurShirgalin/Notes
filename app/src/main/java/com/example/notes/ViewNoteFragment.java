package com.example.notes;

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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.example.notes.Data.VIEW_NOTE;

public class ViewNoteFragment extends Fragment {

    private int index;

    public static ViewNoteFragment newInstance(int index) {
        ViewNoteFragment fragment = new ViewNoteFragment();
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
        AppCompatTextView textViewNoteName = new AppCompatTextView(getContext());
        textViewNoteName.setTextSize(30);
        textViewNoteName.setTextColor(Color.BLACK);
        String note = Data.notes.get(index);
        String noteName = Data.noteNames.get(index);
        String date = Data.dates.get(index);
        String text = String.format("%s\n%s\n\n%s", noteName, date, note);
        SpannableString noteText = new SpannableString(text);
        noteText.setSpan(new RelativeSizeSpan(0.4f), noteName.length() + 1,
                date.length() + noteName.length() + 1, 0);
        noteText.setSpan(new RelativeSizeSpan(0.7f), noteText.length() - note.length() - 2,
                noteText.length(), 0);
        noteText.setSpan(new ForegroundColorSpan(Color.GRAY), noteName.length() + 1,
                date.length() + noteName.length() + 1, 0);
        linearLayout.addView(textViewNoteName);
        textViewNoteName.setText(noteText);
        textViewNoteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEdit(index);
            }
        });
//        AppCompatButton buttonEditDate = view.findViewById(R.id.button_edit_date);
//        buttonEditDate.setOnClickListener(v -> initDateChange(index));
    }

    private void initEdit(int index) {
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(index);
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.edit_land, editNoteFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

//    private void initDateChange(int index) {
//        Context context = getContext();
//        if (context != null) {
//            Intent intent = new Intent(getActivity(), ChangeDateActivity.class);
//            intent.putExtra(DateChangeFragment.KEY_CHANGE, index);
//            startActivity(intent);
//        }
//    }
}