package com.example.votingapp.data_type.question;

import android.os.Parcel;

public class TextQuestionParcel extends QuestionParcel {
    public TextQuestionParcel(String question) {
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
    }// constructor for text q

    private TextQuestionParcel(Parcel in) {
        questionType = QuestionType.valueOf(in.readString());
        questionString = in.readString();
    }// parcel interface

    public static final Creator<TextQuestionParcel> CREATOR = new Creator<TextQuestionParcel>() {
        @Override
        public TextQuestionParcel createFromParcel(Parcel in) {
            return new TextQuestionParcel(in);
        }

        @Override
        public TextQuestionParcel[] newArray(int size) {
            return new TextQuestionParcel[size];
        }
    };

    @Override
    public QuestionType getQuestionType() {
        return super.questionType;
    }

    @Override
    public String getQuestionString() {
        return super.questionString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.questionType.name());
        dest.writeString(questionString);
    }
}
