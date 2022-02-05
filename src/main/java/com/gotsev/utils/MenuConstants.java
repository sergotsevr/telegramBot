package com.gotsev.utils;

public enum MenuConstants {

    TEST("Test"),
    FIRST_QUESTION("First question"),
    TEST_RESULT("Test result"),
    MENU("Menu");

    private String name;
    MenuConstants(String name) {
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}
