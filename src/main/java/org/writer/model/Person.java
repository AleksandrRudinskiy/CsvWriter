package org.writer.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class Person {
    @CsvBindByPosition(position = 0)
    private String firstName;
    @CsvBindByPosition(position = 1)
    private String lastName;
    @CsvBindByPosition(position = 2)
    private int dayOfBirth;
    @CsvBindByPosition(position = 3)
    private Months monthOfBirth;
    @CsvBindByPosition(position = 4)
    private int yearOfBirth;
}
