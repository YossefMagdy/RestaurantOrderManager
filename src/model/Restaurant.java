package model;

import enums.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

public class Restaurant {

    private final ArrayList<MenuItem> menu = new ArrayList<>();
    private final LinkedList<Order> kitchenQueue =new LinkedList<>();
    private final HashMap<Integer, Order> orders = new LinkedHashMap<>();
    private final LinkedHashMap<Integer, Order> completedOrdersStore = new LinkedHashMap<>();

    public Optional<MenuItem> findMenuItem(int id) {
        return menu.stream().filter(item -> item.getId() == id).findFirst();
    }

    public Optional<Order> findOrder(int id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<MenuItem> getMenuSortedById() {
        return menu.stream().sorted(Comparator.comparingInt(MenuItem::getId))
                .collect(Collectors.toList());
    }

    public List<MenuItem> getMenuSortedByPrice() {
        return menu.stream().sorted(Comparator.comparingDouble(MenuItem::getPrice)
                .thenComparingInt(MenuItem::getId)).collect(Collectors.toList());
    }

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
        menu.forEach(m -> System.out.println(m));
    }

    public void searchMenuItem(Integer id){
        System.out.println(findMenuItem(id).map(item -> "-> " + item)
                .orElse("Menu id doesn't exist"));
    }

    public void createOrder(Order o){
        if (orders.putIfAbsent(o.getOrderId(), o) != null) {
            System.out.println("Order already exists");
        }
    }

    public boolean checkOrderId (int orderId){
       return orders.containsKey(orderId);
    }

    public void AddOrderItem(int orderId, int menuItemId,int quantity){
        if(!checkOrderId(orderId)){
            System.out.println("Order Id doesn't exist");
            return;
        }
        Order order = orders.get(orderId);
        if(order.getStatus() == OrderStatus.COMPLETED ||
                order.getStatus() == OrderStatus.CANCELLED){
            System.out.println("Order has already been " + order.getStatus());
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
            System.out.println("Order Id doesn't exist");
            return;
        }

        Order order = orders.get(orderId);

        if(order.getStatus() == OrderStatus.COMPLETED ||
                order.getStatus() == OrderStatus.CANCELLED){
            System.out.println("Order has already been " + order.getStatus());
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
        orders.values().forEach(o -> System.out.println(o));
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
        System.out.println("Kitchen queue is empty");
    }

    public void displayCompletedOrders() {
        completedOrdersStore.values().forEach(o -> System.out.println(o));
    }
}
