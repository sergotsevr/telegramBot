package com.gotsev.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotsev.models.FirstTest;
import com.gotsev.models.interfaces.Test;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class TestServiceFromJson implements TestService{

    private String PATH_TO_TESTS = "src/main/resources/tests";
    private ObjectMapper objectMapper = new ObjectMapper();
    ClassLoader classLoader = getClass().getClassLoader();

    List<Test> tests = new ArrayList<>();

    @SneakyThrows
    public TestServiceFromJson(){
        try (Stream<Path> paths = Files.walk(Paths.get(PATH_TO_TESTS))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(pathToTest -> tests.add(readTest(pathToTest)));
        }
        for (Test test : tests){
            System.out.println(test.getSection());
        }
    }

    @Override
    public List<String> getSections() {
        return null;
    }

    @Override
    public List<Test> getTestsFromSection(String section) {
        return null;
    }

    @SneakyThrows
    private Test readTest(Path path){
        FileInputStream fis = new FileInputStream(path.toString());
        String data = IOUtils.toString(fis, "UTF-8");
        return objectMapper.readValue(data, FirstTest.class);
    }

    @SneakyThrows
    public void writeTestInJson(){
        try {
            log.info("Writing test to disk");
            String fileName = "/test.json";
            FirstTest test = new FirstTest("OOP");
            String jsonTest = objectMapper.writeValueAsString(test);
            Path settingsPath = Paths.get(PATH_TO_TESTS+fileName);
            if (Files.notExists(settingsPath)) {
                Files.createFile(settingsPath);
            }
            Files.write(settingsPath, jsonTest.getBytes());
        } catch (JsonProcessingException e) {
            log.error("Test creation failed");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Test write failed");
            throw new RuntimeException(e);
        }
    }

}