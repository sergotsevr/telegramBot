package com.gotsev.models;

import com.gotsev.models.interfaces.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionForJava implements Question {

    private Integer number;

    private String statement;

    private final String answer;

    private List<String> options = new ArrayList<>();

    public QuestionForJava(){
        this.options.add("Right answer1");
        this.options.add("Wrong answer");
        this.options.add("Wrong answer");
        this.options.add("Wrong answer");

        this.answer = this.options.get(0);

        this.statement="First question";
    }


    @Override
    public List<String> getOptions() {
        return options;
    }

    public boolean checkAnswer(String answer){
        return this.answer.equals(answer);
    }
}
