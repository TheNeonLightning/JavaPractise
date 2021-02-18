package ru.sber.ClientInformation;

public class LegalEntityClient extends Client {

    private final String name;

    private final int inn;

    private final boolean isSanctioned;

    LegalEntityClient(String name, int inn, boolean isSanctioned) {
        clientType = ClientType.LEGAL_ENTITY;
        this.name = name;
        this.inn = inn;
        this.isSanctioned = isSanctioned;
    }

    public String getName() {
        return name;
    }

    public int getInn() {
        return inn;
    }

    public boolean isSanctioned() {
        return isSanctioned;
    }
}
