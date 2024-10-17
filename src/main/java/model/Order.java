package model;

/**
 * A class representing an order made by a client for a product.
 */
public class Order {
    private int orderId;
    private int clientId;
    private int productId;
    private String orderDate;
    private Double totalPrice;

    public Order(){}
    public Order(int orderId, int clientId, int productId, String orderDate, Double totalPrice) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice;
    }
}
