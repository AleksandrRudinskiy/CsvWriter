package org.reader;

import org.exception.NullFileNameException;
import org.writer.model.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsReader {
    private final static String FIELD_SEPARATOR = ";";
    private final static String SCORES_SEPARATOR = ",";

    public List<Student> readFromCsv(String fileName) {
        if (fileName == null) {
            throw new NullFileNameException("Имя файла не должно быть null!");
        }
        List<Student> students = new ArrayList<>();
        File csvFile = new File(fileName);
        try (BufferedReader in = new BufferedReader(new FileReader(csvFile))) {
            students = in.lines().skip(1).map(line -> {
                String[] x = line.split(FIELD_SEPARATOR);
                return new Student(x[0], parseScores(x[1]));
            }).collect(Collectors.toList());
        } catch (IOException exception) {
            System.out.println("файл " + fileName + " не найден");
        }
        return students;
    }

    /**
     * Метод создает csv файл с коллекцией данных
     *
     * @param line -> строка из csv файла
     * @ return -> коллекция оценок студента
     */
    private List<String> parseScores(String line) {
        return List.of(line.split(SCORES_SEPARATOR));
    }
}
