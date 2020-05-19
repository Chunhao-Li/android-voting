package com.example.votingapp.backend_storage;

import java.util.ArrayList;

public class Question {
    private String question;
    QuestionType type;
    ArrayList<String> choices;

    public Question(String question, QuestionType type) {
        this.question = question;
        this.type = type;
    }
}
