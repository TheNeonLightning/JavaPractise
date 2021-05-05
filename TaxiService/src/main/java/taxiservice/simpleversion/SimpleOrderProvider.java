package taxiservice.simpleversion;

import taxiservice.OrderProvider;

public class SimpleOrderProvider implements OrderProvider {

    private int orderId = 0;

    @Override
    public SimpleOrder provideOrder() {
        return new SimpleOrder(orderId++);
    }
}
