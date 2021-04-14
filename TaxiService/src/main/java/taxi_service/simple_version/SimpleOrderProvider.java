package taxi_service.simple_version;

import taxi_service.Order;
import taxi_service.OrderProvider;

public class SimpleOrderProvider implements OrderProvider {

    private int orderId = 0;

    @Override
    public SimpleOrder provideOrder() {
        return new SimpleOrder(orderId++);
    }
}
