package org.data;

import net.datafaker.Faker;
import org.writer.model.Months;
import org.writer.model.Person;
import org.writer.model.Student;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс для генерации тестовых данных.
 */
public class DataGenerator {
    private static final Faker faker = new Faker();

    /**
     * Генерирует список студентов {@link Student} с указанным количеством.
     *
     * @param count количество студентов {@link Student} для генерации.
     * @return список сгенерированных студентов {@link Student}.
     */
    public static List<Student> generateStudents(int count) {
        String[] scores = {"unsatisfactory", "satisfactorily", "good", "great"};
        Random r = new Random();
        return IntStream.range(0, count)
                .mapToObj(i -> Student.builder()
                        .name(faker.name().fullName())
                        .score(IntStream.range(0, 10)
                                .mapToObj(j -> scores[r.nextInt(scores.length)])
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Генерирует список людей {@link Person} с указанным количеством элементов.
     *
     * @param count количество людей {@link Person} для генерации.
     * @return список сгенерированных людей {@link Person}.
     */
    public static List<Person> generatePersons(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> Person.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .dayOfBirth(faker.number().numberBetween(1, 20))
                        .monthOfBirth(Months.values()[faker.number().numberBetween(1, 12)])
                        .yearOfBirth(faker.number().numberBetween(1950, 2005))
                        .build())
                .collect(Collectors.toList());
    }
}
