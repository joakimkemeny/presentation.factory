package se.joakimkemeny.demo.factory.factory;

public class FactoryQueueItem {

    int orderId;
    int s1;
    int s2;
    int a1;
    int a2;
    int a3;
    int b1;
    int b2;
    int b3;
    int b4;

    public FactoryQueueItem(int orderId, int quantity) {
        this.orderId = orderId;

        s1 = (int) (quantity * (Math.random() * 0.6 + 0.2));
        s2 = quantity - s1;

        a1 = (int) (s1 * Math.random() * 0.4);
        a2 = (int) (s1 * Math.random() * 0.4);
        a3 = s1 - a1 - a2;

        b1 = (int) (s2 * Math.random() * 0.3);
        b2 = (int) (s2 * Math.random() * 0.3);
        b3 = (int) (s2 * Math.random() * 0.3);
        b4 = s2 - b1 - b2 - b3;
    }

    public boolean isDone() {
        return s1 == 0 && s2 == 0 && a1 == 0 && a2 == 0 && a3 == 0 && b1 == 0 && b2 == 0 && b3 == 0 && b4 == 0;
    }

    @Override
    public String toString() {
        return "s1=" + s1 + "[" + a1 + "," + a2 + "," + a3 + "], s2=" + s2 + "[" + b1 + "," + b2 + "," + b3 + "," + b4 + "]";
    }
}
