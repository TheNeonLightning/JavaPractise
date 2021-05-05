package taxiservice;

public interface TaxiService {

    void startOperating();

    void stopOperating();

    void addTaxi(Taxi taxi);
}
