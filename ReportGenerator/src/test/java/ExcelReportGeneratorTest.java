import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import ru.sber.ReportGenerator.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ExcelReportGeneratorTest {

    @Test
    public void generateReportValuesTest() {
        Application.main(null);
        try (FileInputStream file =
                     new FileInputStream(new File("result.xlsx"))) {

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Assert.assertEquals(
                    row.getCell(0).getRichStringCellValue().getString(),
                    "20");
            Assert.assertEquals(
                    row.getCell(1).getRichStringCellValue().getString(),
                    "5");
            Assert.assertEquals(
                    row.getCell(2).getRichStringCellValue().getString(),
                    "27");
            Assert.assertEquals(
                    row.getCell(3).getRichStringCellValue().getString(),
                    "27");

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find result file", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not read from result file", e);
        }
    }

    @Test
    public void generateReportNamingTest() {
        Application.main(null);
        try (FileInputStream file =
                     new FileInputStream(new File("result.xlsx"))) {

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            Assert.assertEquals(
                    row.getCell(0).getRichStringCellValue().getString(),
                    "Кол-во владельцев");
            Assert.assertEquals(
                    row.getCell(1).getRichStringCellValue().getString(),
                    "Сред. кол-во машин");
            Assert.assertEquals(
                    row.getCell(2).getRichStringCellValue().getString(),
                    "Машины мощнее заданного");
            Assert.assertEquals(
                    row.getCell(3).getRichStringCellValue().getString(),
                    "Сред. возраст марки");

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find result file", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not read from result file", e);
        }
    }
}
