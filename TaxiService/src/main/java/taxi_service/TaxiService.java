package taxi_service;

public interface TaxiService {

    void startOperating();

    void stopOperating();

    void addTaxi(Taxi taxi);
}
