package org.writer;

import org.writer.model.Months;
import org.writer.model.Person;
import org.writer.model.Student;

import java.util.ArrayList;
import java.util.List;

public class Main {
    final static String PERSONS_FILE_NAME = "persons_data.csv";

    public static void main(String[] args) {
        Person firstPerson = Person.builder()
                .firstName("Anton")
                .lastName("Ivanov")
                .dayOfBirth(1)
                .monthOfBirth(Months.AUGUST)
                .yearOfBirth(1990)
                .build();
        Person secondPerson = Person.builder()
                .firstName("Ivan")
                .lastName("Antonov")
                .dayOfBirth(21)
                .monthOfBirth(Months.APRIL)
                .yearOfBirth(1985)
                .build();
        List<Person> persons = new ArrayList<>();
        persons.add(firstPerson);
        persons.add(secondPerson);

        Writable writer = new CsvWriter();
        writer.writeToFile(persons, PERSONS_FILE_NAME);
        Student firstStudent = Student.builder()
                .name("Nickolay")
                .score(List.of("good", "satisfactorily"))
                .build();
        Student secondStudent = Student.builder()
                .name("Vasilii")
                .score(List.of("excellent", "good", "good"))
                .build();
        List<Student> students = new ArrayList<>();
        students.add(firstStudent);
        students.add(secondStudent);
        writer.writeToFile(students, "students_data.csv");
    }
}