package com.gotsev.models.interfaces;

import java.util.List;

public interface Test {

    List<? super Question> getQuestions();

    String getSection();
}
