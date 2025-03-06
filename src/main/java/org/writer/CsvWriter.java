package org.writer;

import lombok.extern.slf4j.Slf4j;
import org.exception.EmptyDataException;
import org.exception.NullFieldException;
import org.writer.model.CSVField;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * *Этот класс формирует отчет в файл в формате csv**
 * *@author Александр Рудинский**
 **/
@Slf4j
public class CsvWriter implements Writable {
    private final static String FIELD_SEPARATOR = ";";

    /**
     * Метод создает csv файл с коллекцией данных
     *
     * @param data     -> коллекция данных для отчета
     * @param fileName -> имя файла отчета
     */
    @Override
    public void writeToFile(List<?> data, String fileName) {
        if (!data.isEmpty()) {
            File csvOutputFile = new File(fileName);
            try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                pw.println(getHeaders(data.get(0)));
                for (Object item : data) {
                    Class<?> itemsClass = item.getClass();
                    StringBuilder stringBuilder = new StringBuilder();
                    List<Field> allFields = Arrays.asList(itemsClass.getDeclaredFields());
                    Field firstField = allFields.get(0);
                    if (firstField != null) {
                        if (firstField.isAnnotationPresent(CSVField.class)) {
                            firstField.setAccessible(true);
                            log.info("value of the first field is: {}", firstField.get(item));
                            makeLine(stringBuilder, firstField, item);
                        }
                    }
                    for (int i = 1; i < allFields.size(); i++) {
                        stringBuilder.append(FIELD_SEPARATOR);
                        Field currentField = allFields.get(i);
                        if (currentField != null) {
                            if (currentField.isAnnotationPresent(CSVField.class)) {
                                currentField.setAccessible(true);
                                makeLine(stringBuilder, currentField, item);
                            }
                        }
                    }
                    pw.println(stringBuilder);
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new EmptyDataException("Данные не должны быть пустыми!");
        }
    }

    private String getHeaders(Object item) {
        Class<?> itemsClass = item.getClass();
        List<Field> fields = Arrays.asList(itemsClass.getDeclaredFields());
        StringBuilder header = new StringBuilder();
        header.append(fields.get(0).getName());
        for (int i = 1; i < fields.size(); i++) {
            header.append(FIELD_SEPARATOR);
            header.append(fields.get(i).getName());
        }
        return header.toString();
    }

    private boolean isTypeList(Field field) {
        return field.getType().getName().equals("java.util.List");
    }

    private String listToString(String line) {

        return line.replaceAll("\\[", "")
                .replaceAll("]", "");
    }

    private void makeLine(StringBuilder stringBuilder, Field field, Object item) {
        try {
            if (field.get(item) == null) {
                throw new NullFieldException("Значение поля не должно быть null!");
            }
            if (isTypeList(field)) {
                stringBuilder.append(listToString(field.get(item).toString()));
            } else {
                check(field, item);
                stringBuilder.append(field.get(item));
            }
        } catch (IllegalAccessException exception) {
            System.out.println("Поле класса не прочитано!");
        }
    }

    private void check(Field field, Object o) throws IllegalAccessException {
        Object value = field.get(o);
        if (value == null) {
            throw new NullFieldException("Значение поля не должно быть null!");
        }
    }
}
