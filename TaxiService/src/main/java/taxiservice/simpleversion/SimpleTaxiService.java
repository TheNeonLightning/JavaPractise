package taxiservice.simpleversion;

import taxiservice.Taxi;
import taxiservice.TaxiService;

import java.util.ArrayList;
import java.util.List;

public class SimpleTaxiService implements TaxiService {
    private final SimpleDispatcher dispatcher;
    private final List<Taxi> taxis = new ArrayList<>();

    public SimpleTaxiService(SimpleDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void addTaxi(Taxi taxi) {
        taxis.add(taxi);
        dispatcher.notifyAvailable(taxi);
    }

    public void startOperating() {
        new Thread(dispatcher).start();

        for (Taxi taxi : taxis) {
            new Thread(taxi).start();
        }
    }

    public void stopOperating() {
        dispatcher.gracefulStop();

        for (Taxi taxi : taxis) {
            taxi.gracefulStop();
        }
    }
}
