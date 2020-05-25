package com.example.votingapp.data_type.question;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MultiChoiceParcel extends QuestionParcel implements Parcelable {

    //    the name of each class
    private ArrayList<String> choices;

    public MultiChoiceParcel(String question, ArrayList<String> choices){
        super.questionString = question;
        super.questionType = QuestionType.MULTI_CHOICE;
        this.choices = choices;
    }

    protected MultiChoiceParcel(Parcel in) {
        questionString = in.readString();
        questionType = QuestionType.valueOf(in.readString());
        choices = in.createStringArrayList();

    }

    public static final Creator<MultiChoiceParcel> CREATOR = new Creator<MultiChoiceParcel>() {
        @Override
        public MultiChoiceParcel createFromParcel(Parcel in) {
            return new MultiChoiceParcel(in);
        }

        @Override
        public MultiChoiceParcel[] newArray(int size) {
            return new MultiChoiceParcel[size];
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
