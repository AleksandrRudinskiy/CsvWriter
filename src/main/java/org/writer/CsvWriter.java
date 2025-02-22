package org.writer;

import org.exception.EmptyDataException;

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
                        firstField.setAccessible(true);
                        makeLine(stringBuilder, firstField, item);
                    }
                    for (int i = 1; i < allFields.size(); i++) {
                        stringBuilder.append(FIELD_SEPARATOR);
                        Field currentField = allFields.get(i);
                        if (currentField != null) {
                            currentField.setAccessible(true);
                            makeLine(stringBuilder, currentField, item);
                        }
                    }
                    pw.println(stringBuilder);
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
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
        String line1 = line.replaceAll(",", "");
        String line2 = line1.replaceAll("\\[", "");
        return line2.replaceAll("]", "");
    }

    private void makeLine(StringBuilder stringBuilder, Field field, Object item) {
        try {
            if (isTypeList(field)) {
                stringBuilder.append(listToString(field.get(item).toString()));
            } else {
                stringBuilder.append(field.get(item));
            }
        } catch (IllegalAccessException exception) {
            System.out.println("Поле класса не прочитано!");
        }
    }
}
