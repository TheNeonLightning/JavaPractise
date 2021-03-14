import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import ru.sber.ReportGenerator.ExcelReport;
import ru.sber.ReportGenerator.ExcelReportGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;


public class ExcelReportTest {

    static class LocalTestData {
        final int integer = 0;
        final String str = "test text";
        final Double dbl = 1.2;
    }

    public void checkReport() {
        try (FileInputStream file =
                     new FileInputStream("test_file.xlsx")) {

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Assert.assertEquals(
                    row.getCell(0).getRichStringCellValue().getString(),
                    "0");
            Assert.assertEquals(
                    row.getCell(1).getRichStringCellValue().getString(),
                    "test text");
            Assert.assertEquals(
                    row.getCell(2).getRichStringCellValue().getString(),
                    "1.2");

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find result file", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not read from result file", e);
        }
    }

    @Test
    public void asBytesTest() {

        ExcelReportGenerator<LocalTestData > reportGenerator =
                new ExcelReportGenerator<>();

        ExcelReport report =
                reportGenerator.generate(Collections.
                        singletonList(new LocalTestData()));

        byte[] result = report.asBytes();
        try (FileOutputStream fos = new FileOutputStream("test_file.xlsx")) {

            fos.write(result);

            checkReport();

            Path fileToDeletePath = Paths.get("test_file.xlsx");
            Files.delete(fileToDeletePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file", e);
        }
    }

}
