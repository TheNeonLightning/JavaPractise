package taxiservice;

public interface Taxi extends Runnable {
    void run();

    void placeOrder(Order order);

    // Added graceful stop method to the interface as I think it is general
    // to any realization
    void gracefulStop();
}
