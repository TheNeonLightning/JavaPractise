import taxiservice.TaxiService;
import taxiservice.simpleversion.SimpleDispatcher;
import taxiservice.simpleversion.SimpleOrderProvider;
import taxiservice.simpleversion.SimpleTaxi;
import taxiservice.simpleversion.SimpleTaxiService;

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
