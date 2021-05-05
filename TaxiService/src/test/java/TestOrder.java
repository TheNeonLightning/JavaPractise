import taxiservice.Order;

class TestOrder implements Order {

    private final int id;
    private boolean isCompleted = false;

    TestOrder(int id) {
        this.id = id;
    }

    @Override
    public void processOrder() {
        isCompleted = true;
    }

    boolean getIsCompleted() {
        return isCompleted;
    }
}