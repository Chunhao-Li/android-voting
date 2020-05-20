package com.example.votingapp.data_storage;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class TextQuestion extends Question implements Parcelable {
    public TextQuestion(String question){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
    }

    protected TextQuestion(Parcel in) {
        questionType = QuestionType.valueOf(in.readString());
        questionString = in.readString();
    }

    public static final Creator<TextQuestion> CREATOR = new Creator<TextQuestion>() {
        @Override
        public TextQuestion createFromParcel(Parcel in) {
            return new TextQuestion(in);
        }

        @Override
        public TextQuestion[] newArray(int size) {
            return new TextQuestion[size];
        }
    };

    @Override
    public QuestionType getQuestionType(){
        return super.questionType;
    }

    @Override
    public String getQuestionString(){
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
