package com.example.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CardData implements Parcelable {
    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };
    private String noteName;
    private String noteDate;
    private String note;

    public CardData(String noteName, String noteDate, String note) {
        this.noteName = noteName;
        this.noteDate = noteDate;
        this.note = note;
    }

    protected CardData(Parcel in) {
        noteName = in.readString();
        noteDate = in.readString();
        note = in.readString();
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeString(noteDate);
        dest.writeString(note);
    }
}
