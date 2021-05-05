package taxiservice.simpleversion;

import taxiservice.Order;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleOrder implements Order {

    private final int id;
    private final long millis;

    SimpleOrder(int id) {
        this.id = id;
        millis = ThreadLocalRandom.current().nextLong(0, 100);
    }

    @Override
    public void processOrder() {
        try {
            Thread.sleep(millis);
            System.out.println("Order " + id + " is processed");
        } catch (InterruptedException ignore) {
        }
    }
}
