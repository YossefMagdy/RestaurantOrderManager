package model;

import enums.OrderStatus;

import java.util.ArrayList;

public class Order {

    private final int orderId;
    private final String customerName;
    private ArrayList<OrderItem> items = new ArrayList<>();
    private double total;
    private OrderStatus status;

    public Order(int orderId, String customerName, ArrayList<OrderItem> items, double total) {
        this(orderId,customerName);
        this.items = items;
        this.total = total;
    }

    public Order(int orderId, String customerName) {
        this.customerName = customerName;
        this.orderId = orderId;
        this.status = OrderStatus.PENDING;
    }

    public int getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void setItems(OrderItem items) {
        int index = this.items.indexOf(items);
        if(index == -1){
            this.items.add(items);
            return;
        }
        this.items.set(index,items);
    }

    public void removeItems(OrderItem orderItem) {
        this.items.remove(orderItem);
    }

    public void calculateTotal() {
        total = 0;
        for (OrderItem item : items) {
            total += item.calculateSubTotal();
        }
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", items=" + items +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}
