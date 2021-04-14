import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import taxi_service.Order;
import taxi_service.OrderProvider;
import taxi_service.TaxiService;
import taxi_service.simple_version.SimpleDispatcher;
import taxi_service.simple_version.SimpleTaxi;
import taxi_service.simple_version.SimpleTaxiService;

import java.util.ArrayList;
import java.util.List;

class TestOrder implements Order {

    private final int id;
    private boolean isCompleted = false;

    TestOrder(int id) {
        this.id = id;
    }

    @Override
    public void processOrder() {
        isCompleted = true;
    }

    boolean getIsCompleted() {
        return isCompleted;
    }
}

class TestOrderProvider implements OrderProvider {

    int orderId = 0;
    List<TestOrder> orders = new ArrayList<>();

    @Override
    public Order provideOrder() {
        return new TestOrder(orderId++);
    }

    public void checkAllOrdersCompleted() {
        for (TestOrder order : orders) {
            Assert.assertTrue(order.getIsCompleted());
        }
    }
}

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
        // Just waiting works but is the program is invalid
        Thread.sleep(1000);
        testOrderProvider.checkAllOrdersCompleted();
    }
}
