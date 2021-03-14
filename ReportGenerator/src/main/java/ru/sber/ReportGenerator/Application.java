package ru.sber.ReportGenerator;

import ru.sber.GarageInterface.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;


public class Application {

    public static void fillGarage(Garage garage, int size) {

        for (int index = 0; index < size; ++index) {
            Car car = new Car(index,
                    "Brand" + index % 10,
                    "model",
                    index % 50,
                    index % 30,
                    index % 20);

            Owner owner = new Owner("name",
                    "lastName",
                    index % 20 + 20,
                    index % 20);
            garage.addNewCar(car, owner);
        }
    }

    public static void main(String[] args) {

        int size = 100;
        FastGarage garage = new FastGarage();
        fillGarage(garage, size);
        GarageData data = new GarageData(garage, 20, "Brand2");

        ReportGenerator<GarageData> reportGen = new ExcelReportGenerator<>();
        Report report = reportGen.generate(Collections.singletonList(data));
        File resultFile = new File("result.xlsx");


        try (FileOutputStream fos = new FileOutputStream(resultFile)) {

            report.writeTo(fos);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find file", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file", e);
        }
    }
}
