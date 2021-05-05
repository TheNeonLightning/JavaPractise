package taxiservice.simpleversion;

import taxiservice.Dispatcher;
import taxiservice.OrderProvider;
import taxiservice.Taxi;

import java.util.ArrayDeque;
import java.util.Queue;

public class SimpleDispatcher implements Dispatcher {

    private volatile boolean keepOperating = true;
    private final Queue<Taxi> taxiQueue = new ArrayDeque<>();
    private final OrderProvider orderProvider;

    public SimpleDispatcher(OrderProvider orderProvider) {
        this.orderProvider = orderProvider;
    }

    @Override
    public void run() {
        while (keepOperating) {
            Taxi currentTaxi = getAvailableTaxi();
            if (currentTaxi == null) {
                break;
            }
            currentTaxi.placeOrder(orderProvider.provideOrder());
        }
        System.out.println("Dispatcher stopped");
    }

    @Override
    public synchronized void notifyAvailable(Taxi taxi) {
        taxiQueue.add(taxi);
        notifyAll();
    }

    @Override
    public synchronized void gracefulStop() {
        this.keepOperating = false;
        notifyAll(); // notifying so would stop waiting for new taxis to arrive
                     // in getAvailableTaxi() method
    }

    /**
     * Returns first taxi from the available taxi queue taxiQueue.
     * If graceful stop was called will return null pointer.
     */
    private synchronized Taxi getAvailableTaxi() {
        while (taxiQueue.isEmpty()) {
            if (keepOperating) {
                doWait();
            } else {
                return null;
            }
        }
        return taxiQueue.remove();
    }

    private void doWait() {
        try {
            wait();
        } catch (InterruptedException ignore) {
        }
    }
}
