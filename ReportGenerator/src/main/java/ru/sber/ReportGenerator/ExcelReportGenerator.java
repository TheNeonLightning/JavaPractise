package ru.sber.ReportGenerator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;


public class ExcelReportGenerator<T> implements ReportGenerator<T> {

    @Override
    public ExcelReport generate(List<T> entities) {

        Class<?> clazz;
        if (!entities.isEmpty()) {
            clazz = entities.get(0).getClass();
        } else {
            throw new IllegalArgumentException("Empty list");
        }

        Workbook workbook = new XSSFWorkbook();
        int entityIndex = 0;
        for (T entity : entities) {
            String sheetName = clazz.getName() + entityIndex++;
            generateSheet(workbook, sheetName, entity);
        }

        return new ExcelReport(workbook);
    }

    private void generateSheet(Workbook workbook, String sheetName, T entity) {

        Sheet sheet = workbook.createSheet(sheetName);

        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Row fieldName = sheet.createRow(0);
        Row fieldValue = sheet.createRow(1);

        int column = 0;
        for (Field field : fields) {
            field.setAccessible(true);

            Cell fieldNameCell = fieldName.createCell(column);
            String name = field.getName();
            if (field.isAnnotationPresent(FieldReportName.class)) {
                FieldReportName reportName =
                        field.getAnnotation(FieldReportName.class);
                name = reportName.value();
            }
            fieldNameCell.setCellValue(name);

            Cell fieldValueCell = fieldValue.createCell(column++);
            try {
                fieldValueCell.setCellValue(field.get(entity).toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Could not access entity field", e);
            }
        }
    }
}
