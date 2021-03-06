package com.gotsev.models;

import com.gotsev.models.interfaces.Question;
import com.gotsev.models.interfaces.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FirstTest implements Test {

    String section;
    List<Question> questions = new ArrayList<>();

    public FirstTest(String section){
        QuestionForJava firstQuestion = new QuestionForJava();
        firstQuestion.setNumber(0);
        firstQuestion.setStatement("First Question");
        questions.add(firstQuestion);
        QuestionForJava secondQuestion = new QuestionForJava();
        secondQuestion.setStatement("Second Question");
        secondQuestion.setNumber(1);
        questions.add(secondQuestion);
        QuestionForJava thirdQuestion = new QuestionForJava();
        thirdQuestion.setStatement("third Question");
        thirdQuestion.setNumber(2);
        questions.add(thirdQuestion);
        this.section=section;
    }


}
