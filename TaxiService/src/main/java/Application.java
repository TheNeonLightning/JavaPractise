import taxi_service.TaxiService;
import taxi_service.simple_version.SimpleDispatcher;
import taxi_service.simple_version.SimpleOrderProvider;
import taxi_service.simple_version.SimpleTaxi;
import taxi_service.simple_version.SimpleTaxiService;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        SimpleDispatcher dispatcher
                = new SimpleDispatcher(new SimpleOrderProvider());
            // can't use Dispatcher interface here because need specific
            // method of realization
            // to add taxis to the dispatcher's queue (using method
            // inside service)

        TaxiService service = new SimpleTaxiService(dispatcher);

        for (int taxiId = 0; taxiId < 10; ++taxiId) {
            service.addTaxi(new SimpleTaxi(taxiId, dispatcher));
        }

        service.startOperating();
        Thread.sleep(100);
        service.stopOperating();
    }
}
