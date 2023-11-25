package com.app.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class ExcelToJavaMapper {
    public static <T> T mapExcelRowToObject(XSSFRow row, Class<T> clazz) {
        Map<Integer, String> columnMapping = mapColumnsToFields(row.getSheet().getRow(0));
        T object = null;

        try {
            object = clazz.newInstance();

            for (Map.Entry<Integer, String> entry : columnMapping.entrySet()) {
                Integer columnIndex = entry.getKey();
                String fieldName = entry.getValue();
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object cellValue = getValueFromCell(row.getCell(columnIndex), field.getType());
                    field.set(object, cellValue);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    System.out.println("Field " + fieldName + " not found in " + clazz.getSimpleName() + " class.");
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return object;
    }

    private static Map<Integer, String> mapColumnsToFields(Row headerRow) {
        Map<Integer, String> columnMapping = new HashMap<>();
        for (int cellIndex = 0; cellIndex < headerRow.getPhysicalNumberOfCells(); cellIndex++) {
            Cell cell = headerRow.getCell(cellIndex);
            String columnName = cell.getStringCellValue();
            columnMapping.put(cellIndex, columnName);
        }
        return columnMapping;
    }

    private static Object getValueFromCell(Cell cell, Class<?> fieldType) {
        if (cell != null) {
            if (fieldType == String.class) {
                System.out.println("String: " + cell.getStringCellValue());
                return cell.getStringCellValue();
            } else if (fieldType == int.class || fieldType == Integer.class) {
                System.out.println("Integer: " + cell.getNumericCellValue());
                return (int) cell.getNumericCellValue();
            } else if (fieldType == float.class || fieldType == Float.class) {
                System.out.println("Float: " + (float) cell.getNumericCellValue());
                return (float) cell.getNumericCellValue();
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                System.out.println("Boolean: " + cell.getBooleanCellValue());
                return cell.getBooleanCellValue();
            } else if (fieldType == LocalDateTime.class) {
                Date dateCellValue = cell.getDateCellValue();
                System.out.println("LocalDateTime: " + dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                return dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
        }
        return null;
    }
}

