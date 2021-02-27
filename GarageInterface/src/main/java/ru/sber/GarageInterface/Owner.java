package ru.sber.GarageInterface;


import java.util.Objects;

public class Owner {

    private final String name;

    private final String lastName;

    private final int age;

    private final int ownerId;

    public Owner(String name, String lastName, int age, int ownerId) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;

        Owner owner = (Owner) o;

        if (age != owner.age) return false;
        if (ownerId != owner.ownerId) return false;
        if (!Objects.equals(name, owner.name))
            return false;
        return Objects.equals(lastName, owner.lastName);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + ownerId;
        return result;
    }
}
