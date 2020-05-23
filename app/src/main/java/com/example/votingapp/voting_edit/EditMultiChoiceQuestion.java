package com.example.votingapp.voting_edit;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.votingapp.data_storage.QuestionType;

import java.util.ArrayList;

public class EditMultiChoiceQuestion extends EditQuestion implements Parcelable {

    //    the name of each class
    private ArrayList<String> choices;

    public EditMultiChoiceQuestion(String question, ArrayList<String> choices){
        super.questionString = question;
        super.questionType = QuestionType.MULTI_CHOICE;
        this.choices = choices;
    }

    protected EditMultiChoiceQuestion(Parcel in) {
        questionString = in.readString();
        questionType = QuestionType.valueOf(in.readString());
        choices = in.createStringArrayList();

    }

    public static final Creator<EditMultiChoiceQuestion> CREATOR = new Creator<EditMultiChoiceQuestion>() {
        @Override
        public EditMultiChoiceQuestion createFromParcel(Parcel in) {
            return new EditMultiChoiceQuestion(in);
        }

        @Override
        public EditMultiChoiceQuestion[] newArray(int size) {
            return new EditMultiChoiceQuestion[size];
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
