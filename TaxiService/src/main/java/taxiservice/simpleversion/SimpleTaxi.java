package taxiservice.simpleversion;

import taxiservice.Order;
import taxiservice.Taxi;

public class SimpleTaxi implements Taxi {

    private final int id;

    private Order currentOrder;
    private final SimpleDispatcher dispatcher;
    private volatile boolean keepOperating = true;

    public SimpleTaxi(int id, SimpleDispatcher dispatcher) {
        this.id = id;

        currentOrder = null;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {

        while (keepOperating) {
            dispatcher.notifyAvailable(this);

            synchronized (this) {
                while (currentOrder == null) {
                    if (keepOperating) {
                        doWait();
                    } else {
                        break;
                    }
                }
            }
            if (currentOrder != null) { // if currentOrder is null
                processOrder();         // then graceful stop was called
            }
        }
        System.out.println("Taxi " + id + " stopped");
    }

    @Override
    public synchronized void placeOrder(Order order) {
        currentOrder = order;
        notifyAll();
    }

    @Override
    public synchronized void gracefulStop() {
        this.keepOperating = false;
        notifyAll(); // notifying so would stop waiting for new taxis to arrive
                     // in getAvailableTaxi() method
    }

    private void doWait() {
        try {
            wait();
        } catch (InterruptedException ignore) {
        }
    }

    private synchronized void processOrder() {
        currentOrder.processOrder();
        currentOrder = null;
    }
}
