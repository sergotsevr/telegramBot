package com.gotsev.models;

import com.gotsev.models.interfaces.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionForJava implements Question {

    private Integer number;

    private String statement;

    private String answer;

    private List<String> options = new ArrayList<>();


    @Override
    public List<String> getOptions() {
        return options;
    }

    public boolean checkAnswer(String answer){
        return this.answer.equals(answer);
    }
}
