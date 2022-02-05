package com.gotsev.services;

import com.gotsev.models.interfaces.Test;

import java.util.List;

public interface TestService {

    List<String> getSections();

    List<Test> getTestsFromSection(String section);

}
