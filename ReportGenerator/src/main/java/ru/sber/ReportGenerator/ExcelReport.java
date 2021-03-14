package ru.sber.ReportGenerator;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelReport implements Report {

    private final Workbook workbook;

    ExcelReport(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public byte[] asBytes() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Could not convert to byte array", e);
        }
    }

    @Override
    public void writeTo(OutputStream os) {
        try {

            workbook.write(os);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not write to given output stream", e);
        }
    }
}
