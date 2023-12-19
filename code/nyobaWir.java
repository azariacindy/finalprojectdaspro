import java.util.Arrays;
import java.util.Scanner;

public class nyobaWir {

    static Scanner scanner = new Scanner(System.in);

    static class Product {
        String name;
        double price;
        int quantity;

        Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    static class Order {
        String productName;
        int quantity;

        Order(String productName, int quantity) {
            this.productName = productName;
            this.quantity = quantity;
        }
    }

    static class User {
        String username;
        String password;
        String userType;

        User(String username, String password, String userType) {
            this.username = username;
            this.password = password;
            this.userType = userType;
        }
    }

    static class Member {
        int memberId;
        String memberName;

        Member(int memberId, String memberName) {
            this.memberId = memberId;
            this.memberName = memberName;
        }
    }

    static User[] users = {
            new User("cashier", "cashier123", "CASHIER"),
            new User("admin", "admin123", "ADMIN")
            // Add other users
    };

    static Product[] products = {
            new Product("Detil", 18000, 20),
            new Product("Pintane", 20000, 20),
            new Product("Emerin", 10000, 20),
            // Add other products
    };

    static Member[] members = {
            new Member(101, "Adele"),
            new Member(102, "Taylor Swift"),
            // Add other members
    };

    static double discount = 0.1;

    public static void main(String[] args) {
        System.out.println("-------------------------------");
        System.out.println("-    WELCOME TO OLI MARKET    -");
        System.out.println("-------------------------------");

        User currentUser = login();

        int menuChoice;

        do {
            displayMenu(currentUser);
            menuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (menuChoice) {
                case 1:
                    displayItemList(products);
                    break;
                case 2:
                    makeTransaction(currentUser);
                    break;
                case 3:
                    manageMemberData();
                    break;
                case 4:
                    if ("ADMIN".equals(currentUser.userType)) {
                        displayItemQuantities(products);
                    } else {
                        System.out.println("Access denied. Admin privilege required.");
                    }
                    break;
                case 5:
                    if ("ADMIN".equals(currentUser.userType)) {
                        manageProductStock(products);
                    } else {
                        System.out.println("Access denied. Admin privilege required.");
                    }
                    break;
                case 6:
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please try again.");
            }
        } while (menuChoice != 5);

        scanner.close();
    }

    static User login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Login successful. Welcome, " + user.userType + "!");
                return user;
            }
        }

        System.out.println("Login failed. Invalid credentials.");
        System.exit(0);
        return null;
    }

    static void displayMenu(User user) {
        System.out.println("1. Display List of Items");
        System.out.println("2. Make a Transaction");
        System.out.println("3. Manage Member Data");
        System.out.println("4. Display List and Quantity of Items");
        System.out.println("5. Manage Display Stock");
        System.out.println("6. Exit");
        System.out.print("Select menu: ");
    }

    static void displayItemList(Product[] products) {
        System.out.println("List of Products:");
        System.out.println("----------------------------------------------");
        System.out.printf("%-5s %-20s %8s%n", "Code", "Product Name", "Price");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%-5d %-20s Rp%,8.0f%n", i + 1, products[i].name, products[i].price);
        }
        System.out.println("----------------------------------------------");
    }

    static void displayItemQuantities(Product[] products) {
        System.out.println("List of Products and Quantities:");
        System.out.println("----------------------------------");
        System.out.printf("%-20s %-10s%n", "Product Name", "Quantity");
        for (Product product : products) {
            System.out.printf("%-20s %-10d%n", product.name, product.quantity);
        }
        System.out.println("----------------------------------");
    }

    static void manageProductStock(Product[] products) {
        displayItemQuantities(products);
        System.out.print("Enter the item code to update quantity (enter 0 to finish): ");
        int productCode = scanner.nextInt();

        while (productCode != 0) {
            if (productCode > 0 && productCode <= products.length) {
                System.out.print("Enter the new quantity of items: ");
                int newQuantity = scanner.nextInt();
                System.out.println();
                products[productCode - 1].quantity += newQuantity;
            } else {
                System.out.println("Invalid item code!");
            }

            displayItemQuantities(products);

            System.out.print("Enter the item code to update quantity (enter 0 to finish): ");
            productCode = scanner.nextInt();
        }
    }

    static void makeTransaction(User currentUser) {

        displayItemList(products);
        Order[] orders = recordOrders(products);

        if (orders.length == 0) {
            System.out.println("No items selected. Exiting transaction.");
            return;
        }

        double totalPrice = calculateTotalPrice(orders);

        if ("CASHIER".equals(currentUser.userType)) {
            boolean isMember = checkMembershipCard();
            if (isMember) {
                totalPrice -= totalPrice * discount;
                System.out.println("Member discount applied!");
            }
        }

        displayReceipt(orders, totalPrice);
        updateStock(products, orders);

        double change = handlePayment(totalPrice);
    }

    static Order[] recordOrders(Product[] products) {
        System.out.println("Record your orders (enter 0 to finish):");
        
        Order[] orderArray = new Order[0]; // Dynamic array to store orders
    
        int productCode;
        int orderIndex = 0;
    
        do {
            System.out.print("Select item (enter 0 to finish): ");
            productCode = scanner.nextInt();
    
            if (productCode > 0 && productCode <= products.length) {
                System.out.print("Enter the quantity of items: ");
                int quantity = scanner.nextInt();
    
                // Resize the array and add the new order
                Order[] newOrderArray = new Order[orderArray.length + 1];
                System.arraycopy(orderArray, 0, newOrderArray, 0, orderArray.length);
                newOrderArray[orderArray.length] = new Order(products[productCode - 1].name, quantity);
    
                orderArray = newOrderArray;
            } else if (productCode == 0) {
                break;
            } else {
                System.out.println("Invalid item code!");
            }
        } while (true);
    
        return orderArray;
    }    

    static boolean checkMembershipCard() {
        System.out.print("Do you have a membership card? (y/n): ");
        String memberResponse = scanner.next();
        return memberResponse.equalsIgnoreCase("y");
    }

    static double calculateTotalPrice(Order[] orders) {
        double totalPrice = 0.0;

        for (Order order : orders) {
          if (order.quantity != 0) {
              for (Product product : products) {
                  if (product.name.equals(order.productName)) {
                      totalPrice += product.price * order.quantity;
                      break;
                  }
              }
          }
        }

        return totalPrice;
    }

    static void displayReceipt(Order[] orders, double totalPrice) {
        System.out.println("------------------------------");
        System.out.println("            RECEIPT           ");
        System.out.println("------------------------------");
        for (Order order : orders) {
            System.out.printf("%-20s   %d   Rp%,8.0f%n", order.productName, order.quantity,
                    getPriceByProductName(order.productName) * order.quantity);
        }
        System.out.println("------------------------------");
        System.out.printf("Total Price: Rp%,.0f%n", totalPrice);
        System.out.println("------------------------------");
    }

    static double handlePayment(double totalPrice) {
        System.out.print("Enter the amount of money from the customer: Rp. ");
        double customerMoney = scanner.nextDouble();

        if (customerMoney >= totalPrice) {
            double change = customerMoney - totalPrice;
            String itemFormat = String.format("Change: Rp%,8.0f", change);
            System.out.println(itemFormat);
            return change;
        } else {
            System.out.println("Sorry, your payment is not sufficient. Please try again.");
            return 0; // Indicates insufficient payment
        }
    }

    static double getPriceByProductName(String productName) {
        return Arrays.stream(products)
                .filter(product -> product.name.equals(productName))
                .findFirst()
                .map(product -> product.price)
                .orElse(0.0);
    }

    static void updateStock(Product[] products, Order[] orders) {
        for (Order order : orders) {
          if (order.quantity != 0) {
              for (Product product : products) {
                  if (product.name.equals(order.productName)) {
                      product.quantity -= order.quantity;
                      break;
                  }
              }
          }
        }
        
        System.out.println("Stock updated successfully.");
    }

    private static void manageMemberData() {
        System.out.println("1. View Members");
        System.out.println("2. Update Member");
        System.out.println("3. Delete Member");
        System.out.println("4. Back to Main Menu");
        System.out.print("Select option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                viewMembers();
                break;
            case 2:
                updateMember();
                break;
            case 3:
                // Prompt the user to enter the member ID to delete
                System.out.print("Enter member ID to delete: ");
                int memberIdToDelete = scanner.nextInt();
                deleteMember(memberIdToDelete);
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    private static void viewMembers() {
        System.out.println("List of Members:");
        System.out.println("----------------------------------------------");
        System.out.printf("%-5s %-20s%n", "Id", "Member Name");
        for (Member member : members) {
            System.out.printf("%-5d %-20s%n", member.memberId, member.memberName);
        }
        System.out.println("----------------------------------------------");
        System.out.println("------------------------------");
    }    

    private static void updateMember() {
        System.out.print("Enter member ID to update: ");
        String memberId = scanner.nextLine();

        Member memberToUpdate = findMember(memberId);

        if (memberToUpdate != null) {
            System.out.print("Enter new member name: ");
            String newMemberName = scanner.nextLine();

            // Update the existing member
            memberToUpdate.memberName = newMemberName;

            System.out.println("Member updated successfully.");
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void deleteMember(int memberId) {
        Member memberToDelete = findMember(memberId);
    
        if (memberToDelete != null) {
            // Create a new array without the member to be deleted
            Member[] newMembers = new Member[members.length - 1];
            int index = 0;
            for (Member member : members) {
                if (member.memberId != memberId) {
                    newMembers[index++] = member;
                }
            }
            members = newMembers;
            System.out.println("Member deleted successfully.");
        } else {
            System.out.println("Member not found.");
        }
    }    

    private static Member findMember(int memberId) {
        for (Member member : members) {
            if (member.memberId == memberId) {
                return member;
            }
        }
        return null;
    }
}  