package org.reader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.writer.model.Months;
import org.writer.model.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonsReader {
    private final String FIELD_SEPARATOR = ";";
    public List<Person> readFromCsv(String fileName) {
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

    public List<Person> readWithAnnotationFromCsv(String fileName) {
        List<Person> persons = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            CsvToBean beanParser = new CsvToBeanBuilder<>(fileReader).withType(Person.class).build();
           persons = beanParser.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
