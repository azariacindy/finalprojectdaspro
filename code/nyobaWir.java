import java.util.Arrays; // import arrays
import java.util.Scanner; // import scanner

public class nyobaWir {

    static Scanner scanner = new Scanner(System.in);

    static class Product { // mendefinisikan class product dengan atribut name, price, quantity
        String name;
        double price;
        int quantity;

        // menginisialisasi atribut dengan nilai yang sudah didefinisikan sebelumnya
        Product(String name, double price, int quantity) { 
            this.name = name;
            this.price = price;
            this.quantity = quantity; 
            // 'this' membantu menjelaskan bahwa kita merujuk pada atribut class, bukan variabel local
            // untuk mengatur/menginisialisasi nilai atribut quantity
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

    // mendeklarasi dan inisialisasi array
    static User[] users = {
            new User("cashier", "cashier123", "CASHIER"),
            new User("admin", "admin123", "ADMIN")
            // Add other users
    };

    static Product[] products = {
            new Product("Detil", 18000, 20),
            new Product("Pintane", 20000, 20),
            new Product("Emerin", 10000, 20)
            // Add other products
    };

    static Member[] members = {
            new Member(101, "Adele"),
            new Member(102, "Taylor Swift"),
            new Member(103, "Olivia Rodrigo")
            // Add other members
    };

    static double discount = 0.1; // diskon defaultnya 10% / 0.1

    // dapat diakses dari mana saja
    public static void main(String[] args) {
        System.out.println("----------------------------------------------");
        System.out.println("||          WELCOME TO OLI MARKET           ||");
        System.out.println("----------------------------------------------");
    
        User currentUser = login();
    
        mainMenu(currentUser); // Panggil mainMenu dengan currentUser sebagai parameter
    
        scanner.close();
    }    

    // dapat diakses tanpa membuat objek dari kelas yang sesuai
    static User login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine(); // input username

        System.out.print("Enter password: ");
        String password = scanner.nextLine(); // input password

        for (User user : users) { // menggunakan for each loop untuk memeriksa objek User dalam array user
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Login successful. Welcome, " + user.userType + "!");
                return user;
            }
        }

        System.out.println("Login failed. Invalid credentials.");
        System.exit(0); // keluar dari fungsi jika status 0(sukses)
        return null; // jika login gagal maka mengembalikan nilai null
    }

    static void mainMenu(User currentUser) {
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
                    if ("ADMIN".equals(currentUser.userType)) {
                        manageMemberData();
                    } else {
                        System.out.println("Access denied. Admin privilege required.");
                    }
                    break;
                case 4:
                    displayItemQuantities(products);
                    break;
                case 5:
                    if ("ADMIN".equals(currentUser.userType)) {
                        manageProductStock(products, currentUser);
                    } else {
                        System.out.println("Access denied. Admin privilege required.");
                    }
                    break;
                case 6:
                    System.out.println("Thanks For Comming!");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please try again.");
            }
        } while (menuChoice != 6);
    }    

    // void tidak bisa return data, jika dipanggil ke main fungsi dengan void hanya melakukan perintah
    static void displayMenu(User user) {
        System.out.println("----------------------------------------------");
        System.out.println("|| 1. Display List of Items                 ||");
        System.out.println("|| 2. Make a Transaction                    ||");
        System.out.println("|| 3. Manage Member Data                    ||");
        System.out.println("|| 4. Stock of Items in Display             ||");
        System.out.println("|| 5. Manage Display Stock                  ||");
        System.out.println("|| 6. Exit                                  ||");
        System.out.println("----------------------------------------------");
        System.out.print("Select menu: ");
    }

    static void displayItemList(Product[] products) {
        System.out.println("----------------------------------------------");
        System.out.println("||            List of Products:             ||");
        System.out.println("----------------------------------------------");
        System.out.printf("%-7s %-20s %8s%n", "Code", "Product Name", "Price");
        for (int i = 0; i < products.length; i++) { 
            System.out.printf("%-7d %-20s Rp%,8.0f%n", i + 1, products[i].name, products[i].price);
        }
        System.out.println("----------------------------------------------");
    }

    static void displayItemQuantities(Product[] products) {
        System.out.println("----------------------------------------------");
        System.out.println("||     List of Products and Quantities:     ||");
        System.out.println("----------------------------------------------");
        System.out.printf("%-7s %-20s %-10s%n", "Code", "Product Name", "Quantity");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%-7d %-20s %-10d%n", i + 1, products[i].name, products[i].quantity);
        }
        System.out.println("----------------------------------------------");
    }

    static void manageProductStock(Product[] products, User currentUser) {
        boolean exitingManageStock = false;
    
        do {
            displayItemQuantities(products);
            System.out.println("Options:");
            System.out.println("1. Update Stock");
            System.out.println("0. Back to Main Menu");
    
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
    
            switch (choice) {
                case 1:
                    System.out.print("Enter the item code to update quantity (enter 0 to finish): ");
                    int productCode = scanner.nextInt();
    
                    while (productCode != 0) {
                        if (productCode > 0 && productCode <= products.length) {
                            System.out.print("Enter the new quantity of items: ");
                            int newQuantity = scanner.nextInt();
                            System.out.println();
                            products[productCode - 1].quantity += newQuantity;
                        } else {
                            System.out.println("----------------------------------------------");
                            System.out.println("||            Invalid item code!            ||");
                            System.out.println("----------------------------------------------");
                        }
    
                        displayItemQuantities(products);
    
                        System.out.print("Enter the item code to update quantity (enter 0 to finish): ");
                        productCode = scanner.nextInt();
                    }
    
                    break;
                case 0:
                    exitingManageStock = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!exitingManageStock);
    
        // Setelah selesai mengelola stok produk, kembali ke menu utama
        mainMenu(currentUser);
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

    static void makeTransaction(User currentUser) {
        displayItemList(products);
        Order[] orders = recordOrders(products);
    
        if (orders.length == 0) {
            System.out.println("No items selected. Exiting transaction.");
            return;
        }
    
        System.out.print("Enter discount percentage (0-100): ");
        int discountPercentage = scanner.nextInt();
    
        if (discountPercentage >= 0 && discountPercentage <= 100) {
            double discountRate = discountPercentage / 100.0;
            double totalPrice = calculateTotalPrice(orders);
    
            boolean isMember = checkMembershipCard();
            if (isMember) {
                double discountAmount = totalPrice * discountRate;
                double discountedPrice = totalPrice - discountAmount;
                System.out.println("Discount applied: " + discountPercentage + "%");
                System.out.printf("Discounted Amount: Rp%,.0f%n", discountAmount);
                System.out.printf("Total Price after Discount: Rp%,.0f%n", discountedPrice);
                displayReceipt(orders, discountedPrice);
                updateStock(products, orders);
    
                double change = handlePayment(discountedPrice);
            } else {
                displayReceipt(orders, totalPrice);
                updateStock(products, orders);
                double change = handlePayment(totalPrice);
            }
        } else {
            System.out.println("Invalid discount percentage. Exiting transaction.");
        }
    }        

    // static dengan parameter ada inisialisasi didalamnya
    static Order[] recordOrders(Product[] products) {
        System.out.println("||            Record your orders            ||");
        System.out.println("----------------------------------------------");
        
        Order[] orderArray = new Order[0]; // Dynamic 2array to store orders
    
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
                System.out.println("----------------------------------------------");
                System.out.println("||            Invalid item code!            ||");
                System.out.println("----------------------------------------------");
            }
        } while (true);
    
        return orderArray;
    }    

    // static tanpa parameter tidak memerlukan informasi tambahan dari luar
    static boolean checkMembershipCard() {
        System.out.println("----------------------------------------------");
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
        System.out.println("----------------------------------------------");
        System.out.println("||                  RECEIPT                 ||");
        System.out.println("----------------------------------------------");
        for (Order order : orders) { 
            System.out.printf("%-20s   %d   Rp%,8.0f%n", order.productName, order.quantity,
                    getPriceByProductName(order.productName) * order.quantity);
        }
        System.out.println("----------------------------------------------");
        System.out.printf("Total Price: Rp%,.0f%n", totalPrice);
        System.out.println("----------------------------------------------");
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
        // stream digunakan untuk mengolah data secara fungsional
        return Arrays.stream(products) 
            // operasi filter yang digunakan untuk memeriksa setiap elemen produk dalam stream    
            .filter(product -> product.name.equals(productName)) 
                .findFirst() // mengambil elemen pertama yang memenuhi syarat (jika ada) dari stream
                .map(product -> product.price) // mengambil harga produk
                .orElse(0.0); // jika tidak berisi nilai (tidak ada produk yang memenuhi syarat), maka operasi ini akan mengembalikan nilai default 0.0.
    }

    //private tidak dapat diakses dari luar kelas
    private static void manageMemberData() {
        System.out.println("----------------------------------------------");
        System.out.println("|| 1. View Members                          ||");
        System.out.println("|| 2. Update Member                         ||");
        System.out.println("|| 3. Delete Member                         ||");
        System.out.println("|| 4. Back to Main Menu                     ||");
        System.out.println("----------------------------------------------");
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
                System.out.println("----------------------------------------------");
                System.out.println("||    Invalid option. Please try again.     ||");
                System.out.println("----------------------------------------------");
                break;
        }
    }

    private static void viewMembers() {
        System.out.println("----------------------------------------------");
        System.out.println("||              List of Members:            ||");
        System.out.println("----------------------------------------------");
        System.out.printf("%-5s %-20s%n", "Id", "Member Name");
        for (Member member : members) {
            System.out.printf("%-5d %-20s%n", member.memberId, member.memberName);
        }
        System.out.println("----------------------------------------------");
    }    

    private static void updateMember() {
        System.out.print("Enter member ID to update: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
    
        Member memberToUpdate = findMember(memberId);
    
        if (memberToUpdate != null) {
            System.out.print("Enter new member name: ");
            String newMemberName = scanner.nextLine();
    
            // Update the existing member
            memberToUpdate.memberName = newMemberName;
    
            System.out.println("----------------------------------------------");
            System.out.println("||       Member updated successfully.       ||");
            System.out.println("----------------------------------------------");
        } else {
            System.out.println("----------------------------------------------");
            System.out.println("||             Member not found.            ||");
            System.out.println("----------------------------------------------");
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
            System.out.println("----------------------------------------------");
            System.out.println("||       Member deleted successfully.       ||");
            System.out.println("----------------------------------------------");
        } else {
            System.out.println("----------------------------------------------");
            System.out.println("||             Member not found.            ||");
            System.out.println("----------------------------------------------");
        }
    }    

    private static Member findMember(int memberId) {
        for (Member member : members) { // untuk mengiterasi melalui sebuah array members yang disimpan pada variabel member
            if (member.memberId == memberId) { // memeriksa member.memberId sama dengan memberid yang di input
                return member; 
            }
        }
        return null; // jika memberId tidak sesuai maka return null
    }
}  
