import org.junit.Assert;
import taxiservice.Order;
import taxiservice.OrderProvider;

import java.util.ArrayList;
import java.util.List;

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