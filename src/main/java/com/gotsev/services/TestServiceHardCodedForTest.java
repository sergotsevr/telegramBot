package com.gotsev.services;

import com.gotsev.models.FirstTest;
import com.gotsev.models.interfaces.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestServiceHardCodedForTest implements TestService{

    List<Test> tests = new ArrayList<>();

    public TestServiceHardCodedForTest(){
        FirstTest firstTest = new FirstTest("Базовые вопросы");
        this.tests.add(firstTest);

    }

    @Override
    public List<String> getSections() {

        return tests.stream().map(Test::getSection).collect(Collectors.toList());
    }

    @Override
    public List<Test> getTestsFromSection(String section) {
        return tests.stream().filter(test -> test.getSection().equals(section)).collect(Collectors.toList());
    }


}
