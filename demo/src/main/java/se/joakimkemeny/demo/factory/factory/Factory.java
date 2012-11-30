package se.joakimkemeny.demo.factory.factory;

import se.joakimkemeny.demo.factory.domain.Order;

public interface Factory {

    void queueOrder(Order order);
}
