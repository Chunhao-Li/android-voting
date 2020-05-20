package com.example.votingapp.data_storage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MultipleChoiceQuestion extends Question implements Parcelable {

    //    the name of each class
    private ArrayList<String> choices;

    public MultipleChoiceQuestion(String question, ArrayList<String> choices){
        super.questionString = question;
        super.questionType = QuestionType.MULTI_CHOICE;
        this.choices = choices;
    }

    protected MultipleChoiceQuestion(Parcel in) {
        questionString = in.readString();
        questionType = QuestionType.valueOf(in.readString());
        choices = in.createStringArrayList();

    }

    public static final Creator<MultipleChoiceQuestion> CREATOR = new Creator<MultipleChoiceQuestion>() {
        @Override
        public MultipleChoiceQuestion createFromParcel(Parcel in) {
            return new MultipleChoiceQuestion(in);
        }

        @Override
        public MultipleChoiceQuestion[] newArray(int size) {
            return new MultipleChoiceQuestion[size];
        }
    };

    public ArrayList<String> getChoices(){
        return this.choices;
    }

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
        dest.writeString(questionString);
        dest.writeString(questionType.name());
        dest.writeStringList(choices);
    }
}
