package se.joakimkemeny.demo.factory.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.joakimkemeny.demo.factory.domain.Order;
import se.joakimkemeny.demo.factory.websocket.WebSocketManager;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderManager {

    @Autowired
    private WebSocketManager webSocketManager;

    private List<Order> orders = new ArrayList<Order>();
    private int orderId;

    @PostConstruct
    protected void setupOrders() {
        orders.add(new Order(++orderId, "Anna Svensson", 100));
        orders.add(new Order(++orderId, "Jonas Karlsson", 150));
    }

    public List<Order> listOrders() {
        return orders;
    }

    public Order getOrder(Integer orderId) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public Order createOrder(Order newOrder) {
        newOrder.setId(++orderId);
        orders.add(newOrder);
        webSocketManager.broadcast("order", "added", newOrder);
        return newOrder;
    }

    public Order updateOrder(Integer orderId, Order updatedOrder) {
        Order order = getOrder(orderId);
        if (order != null) {
            order.setCustomer(updatedOrder.getCustomer());
            order.setQuantity(updatedOrder.getQuantity());
            order.setStatus(updatedOrder.getStatus());
            webSocketManager.broadcast("order", "updated", order);
        }
        return order;
    }

    public void deleteOrder(Integer orderId) {
        Order order = getOrder(orderId);
        orders.remove(order);
        webSocketManager.broadcast("order", "deleted", order);
    }

    public Order changeStatus(Integer orderId, Order.OrderStatus status) {
        Order order = getOrder(orderId);
        if (order != null) {
            order.setStatus(status);
            webSocketManager.broadcast("order", "updated", order);
        }
        return order;
    }
}
