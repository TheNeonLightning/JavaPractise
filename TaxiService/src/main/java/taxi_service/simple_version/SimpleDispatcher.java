package taxi_service.simple_version;

import taxi_service.Dispatcher;
import taxi_service.OrderProvider;
import taxi_service.Taxi;

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


    // Not using this method (notifying taxi in placeOrder() method of taxi)
    @Override
    public void notifyAvailable(Taxi taxi) {
        if (taxi == null) {
            throw new IllegalArgumentException("Notified taxi is a null pointer");
        }
        synchronized (taxi) {
            taxi.notify();
        }
    }

    @Override
    public synchronized void gracefulStop() {
        this.keepOperating = false;
        notify(); // notifying so would stop waiting for new taxis to arrive
                  // in getAvailableTaxi() method
    }

    public synchronized void addAvailableTaxi(Taxi taxi) {
        taxiQueue.add(taxi);
        notify();
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
