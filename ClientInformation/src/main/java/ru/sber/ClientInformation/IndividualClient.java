package ru.sber.ClientInformation;

public class IndividualClient extends Client {

    private final String name;

    private final String surname;

    private final int inn;

    IndividualClient(String name, String surname, int inn) {
        clientType = ClientType.INDIVIDUAL;
        this.name = name;
        this.surname = surname;
        this.inn = inn;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getInn() {
        return inn;
    }
}
