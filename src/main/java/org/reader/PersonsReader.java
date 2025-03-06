package org.reader;

import lombok.extern.slf4j.Slf4j;
import org.exception.NullFileNameException;
import org.writer.model.Months;
import org.writer.model.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PersonsReader {
    private final static String FIELD_SEPARATOR = ";";

    public List<Person> readFromCsv(String fileName) {
        if (fileName == null) {
            throw new NullFileNameException("Имя файла не должно быть null!");
        }
        List<Person> persons = new ArrayList<>();
        File csvFile = new File(fileName);
        try (BufferedReader in = new BufferedReader(new FileReader(csvFile))) {
            persons = in.lines().skip(1).map(line -> {
                String[] x = line.split(FIELD_SEPARATOR);
                return new Person(x[0], x[1], Integer.parseInt(x[2]), Months.valueOf(x[3]), Integer.parseInt(x[4]));
            }).collect(Collectors.toList());
        } catch (IOException exception) {
            System.out.println("файл " + fileName + " не найден");
        }
        return persons;
    }
}
