package com.example.notes;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<String> noteName;
    private ArrayList<String> date;
    private ListClickListener clickListener;

    public NoteAdapter(ArrayList<String> noteName, ArrayList<String> date) {
        this.date = date;
        this.noteName = noteName;
    }

    public void setListClickListener(ListClickListener listClickListener) {
        clickListener = listClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_note_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(noteName.get(position), date.get(position));
    }

    @Override
    public int getItemCount() {
        return noteName.size();
    }

    public interface ListClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatButton buttonList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonList = itemView.findViewById(R.id.button_list);
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        private void onBind(String noteName, String date) {
            String noteButtonText = String.format("%s\n%s", noteName, date);
            SpannableString noteButtonTextSpan = new SpannableString(noteButtonText);
            int len = date.length() + noteName.length() + 1;
            noteButtonTextSpan.setSpan(new RelativeSizeSpan(0.5f), noteName.length() + 1, len, 0);
            noteButtonTextSpan.setSpan(new ForegroundColorSpan(Color.GRAY), noteName.length() + 1, len, 0);
            buttonList.setText(noteButtonTextSpan);
        }
    }
}
