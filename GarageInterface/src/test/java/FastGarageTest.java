import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.sber.GarageInterface.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class FastGarageTest {

    FastGarage garage = new FastGarage();
    int size = 100;
    ArrayList<Car> cars = new ArrayList<>(size);
    ArrayList<Owner> owners = new ArrayList<>(size);

    @Before
    public void fillGarage() {

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
            cars.add(car);
            owners.add(owner);
            garage.addNewCar(car, owner);
        }
    }

    @Test
    public void testAllCarsUniqueOwners() {
        Set<Owner> result = garage.allCarsUniqueOwners();
        for (Owner owner: owners) {
            Assert.assertTrue(result.contains(owner));
        }
    }

    @Test
    public void testTopThreeCarsByMaxVelocity() {
        ArrayList<Car> topVelocityCars = garage.topThreeCarsByMaxVelocity();
        Assert.assertEquals(49, topVelocityCars.get(0).getMaxVelocity());
        Assert.assertEquals(49, topVelocityCars.get(1).getMaxVelocity());
        Assert.assertEquals(48, topVelocityCars.get(2).getMaxVelocity());
    }

    @Test
    public void testAllCarsOfBrand() {
        String brand = "Brand8";
        ArrayList<Car> brandCars = garage.allCarsOfBrand(brand);
        for (Car car: brandCars) {
            Assert.assertEquals(brand, car.getBrand());
        }
    }

    @Test
    public void testCarsWithPowerMoreThan() {
        Collection<Car> powerCars = garage.carsWithPowerMoreThan(20);
        for (Car car: powerCars) {
            Assert.assertTrue(car.getPower() > 20);
        }
        int powerCarsCounterExpected = 0;
        for (int index = 0; index < size; ++index) {
            if ((index % 30) > 20) {
                ++powerCarsCounterExpected;
            }
        }
        Assert.assertEquals(powerCars.size(), powerCarsCounterExpected);
    }

    @Test
    public void testAllCarsOfOwner() {
        Owner owner = owners.get(57);
        ArrayList<Car> ownerCars = garage.allCarsOfOwner(owner);
        for (Car car : ownerCars) {
            Assert.assertEquals(car.getOwnerId(), owner.getOwnerId());
        }
    }

    @Test
    public void testMeanOwnersAgeOfCarBrand() {
        String brand = "Brand2";
        int meanAge = garage.meanOwnersAgeOfCarBrand(brand);
        Assert.assertEquals(meanAge, 27);
    }

    @Test
    public void testMeanCarNumberForEachOwner() {
        int meanCarNumber = garage.meanCarNumberForEachOwner();
        Assert.assertEquals(meanCarNumber, size / 20);
    }

    @Test
    public void testAddAndRemoveCar() {

        // If all inserted cars are returned method AddNewCar() is correct
        for (Car car : cars) {
            Assert.assertEquals(garage.removeCar(car.getCarId()), car);
        }

        // If methods operating on cars are returning zero cars then
        // all cars have been removed from the garage
        Assert.assertEquals(garage.carsWithPowerMoreThan(0).size(), 0);
        Assert.assertEquals(garage.allCarsOfBrand("Brand0").size(), 0);
        Assert.assertEquals(garage.topThreeCarsByMaxVelocity().size(), 0);
        Assert.assertEquals(garage.allCarsOfOwner(owners.get(57)).size(), 0);
    }
}
