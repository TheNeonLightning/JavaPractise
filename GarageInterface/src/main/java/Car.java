import java.util.Objects;

public class Car {

    private final int carId;

    private final String brand;

    private final String modelName;

    private final int maxVelocity;

    private final int power;

    private final int ownerId;

    public Car(int carId, String brand, String modelName,
               int maxVelocity, int power, int ownerId) {
        this.carId = carId;
        this.brand = brand;
        this.modelName = modelName;
        this.maxVelocity = maxVelocity;
        this.power = power;
        this.ownerId = ownerId;
    }

    public int getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public int getPower() {
        return power;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        if (carId != car.carId) return false;
        if (maxVelocity != car.maxVelocity) return false;
        if (power != car.power) return false;
        if (ownerId != car.ownerId) return false;
        if (!Objects.equals(brand, car.brand))
            return false;
        return Objects.equals(modelName, car.modelName);
    }

    @Override
    public int hashCode() {
        int result = carId;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + maxVelocity;
        result = 31 * result + power;
        result = 31 * result + ownerId;
        return result;
    }
}
