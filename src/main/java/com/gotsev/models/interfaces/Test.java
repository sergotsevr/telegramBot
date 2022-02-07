package com.gotsev.models.interfaces;

import java.util.List;

public interface Test {

    List<? extends Question> getQuestions();

    String getSection();
}
