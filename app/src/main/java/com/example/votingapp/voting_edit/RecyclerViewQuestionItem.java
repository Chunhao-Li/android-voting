package com.example.votingapp.voting_edit;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.votingapp.data_storage.QuestionType;

public class RecyclerViewQuestionItem  implements Parcelable {//pass data to server

    private EditQuestion data;
    private QuestionType type;

    protected RecyclerViewQuestionItem(Parcel in) {
        data = in.readParcelable(EditQuestion.class.getClassLoader());
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



    public EditQuestion getData() {
        return data;
    }

    public QuestionType getType() {
        return type;
    }

    public RecyclerViewQuestionItem(EditQuestion data, QuestionType type) {
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
