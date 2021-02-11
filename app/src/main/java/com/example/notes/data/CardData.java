package com.example.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

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
    private Date noteDate;
    private String note;
    private String id;

    public CardData(String noteName, Date noteDate, String note) {
        this.noteName = noteName;
        this.noteDate = noteDate;
        this.note = note;
    }

    protected CardData(Parcel in) {
        noteName = in.readString();
        noteDate = new Date(in.readLong());
        note = in.readString();
    }

    public String getNoteName() {
        return noteName;
    }

    public Date getNoteDate() {
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
        dest.writeLong(noteDate.getTime());
        dest.writeString(note);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
