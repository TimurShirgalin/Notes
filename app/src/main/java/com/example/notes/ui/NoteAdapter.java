package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.data.CardData;
import com.example.notes.data.CardSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private CardSource cardSource;
    private final Fragment fragment;
    private ListClickListener clickListener;
    private int cardPosition;

    public NoteAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setDataSource(CardSource cardSource) {
        this.cardSource = cardSource;
        notifyDataSetChanged();
    }

    public int getCardPosition() {
        return cardPosition;
    }

    public void setListClickListener(ListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_note_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(cardSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return cardSource.size();
    }

    public interface ListClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteName;
        private final TextView noteDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            noteDate = itemView.findViewById(R.id.noteDate);
            registerContextMenu(itemView);
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onItemClick(v, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(v -> {
                cardPosition = getLayoutPosition();
                itemView.showContextMenu();
                return true;
            });
        }

        private void setData(CardData cardData) {
            noteName.setText(cardData.getNoteName());
            noteDate.setText(cardData.getNoteDate().toString());
        }

        private void registerContextMenu(View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    cardPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }
    }
}
