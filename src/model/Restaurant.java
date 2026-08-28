package model;

import enums.OrderStatus;

import java.util.*;

public class Restaurant {

    private final ArrayList<MenuItem> menu = new ArrayList<>();
    private final LinkedList<Order> kitchenQueue =new LinkedList<>();
    private final HashMap<Integer, Order> orders = new HashMap<>();
    private final LinkedHashMap<Integer, Order> completedOrdersStore = new LinkedHashMap<>();

    public void AddMenuItem(MenuItem m){
        if(checkMenuId(m.getId()) != -1){
            System.out.println("Menu Item Id already exists");
            return;
        }
        menu.add(m);
    }

    public void removeMenuItem(int id){
        if(checkMenuId(id) == -1){
            System.out.println("Menu Item Id doesn't exists");
            return;
        }
        System.out.println("Menu Item -> " + menu.remove(checkMenuId(id)) + " has been removed");
    }

    public int checkMenuId (int menuItemId){
        int index = 0;
        for (int i=0; i < menu.size(); i++) {
            if(menu.get(i) !=null && menu.get(i).getId() == menuItemId){
                index  = i;
                return  index;
            }
        }
        index = -1;
        return index;
    }

    public void displayMenu(){
        if(menu.isEmpty()){
            System.out.println("Menu Items are empty");
            return;
        }
        for(MenuItem m : menu){
            System.out.println(m);
        }
    }

    public void searchMenuItem(Integer id){
        int itemId = checkMenuId(id);
        if(itemId != -1){
            System.out.println("-> " + menu);
            return;
        }
        System.out.println("Menu id doesn't exists");
    }

    public void createOrder(Order o){
        if(checkOrderId(o.getOrderId())){
            System.out.println("Order already exists");
            return;
        }
        orders.put(o.getOrderId(), o);
    }

    public boolean checkOrderId (int orderId){
       return orders.containsKey(orderId);
    }

    public void AddOrderItem(int orderId, int menuItemId,int quantity){
        if(!checkOrderId(orderId)){
            System.out.println("Order Id doesn't exists");
            return;
        }
        Order order = orders.get(orderId);
        if(order.getStatus() == OrderStatus.COMPLETED ||
                order.getStatus() == OrderStatus.CANCELLED){
            System.out.printf("Order has already been %s",order.getStatus());
            return;
        }

        int itemIndex = checkMenuId(menuItemId);
        if(itemIndex == -1){
            System.out.println("Menu Item Id doesn't exists");
            return;
        }

        MenuItem item = this.menu.get(itemIndex);

        order.setItems(new OrderItem(item, quantity));
        order.calculateTotal();
    }

    public void removeOrderItem(int orderId, int menuItemId){
        if(!checkOrderId(orderId)){
            System.out.println("Order Id doesn't exists");
            return;
        }

        Order order = orders.get(orderId);

        if(order.getStatus() == OrderStatus.COMPLETED ||
                order.getStatus() == OrderStatus.CANCELLED){
            System.out.printf("Order has already been %s",order.getStatus());
            return;
        }

        for (OrderItem orderItem : order.getItems()) {

            if (orderItem.getItem().getId() == menuItemId) {

                order.removeItems(orderItem);

                order.calculateTotal();

                System.out.println("Menu Item -> " + orderItem.getItem().getName()
                        + " has been removed from Order " + orderId);

                return;
            }
        }

        System.out.println("Menu Item doesn't exist in this order");
    }

    public Order displayOrder(int orderId){
        return orders.get(orderId);
    }
    public void displayAllOrders(){
        for(Order o : orders.values()){
            System.out.println(o);
        }
    }

    public void addOrderTokitchenQueue(){
        for(Order o : orders.values()){
            if(o.getStatus() == OrderStatus.PENDING){
                o.setStatus(OrderStatus.IN_KITCHEN);
                kitchenQueue.add(o);
                System.out.println("Order has been added to kitchen queue");
                return;
            }
        }
        System.out.println("No orders Pending");
    }

    public void nextOrder(){
        Order order = kitchenQueue.poll();
        if(order!=null){
            order.setStatus(OrderStatus.COMPLETED);

            orders.get(order.getOrderId()).setStatus(OrderStatus.COMPLETED);

            completedOrdersStore.put(order.getOrderId(), order);

            return;
        }
        System.out.println("Order has already been empty");
    }

    public void displayCompletedOrders() {
        for (Order o : completedOrdersStore.values()) {
            System.out.println(o);
        }
    }
}
