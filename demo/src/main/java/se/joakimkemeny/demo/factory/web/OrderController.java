package se.joakimkemeny.demo.factory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.joakimkemeny.demo.factory.domain.Order;
import se.joakimkemeny.demo.factory.factory.Factory;
import se.joakimkemeny.demo.factory.manager.OrderManager;
import se.joakimkemeny.demo.factory.websocket.WebSocketController;
import se.joakimkemeny.demo.factory.websocket.WebSocketMapping;

import java.util.List;

@WebSocketController("order")
@Controller
@RequestMapping(produces = "application/json")
public class OrderController {

    @Autowired
    private Factory factory;
    @Autowired
    private OrderManager orderManager;

    @WebSocketMapping(command = "list")
    @RequestMapping(value = "/api/order", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> listOrders() {
        return orderManager.listOrders();
    }

    @RequestMapping(value = "/api/order", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Order createOrder(@RequestBody Order order) {
        return orderManager.createOrder(order);
    }

    @RequestMapping(value = "/api/order/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public Order updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        return orderManager.updateOrder(id, order);
    }

    @WebSocketMapping(command = "delete", variables = "id")
    @RequestMapping(value = "/api/order/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteOrder(@PathVariable Integer id) {
        orderManager.deleteOrder(id);
    }

    @WebSocketMapping(command = "queue", variables = "id")
    @RequestMapping(value = "/api/order/{id}/queue", method = RequestMethod.GET)
    @ResponseBody
    public void queueOrder(@PathVariable Integer id) {
        factory.queueOrder(orderManager.getOrder(id));
    }
}
