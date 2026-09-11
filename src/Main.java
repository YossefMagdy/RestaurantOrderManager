import model.MenuItem;
import model.Order;
import model.Restaurant;

import java.util.Scanner;

import static util.InputValidators.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Restaurant restaurant = new Restaurant();

    public static void main(String[] args) {
        menu();
    }
    public static void displayMenu() {
        System.out.println("===== " + " Resturant Order Management System " + " =====");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. Display Menu");
        System.out.println("4. Search Menu Item");
        System.out.println("5. Create Order");
        System.out.println("6. Add Item to Order");
        System.out.println("7. Remove Item from Order");
        System.out.println("8. Display Order");
        System.out.println("9. Add Order to Kitchen Queue");
        System.out.println("10. Process Next Order");
        System.out.println("11. Search Order");
        System.out.println("12. Check Order Status");
        System.out.println("13. Display Completed Orders");
        System.out.println("14. Exit");
    }

    public static void menu(){

        while (true){
            displayMenu();
            int choice  = validateChoice(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    System.out.println("Enter Menu Item Id ");
                    int menuItemId = validateInteger(scanner, "Menu Item Id ", 1);

                    scanner.nextLine();

                    System.out.println("Enter Menu Item Name ");
                    String menuItemName =validateName(scanner);

                    System.out.println("Enter Menu Item Price ");
                    double price = validateBalance(scanner);

                    scanner.nextLine();

                    System.out.println("Enter Menu Item Category ");
                    String menuCategory = validateName(scanner);

                    restaurant.AddMenuItem(new MenuItem(menuItemId,menuItemName,price,menuCategory));
                    break;
                }
                case 2: {
                    System.out.println("Enter Menu Item Id ");
                    int menuItemId = validateInteger(scanner, "Menu Item Id ", 1);

                    restaurant.removeMenuItem(menuItemId);
                    break;
                }

                case 3:
                    restaurant.displayMenu();
                    break;

                case 4: {
                    System.out.println("Enter Menu Item Id ");
                    int menuItemId = validateInteger(scanner, "Menu Item Id ", 1);
                    restaurant.searchMenuItem(menuItemId);
                    break;
                }

                case 5: {
                    System.out.println("Enter Order Id ");
                    int orderId = validateInteger(scanner, "Order Id ", 1);

                    scanner.nextLine();

                    System.out.println("Enter Customer Name ");
                    String customerName =validateName(scanner);

                    restaurant.createOrder(new Order(orderId,customerName));
                    break;
                }
                case 6: {
                    System.out.println("Enter Order Id ");
                    int orderId = validateInteger(scanner, "Order Id ", 1);

                    System.out.println("Enter Menu Item Id ");
                    int menuItemId = validateInteger(scanner, "Menu Item Id ", 1);

                    System.out.println("Enter the item quantity ");
                    int quantity = validateInteger(scanner, "Item quantity ", 1);

                    restaurant.AddOrderItem(orderId,menuItemId,quantity);
                    break;
                }
                case 7: {
                    System.out.println("Enter Order Id ");
                    int orderId = validateInteger(scanner, "Order Id ", 1);

                    System.out.println("Enter Menu Item Id ");
                    int menuItemId = validateInteger(scanner, "Menu Item Id ", 1);

                    restaurant.removeOrderItem(orderId,menuItemId);
                    break;
                }
                case 8: {

                    restaurant.displayAllOrders();
                    break;
                }
                case 9:
                    restaurant.addOrderTokitchenQueue();
                    break;

                case 10:
                    restaurant.nextOrder();
                    break;

                case 11: {
                    System.out.println("Enter Order Id ");
                    int orderId = validateInteger(scanner, "Order Id ", 1);

                    System.out.println(restaurant.findOrder(orderId).map(Order::toString)
                            .orElse("Order Id doesn't exist"));
                    break;
                }
                case 12: {
                    System.out.println("Enter Order Id ");
                    int orderId = validateInteger(scanner, "Order Id ", 1);
                    System.out.println(restaurant.findOrder(orderId)
                            .map(order -> "Current order status : " + order.getStatus())
                            .orElse("Order Id doesn't exist"));
                    break;
                }
                case 13:
                    restaurant.displayCompletedOrders();
                    break;

                case 14: {
                    System.out.println("Goodbye.");
                    return;
                }

                default: System.out.println("Invalid choice.");

            }
        }
    }
}
