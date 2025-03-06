package org.writer;

import org.data.DataGenerator;
import org.exception.NullFieldException;
import org.exception.NullFileNameException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reader.PersonsReader;
import org.writer.model.Months;
import org.writer.model.Person;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvWriterPersonsTest {
    private String CSV_FILE_NAME;

    @BeforeEach
    void start() {
        CSV_FILE_NAME = "persons_data.csv";
    }

    @Test
    void whenReaderFileNameIsNull_thenExceptionThrown() {
        File csvOutputFile = new File(CSV_FILE_NAME);
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
        writer.writeToFile(persons, CSV_FILE_NAME);
        assertTrue(csvOutputFile.exists(), "Файл " + CSV_FILE_NAME + " должен быть создан!");
        PersonsReader pr = new PersonsReader();
        Exception exception = assertThrows(NullFileNameException.class, () -> {
            pr.readFromCsv(null);
        });
        String expectedMessage = "Имя файла не должно быть null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenPersonsListWhenConvertToCsvFileThenOutputCreated() {
        File csvOutputFile = new File(CSV_FILE_NAME);
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
        writer.writeToFile(persons, CSV_FILE_NAME);
        assertTrue(csvOutputFile.exists());
    }

    @Test
    public void sizePersonsListMustBe_2_WhenConvertToCsvFile() {
        File csvOutputFile = new File(CSV_FILE_NAME);
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
        writer.writeToFile(persons, CSV_FILE_NAME);
        assertTrue(csvOutputFile.exists(), "Файл " + CSV_FILE_NAME + " должен быть создан!");
        PersonsReader pr = new PersonsReader();
        List<Person> personsFromCsv = pr.readFromCsv(CSV_FILE_NAME);
        assertEquals(2, personsFromCsv.size(), "Количество person в файле должно быть 2!");

    }

    @Test
    public void nameOfFirstPersonFromPersonsListMustBe_Anton() {
        File csvOutputFile = new File(CSV_FILE_NAME);
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
        writer.writeToFile(persons, CSV_FILE_NAME);
        assertTrue(csvOutputFile.exists(), "Файл " + CSV_FILE_NAME + " должен быть создан!");
        PersonsReader pr = new PersonsReader();
        List<Person> personsFromCsv = pr.readFromCsv(CSV_FILE_NAME);
        assertEquals("Anton", personsFromCsv.get(0).getFirstName(),
                "Имя первого в списке должно быть Anton!");
    }

    @Test
    public void nameOfSecondPersonFromPersonsListMustBe_Ivan() {
        File csvOutputFile = new File(CSV_FILE_NAME);
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
        writer.writeToFile(persons, CSV_FILE_NAME);
        assertTrue(csvOutputFile.exists(), "Файл " + CSV_FILE_NAME + " должен быть создан!");
        PersonsReader pr = new PersonsReader();
        List<Person> personsFromCsv = pr.readFromCsv(CSV_FILE_NAME);
        assertEquals("Ivan", personsFromCsv.get(1).getFirstName(),
                "Имя первого в списке должно быть Ivan!");
    }

    @Test
    public void sizeListPersonFromFile_MustBeEqual_GeneratedSizeList() {
        File csvOutputFile = new File(CSV_FILE_NAME);
        int count = 10;
        List<Person> persons = DataGenerator.generatePersons(count);
        Writable writer = new CsvWriter();
        writer.writeToFile(persons, CSV_FILE_NAME);
        PersonsReader pr = new PersonsReader();
        List<Person> personsFromCsv = pr.readFromCsv(CSV_FILE_NAME);
        int expectedCount = 10;
        assertTrue(csvOutputFile.exists(), "Файл " + CSV_FILE_NAME + " должен быть создан!");
        assertEquals(expectedCount,
                personsFromCsv.size(),
                "Количество людей в файле должно быть " + expectedCount + " !");
    }

    @Test
    void whenFirstNameIsNull_thenExceptionThrown() {
        Person firstPerson = Person.builder()
                .firstName(null)
                .lastName("Ivanov")
                .dayOfBirth(1)
                .monthOfBirth(Months.AUGUST)
                .yearOfBirth(1990)
                .build();
        List<Person> persons = List.of(firstPerson);
        Writable writer = new CsvWriter();
        Exception exception = assertThrows(NullFieldException.class, () -> {
            writer.writeToFile(persons, CSV_FILE_NAME);
        });
        String expectedMessage = "Значение поля не должно быть null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenSecondNameIsNull_thenExceptionThrown() {
        Person firstPerson = Person.builder()
                .firstName("Anton")
                .lastName(null)
                .dayOfBirth(1)
                .monthOfBirth(Months.AUGUST)
                .yearOfBirth(1990)
                .build();
        List<Person> persons = List.of(firstPerson);
        Writable writer = new CsvWriter();
        Exception exception = assertThrows(NullFieldException.class, () -> {
            writer.writeToFile(persons, CSV_FILE_NAME);
        });
        String expectedMessage = "Значение поля не должно быть null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenMonthOfBirthIsNull_thenExceptionThrown() {
        Person firstPerson = Person.builder()
                .firstName("Anton")
                .lastName("Ivanov")
                .dayOfBirth(1)
                .monthOfBirth(null)
                .yearOfBirth(1990)
                .build();
        List<Person> persons = List.of(firstPerson);
        Writable writer = new CsvWriter();
        Exception exception = assertThrows(NullFieldException.class, () -> {
            writer.writeToFile(persons, CSV_FILE_NAME);
        });
        String expectedMessage = "Значение поля не должно быть null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void sizeListPersonsFromFile_MustBeEqual_GeneratedSizeList() {
        File csvOutputFile = new File(CSV_FILE_NAME);
        int count = 100;
        List<Person> persons = DataGenerator.generatePersons(count);
        Writable writer = new CsvWriter();
        writer.writeToFile(persons, CSV_FILE_NAME);
        PersonsReader pr = new PersonsReader();
        List<Person> personsFromCsv = pr.readFromCsv(CSV_FILE_NAME);
        int expectedCount = 100;
        assertTrue(csvOutputFile.exists(), "Файл " + CSV_FILE_NAME + " должен быть создан!");
        assertEquals(expectedCount, personsFromCsv.size(),
                "Количество людей в файле должно быть " + expectedCount + " !");
    }

    @AfterEach
    void delete() {
        File file = new File(CSV_FILE_NAME);
        file.delete();
    }

}
