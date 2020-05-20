package com.example.votingapp.data_storage.voting_edit;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.votingapp.data_storage.QuestionType;

public class TextEditQuestion extends EditQuestion implements Parcelable {
    public TextEditQuestion(String question){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
    }

    protected TextEditQuestion(Parcel in) {
        questionType = QuestionType.valueOf(in.readString());
        questionString = in.readString();
    }

    public static final Creator<TextEditQuestion> CREATOR = new Creator<TextEditQuestion>() {
        @Override
        public TextEditQuestion createFromParcel(Parcel in) {
            return new TextEditQuestion(in);
        }

        @Override
        public TextEditQuestion[] newArray(int size) {
            return new TextEditQuestion[size];
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
