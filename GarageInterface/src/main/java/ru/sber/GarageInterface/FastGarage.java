package ru.sber.GarageInterface;


import java.util.*;

public class FastGarage implements Garage {

    private final HashMap<Integer, Car> carIdMap = new HashMap<>();

    private final HashMap<Integer, Owner> ownerIdMap = new HashMap<>();

    private final HashMap<String, ArrayList<Car>> brandCarsMap = new HashMap<>();

    private final HashMap<Owner, ArrayList<Car>> ownerCarsMap = new HashMap<>();

    private final TreeMap<CarParam, Car> powerCarTreeSet = new TreeMap<>();

    private final TreeMap<CarParam, Car> velocityCarTreeSet = new TreeMap<>();

    private int minId = -1;

    static class CarParam implements Comparable<CarParam> {
        private final int id;
        private final int param;

        CarParam(int id, int param) {
            this.id = id;
            this.param = param;
        }

        @Override
        public int compareTo(CarParam o) {
            if (o.param > param) {
                return 1;
            } else if (o.param < param) {
                return -1;
            } else return Integer.compare(o.id, id);
        }
    }

    @Override
    public Set<Owner> allCarsUniqueOwners() {
        return ownerCarsMap.keySet();
    }

    @Override
    public ArrayList<Car> topThreeCarsByMaxVelocity() {
        int resultSize = 3;
        int count = 0;
        ArrayList<Car> result = new ArrayList<>(resultSize);
        for (Map.Entry<CarParam, Car> entry : velocityCarTreeSet.entrySet()) {
            if (resultSize == count) break;

            result.add(entry.getValue());
            ++count;
        }
        return result;
    }

    @Override
    public ArrayList<Car> allCarsOfBrand(String brand) {
        return brandCarsMap.get(brand);
    }

    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        // Incrementing power so can use the same class CarParam as in
        // topThreeCarsByMaxVelocity() method. This way the result won't be
        // inclusive. (Can not specify it in the TreeMap method because
        // CarParam classes can not be equal if carId are not equal, and
        // this behaviour is used in topThreeCarsByMaxVelocity() method).
        return powerCarTreeSet.headMap(new CarParam(minId, ++power)).values();
    }

    @Override
    public ArrayList<Car> allCarsOfOwner(Owner owner) {
        return ownerCarsMap.get(owner);
    }

    /**
     * Have to return int by the contract. If one owner has two cars of
     * the same brand their age will be counted twice (I am assuming this is the
     * expected behaviour).
     */
    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        int ownerNumber = brandCarsMap.get(brand).size();
        int ownerAgeSum = 0;
        for (Car car: brandCarsMap.get(brand)) {
            ownerAgeSum += ownerIdMap.get(car.getOwnerId()).getAge();
        }
        return ownerAgeSum / ownerNumber;
    }

    /**
     * Have to return int by the contract.
     */
    @Override
    public int meanCarNumberForEachOwner() {
        int ownerNumber = ownerIdMap.size();
        int ownerCarNumberSum = 0;
        for (List<Car> cars: ownerCarsMap.values()) {
            ownerCarNumberSum += cars.size();
        }
        return ownerCarNumberSum / ownerNumber;
    }


    @Override
    public Car removeCar(int carId) {
        Car removedCar = carIdMap.get(carId);
        carIdMap.remove(carId);

        String brand = removedCar.getBrand();
        brandCarsMap.get(brand).remove(removedCar);

        Integer ownerId = removedCar.getOwnerId();
        Owner owner = ownerIdMap.get(ownerId);

        ownerCarsMap.get(owner).remove(removedCar);

        int power = removedCar.getPower();
        CarParam carPower = new CarParam(carId, power);
        powerCarTreeSet.remove(carPower);

        int velocity = removedCar.getMaxVelocity();
        CarParam carVelocity = new CarParam(carId, velocity);
        velocityCarTreeSet.remove(carVelocity);

        return removedCar;
    }

    @Override
    public void addNewCar(Car car, Owner owner) {
        int carId = car.getCarId();
        if (carIdMap.containsKey(carId)) {
            throw new IllegalArgumentException("Garage already contains car " +
                    "with the same id " + carId);
        } else {
            carIdMap.put(carId, car);
        }

        if (minId > carId) {
            minId = carId - 1;
        }
        int power = car.getPower();
        CarParam carPower = new CarParam(carId, power);
        powerCarTreeSet.put(carPower, car);

        int velocity = car.getMaxVelocity();
        CarParam carVelocity = new CarParam(carId, velocity);
        velocityCarTreeSet.put(carVelocity, car);

        Integer ownerId = owner.getOwnerId();
        if (!ownerIdMap.containsKey(ownerId)) {
            ownerIdMap.put(ownerId, owner);
        }

        String brand = car.getBrand();
        if (brandCarsMap.containsKey(brand)) {
            brandCarsMap.get(brand).add(car);
        } else {
            brandCarsMap.put(brand, new ArrayList<>() {{ add(car);}});
        }

        if (ownerCarsMap.containsKey(owner)) {
            ownerCarsMap.get(owner).add(car);
        } else {
            ownerCarsMap.put(owner, new ArrayList<>() {{ add(car);}});
        }
    }
}
