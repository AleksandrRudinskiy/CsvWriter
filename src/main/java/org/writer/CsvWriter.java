package org.writer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 **Этот класс формирует отчет в файл в формате csv**
 **@author Александр Рудинский**
 **/
public class CsvWriter implements Writable {
    private final String FIELD_SEPARATOR = ";";
    /**
     * Метод создает csv файл с коллекцией данных
     * @param data -> коллекция данных для отчета
     * @param fileName -> имя файла отчета
     */
    @Override
    public void writeToFile(List<?> data, String fileName) {
        File csvOutputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(getHeaders(data.get(0)));
            for (Object item : data) {
                Class<?> itemsClass = item.getClass();
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    List<Field> allFields = Arrays.asList(itemsClass.getDeclaredFields());
                    Field firstField = allFields.get(0);
                    firstField.setAccessible(true);
                    stringBuilder.append(firstField.get(item));

                    for (int i = 1; i < allFields.size(); i++) {
                        stringBuilder.append(FIELD_SEPARATOR);
                        Field currentField = allFields.get(i);
                        currentField.setAccessible(true);
                        Object fieldValue = currentField.get(item);
                        stringBuilder.append(fieldValue);
                    }
                } catch (IllegalAccessException exception) {
                    System.out.println("Поле класса не прочитано!");
                }
                pw.println(stringBuilder);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private String getHeaders(Object item) {
        Class<?> itemsClass = item.getClass();
        List<Field> fields = Arrays.asList(itemsClass.getDeclaredFields());
        StringBuilder header = new StringBuilder();
        header.append(fields.get(0));
        for (int i = 1; i < fields.size(); i++) {
            header.append(FIELD_SEPARATOR);
            header.append(fields.get(i).getName());
        }
        return header.toString();
    }
}
