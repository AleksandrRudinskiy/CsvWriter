package org.writer;

import org.data.DataGenerator;
import org.exception.EmptyDataException;
import org.exception.NullFieldException;
import org.exception.NullFileNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reader.StudentsReader;
import org.writer.model.Student;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvWriterStudentsTest {
    private String STUDENTS_CSV_FILE_NAME;

    @BeforeEach
    void start() {
        STUDENTS_CSV_FILE_NAME = "students_data.csv";
    }

    @Test
    void whenReaderFileNameIsNull_thenExceptionThrown() {
        int count = 20;
        List<Student> students = DataGenerator.generateStudents(count);
        Writable writer = new CsvWriter();
        writer.writeToFile(students, STUDENTS_CSV_FILE_NAME);
        StudentsReader sr = new StudentsReader();
        Exception exception = assertThrows(NullFileNameException.class, () -> {
            sr.readFromCsv(null);
        });
        String expectedMessage = "Имя файла не должно быть null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void sizeListStudentFromFile_MustBeEqual_GeneratedSizeList() {
        File csvOutputFile = new File(STUDENTS_CSV_FILE_NAME);
        int count = 20;
        List<Student> students = DataGenerator.generateStudents(count);
        Writable writer = new CsvWriter();
        writer.writeToFile(students, STUDENTS_CSV_FILE_NAME);
        StudentsReader sr = new StudentsReader();
        List<Student> studentsFromCsv = sr.readFromCsv(STUDENTS_CSV_FILE_NAME);
        int expectedCount = 20;
        assertTrue(csvOutputFile.exists(), "Файл " + STUDENTS_CSV_FILE_NAME + " должен быть создан!");
        assertEquals(expectedCount,
                studentsFromCsv.size(),
                "Количество студентов в файле должно быть " + expectedCount + " !");
    }

    @Test
    void whenNameIsNull_thenExceptionThrown() {
        Student student = Student.builder()
                .name(null)
                .score(List.of())
                .build();
        List<Student> students = List.of(student);
        Writable writer = new CsvWriter();
        Exception exception = assertThrows(NullFieldException.class, () -> {
            writer.writeToFile(students, STUDENTS_CSV_FILE_NAME);
        });
        String expectedMessage = "Значение поля не должно быть null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenScoresIsNull_thenExceptionThrown() {
        Student student = Student.builder()
                .name("Vasilii")
                .score(null)
                .build();
        List<Student> students = List.of(student);
        Writable writer = new CsvWriter();
        Exception exception = assertThrows(NullFieldException.class, () -> {
            writer.writeToFile(students, STUDENTS_CSV_FILE_NAME);
        });
        String expectedMessage = "Значение поля не должно быть null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionWhenEmptyData() {
        Exception exception = assertThrows(EmptyDataException.class, () -> {
            Writable writer = new CsvWriter();
            writer.writeToFile(new ArrayList<>(), STUDENTS_CSV_FILE_NAME);
        });
        String expectedMessage = "Данные не должны быть пустыми!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
