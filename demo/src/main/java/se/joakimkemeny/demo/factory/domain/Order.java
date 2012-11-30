package se.joakimkemeny.demo.factory.domain;

public class Order {

    public static enum OrderStatus {
        Received, Queued, Producing, Produced, Canceled
    }

    private Integer id;
    private String customer;
    private int quantity;
    private OrderStatus status = OrderStatus.Received;

    public Order() {
    }

    public Order(Integer id, String customer, int quantity) {
        this.id = id;
        this.customer = customer;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
