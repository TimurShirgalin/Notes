package com.example.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class DateChangeFragment extends Fragment {
    static final String KEY_CHANGE = "change";
    private int index;

    public static DateChangeFragment newInstance(int index) {
        DateChangeFragment fragment = new DateChangeFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CHANGE, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(KEY_CHANGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatButton buttonChangeDate = view.findViewById(R.id.button_change_date);
        DatePicker datePicker = view.findViewById(R.id.dataPicker);
        buttonChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDate = String.format("%d.%d.%d", datePicker.getDayOfMonth(), (datePicker.getMonth() + 1), datePicker.getYear());
                Data.dates.set(index, newDate);
                getActivity().finish();
            }
        });
    }
}