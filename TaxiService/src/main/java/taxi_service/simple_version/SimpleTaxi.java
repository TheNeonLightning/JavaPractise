package taxi_service.simple_version;

import taxi_service.Order;
import taxi_service.Taxi;

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

        operating:
        while (keepOperating) {

            dispatcher.addAvailableTaxi(this);

            synchronized (this) {
                while (currentOrder == null) {
                    if (keepOperating) {
                        doWait();
                    } else {
                        break operating; // using label only to print
                                         // stopped taxi id in any stop scenario
                    }
                }
            }

            processOrder();
        }
        System.out.println("Taxi " + id + " stopped");
    }

    @Override
    public synchronized void placeOrder(Order order) {
        currentOrder = order;
        notify();
    }

    @Override
    public synchronized void gracefulStop() {
        this.keepOperating = false;
        notify(); // notifying so would stop waiting for new taxis to arrive
                  // in getAvailableTaxi() method
    }

    private void doWait() {
        try {
            wait();
        } catch (InterruptedException ignore) {
        }
    }

    private void processOrder() {
        currentOrder.processOrder();
        currentOrder = null;
    }
}
