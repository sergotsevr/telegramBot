package com.gotsev.models.interfaces;


import java.util.List;


public interface Question {

    List <String> getOptions();

    Integer getNumber();

    String getStatement();

    boolean checkAnswer(String answer);
}
