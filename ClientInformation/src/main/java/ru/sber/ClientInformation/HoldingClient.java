package ru.sber.ClientInformation;

public class HoldingClient extends Client {

    private final String name;

    private final int inn;

    private final boolean isSanctioned;

    private final int authorizedCapital;

    private final int regNum;

    HoldingClient(String name, int inn, boolean isSanctioned,
                  int authorizedCapital, int regNum) {
        clientType = ClientType.HOLDING;
        this.name = name;
        this.inn = inn;
        this.isSanctioned = isSanctioned;
        this.authorizedCapital = authorizedCapital;
        this.regNum = regNum;
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

    public int getAuthorizedCapital() {
        return authorizedCapital;
    }

    public int getRegNum() {
        return regNum;
    }
}
