import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import taxiservice.Order;
import taxiservice.OrderProvider;
import taxiservice.TaxiService;
import taxiservice.simpleversion.SimpleDispatcher;
import taxiservice.simpleversion.SimpleTaxi;
import taxiservice.simpleversion.SimpleTaxiService;

import java.util.ArrayList;
import java.util.List;

public class SimpleTaxiServiceTest {

    SimpleDispatcher dispatcher;
    TaxiService service;
    TestOrderProvider testOrderProvider;

    @Before
    public void setUpService() {
        testOrderProvider = new TestOrderProvider();

        dispatcher
                = new SimpleDispatcher(testOrderProvider);

        service = new SimpleTaxiService(dispatcher);

        for (int taxiId = 0; taxiId < 10; ++taxiId) {
            service.addTaxi(new SimpleTaxi(taxiId, dispatcher));
        }
    }


    @Test
    public void AllOrdersCompletedTest() throws InterruptedException {
        service.startOperating();
        Thread.sleep(1000);
        service.stopOperating();

        // How to properly check completion while using graceful stop?
        // Just waiting works but the program is invalid
        Thread.sleep(1000);
        testOrderProvider.checkAllOrdersCompleted();
    }
}
