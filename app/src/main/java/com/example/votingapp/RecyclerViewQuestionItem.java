package com.example.votingapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.votingapp.data_storage.Question;
import com.example.votingapp.data_storage.QuestionType;

public class RecyclerViewQuestionItem  implements Parcelable {

    private Question data;
    private QuestionType type;

    protected RecyclerViewQuestionItem(Parcel in) {
        data = in.readParcelable(Question.class.getClassLoader());
        type = QuestionType.valueOf(in.readString());
    }

    public static final Creator<RecyclerViewQuestionItem> CREATOR = new Creator<RecyclerViewQuestionItem>() {
        @Override
        public RecyclerViewQuestionItem createFromParcel(Parcel in) {
            return new RecyclerViewQuestionItem(in);
        }

        @Override
        public RecyclerViewQuestionItem[] newArray(int size) {
            return new RecyclerViewQuestionItem[size];
        }
    };



    public Question getData() {
        return data;
    }

    public QuestionType getType() {
        return type;
    }

    public RecyclerViewQuestionItem(Question data, QuestionType type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeString(type.name());

    }
}