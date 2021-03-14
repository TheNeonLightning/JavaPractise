package ru.sber.ReportGenerator;

import ru.sber.GarageInterface.Garage;


public class GarageData {

    @FieldReportName("Кол-во владельцев")
    public int numberOfUniqueOwners;

    @FieldReportName("Сред. кол-во машин")
    public int meanCarNumberForEachOwner;

    @FieldReportName("Машины мощнее заданного")
    public int numberOfCarsWithPowerMoreThanGiven;

    @FieldReportName("Сред. возраст марки")
    public int meanOwnersAgeOfCarBrandGiven;

    GarageData(Garage garage, int power, String brand) {
        numberOfUniqueOwners = garage.allCarsUniqueOwners().size();
        meanCarNumberForEachOwner = garage.meanCarNumberForEachOwner();
        numberOfCarsWithPowerMoreThanGiven = garage.carsWithPowerMoreThan(power).size();
        meanOwnersAgeOfCarBrandGiven = garage.meanOwnersAgeOfCarBrand(brand);
    }

}
