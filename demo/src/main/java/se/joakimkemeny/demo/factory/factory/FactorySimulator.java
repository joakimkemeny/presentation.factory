package se.joakimkemeny.demo.factory.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.joakimkemeny.demo.factory.domain.Order;
import se.joakimkemeny.demo.factory.domain.Scale;
import se.joakimkemeny.demo.factory.domain.Silo;
import se.joakimkemeny.demo.factory.manager.OrderManager;
import se.joakimkemeny.demo.factory.manager.ScaleManager;
import se.joakimkemeny.demo.factory.manager.SiloManager;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class FactorySimulator implements Runnable, Factory {

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private ScaleManager scaleManager;
    @Autowired
    private SiloManager siloManager;

    private final BlockingQueue<FactoryQueueItem> orderQueue = new LinkedBlockingQueue<FactoryQueueItem>();

    public FactorySimulator() {
        new Thread(this).start();
    }

    @Override
    public void queueOrder(Order order) {
        try {
            orderQueue.put(new FactoryQueueItem(order.getId(), order.getQuantity()));
            orderManager.changeStatus(order.getId(), Order.OrderStatus.Queued);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                produce(orderQueue.take());
            }
        } catch (InterruptedException e) {
        }
    }

    void produce(FactoryQueueItem item) {

        orderManager.changeStatus(item.orderId, Order.OrderStatus.Producing);

        List<Scale> scales = scaleManager.listScales();
        Scale scale1 = scales.get(0);
        Scale scale2 = scales.get(1);

        scaleManager.updateScale(scale1.getName(), 0, item.s1, false, scale1.isActive());
        scaleManager.updateScale(scale2.getName(), 0, item.s2, false, scale2.isActive());

        List<Silo> silos = siloManager.listSilos();

        while (!item.isDone()) {

            if (scale1.isActive()) {
                if (item.a1 > 0) {
                    item.a1 -= 1;
                    updateSilo(silos.get(0), -1, item.a1 != 0);
                    updateScale(scale1, 1, false);
                } else if (item.a2 > 0) {
                    item.a2 -= 1;
                    updateSilo(silos.get(1), -1, item.a2 != 0);
                    updateScale(scale1, 1, false);
                } else if (item.a3 > 0) {
                    item.a3 -= 1;
                    updateSilo(silos.get(2), -1, item.a3 != 0);
                    updateScale(scale1, 1, false);
                } else if (item.s1 > 0) {
                    item.s1 -= 1;
                    updateScale(scale1, -1, item.s1 != 0);
                }
            }

            if (scale2.isActive()) {
                if (item.b1 > 0) {
                    item.b1 -= 1;
                    updateSilo(silos.get(3), -1, item.b1 != 0);
                    updateScale(scale2, 1, false);
                } else if (item.b2 > 0) {
                    item.b2 -= 1;
                    updateSilo(silos.get(4), -1, item.b2 != 0);
                    updateScale(scale2, 1, false);
                } else if (item.b3 > 0) {
                    item.b3 -= 1;
                    updateSilo(silos.get(5), -1, item.b3 != 0);
                    updateScale(scale2, 1, false);
                } else if (item.b4 > 0) {
                    item.b4 -= 1;
                    updateSilo(silos.get(6), -1, item.b4 == 0);
                    updateScale(scale2, 1, false);
                } else if (item.s2 > 0) {
                    item.s2 -= 1;
                    updateScale(scale2, -1, item.s2 != 0);
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            if (!item.isDone()) {
                scales = scaleManager.listScales();
                silos = siloManager.listSilos();
                scale1 = scales.get(0);
                scale2 = scales.get(1);
            }
        }

        orderManager.changeStatus(item.orderId, Order.OrderStatus.Produced);
    }

    private void updateScale(Scale scale, int increment, boolean emptying) {
        int current = scale.getCurrent() + increment;
        scaleManager.updateScale(scale.getName(), current, scale.getTarget(), emptying, scale.isActive());
    }

    private void updateSilo(Silo silo, int increment, boolean active) {
        int level = silo.getLevel() + increment;
        if (level < 0) {
            level += silo.getCapacity();
        }
        siloManager.updateSilo(silo.getName(), level, active);
    }
}
