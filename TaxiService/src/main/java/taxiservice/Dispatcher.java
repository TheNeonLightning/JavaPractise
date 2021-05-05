package taxiservice;

public interface Dispatcher extends Runnable {
    void notifyAvailable(Taxi taxi);

    void run();

    // Added graceful stop method to the interface as I think it is general
    // to any realization
    void gracefulStop();
}