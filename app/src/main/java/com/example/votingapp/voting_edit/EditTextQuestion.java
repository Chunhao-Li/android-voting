package com.example.votingapp.voting_edit;

import android.os.Parcel;

import com.example.votingapp.data_storage.QuestionType;

public class EditTextQuestion extends EditQuestion  {
    public EditTextQuestion(String question){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
    }// constructor for text q

    protected EditTextQuestion(Parcel in) {
        questionType = QuestionType.valueOf(in.readString());
        questionString = in.readString();
    }// parcel interface

    public static final Creator<EditTextQuestion> CREATOR = new Creator<EditTextQuestion>() {
        @Override
        public EditTextQuestion createFromParcel(Parcel in) {
            return new EditTextQuestion(in);
        }

        @Override
        public EditTextQuestion[] newArray(int size) {
            return new EditTextQuestion[size];
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
