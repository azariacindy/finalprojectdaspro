import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;
    private UserType userType;

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}

enum UserType { // enum or enumeration is used to define a fixed set of constants
    ADMIN,
    CASHIER
}

class Member {
    private String memberId;
    private String memberName;

    public Member(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }
}

public class project {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static ArrayList<Member> members = new ArrayList<>();
    private static String[] productList = {"Detil", "Pintane", "Emerin", "Pepsudin", "Towel", "Sinsyden", "Tari", "Ultri Milk", "Kenzlir", "Cimore"};
    private static double[] productPrice = {18000, 20000, 20000, 10000, 40000, 11000, 5000, 7000, 9000, 10000};
    private static int[] productQuantities = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20};

    public static void main(String[] args) {
        initializeMembers();
        login();

        int menuChoice;

        do {
            displayMenu();
            menuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (menuChoice) {
                case 1:
                    displayItemList();
                    break;
                case 2:
                    makeTransaction();
                    break;
                case 3:
                    if (currentUser.getUserType() == UserType.ADMIN) {
                        manageMemberData();
                    } else {
                        System.out.println("Access denied. Admin privilege required.");
                    }
                    break;
                case 4:
                    if (currentUser.getUserType() == UserType.ADMIN) {
                        stockInDisplay();
                    } else {
                        System.out.println("Access denied. Admin privilege required.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please try again.");
            }
        } while (menuChoice != 5);

        scanner.close();
    }

    private static void initializeMembers() {
        members.add(new Member("M001", "John Christ"));
        members.add(new Member("M002", "Taylor Swift"));
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User foundUser = authenticate(username, password);

        if (foundUser != null) {
            currentUser = foundUser;
            System.out.println("Login successful. Welcome, " + currentUser.getUserType() + "!");
        } else {
            System.out.println("Login failed. Invalid credentials.");
            System.exit(0);
        }
    }

    private static User authenticate(String username, String password) {
        User admin = new User("admin", "admin123", UserType.ADMIN);
        User cashier = new User("cashier", "cashier123", UserType.CASHIER);

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            return admin;
        } else if (cashier.getUsername().equals(username) && cashier.getPassword().equals(password)) {
            return cashier;
        } else {
            return null;
        }
    }

    private static void displayMenu() {
        System.out.println("1. Display List Product");
        System.out.println("2. Make a Transaction");
        System.out.println("3. Manage Member Data");
        System.out.println("4. Stock In Display");
        System.out.println("5. Exit");
        System.out.print("Select menu: ");
    }

    private static void displayItemList() {
        System.out.println("List of Product:");
        System.out.println("------------------------------");
        System.out.printf("%-10s %-20s %-10s%n", "Code", "Product Name", "Price");
        for (int i = 0; i < productPrice.length; i++) {

            System.out.printf("%-10s %-20s %-10s%n", productList, productPrice);
        }
        System.out.println("------------------------------");
    }

    private static void stockInDisplay() {
        System.out.println("Stock in Display:");
        System.out.println("------------------------------");
        System.out.printf("%-10s %-20s %-15s%n", "Code", "Product Name", "Quantity");
        for (int i = 0; i < productQuantities.length; i++) {

            System.out.printf("%-10s %-20s %-15s%n", productList, productQuantities);
        }
        System.out.println("------------------------------");
    }

    private static void makeTransaction() {
        displayItemList();
        Map<String, Integer> selectedProducts = new HashMap<>();

        int productCode;
        do {
            System.out.print("Select item (enter 0 to finish): ");
            String itemCode = scanner.nextLine().trim();

            if (itemCode.equals("0")) {
                break;
            }

            itemCode = itemCode.toLowerCase(); // to convert all characters to lowercase

            if (isValidItemCode(itemCode)) {
                System.out.print("Enter the quantity of items: ");
                int quantity;
                try {
                    quantity = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity. Please enter a valid number.");
                    continue;
                }

                if (quantity > 0 && quantity <= productQuantities[getIndexFromItemCode(itemCode)][0]) {
                    selectedProducts.put(itemCode, quantity);
                } else {
                    System.out.println("Invalid quantity. Please enter a valid quantity within stock limits.");
                }
            } else {
                System.out.println("Invalid item code!");
            }

        } while (true);

        double totalPrice = calculateTotalPrice(selectedProducts);
        printReceipt(totalPrice, selectedProducts);
    }

    private static boolean isValidItemCode(String itemCode) {
        return itemCode.matches("item[1-3]");
    }

    private static int getIndexFromItemCode(String itemCode) {
        return Integer.parseInt(itemCode.substring(itemCode.length() - 1)) - 1;
    }

    private static double calculateTotalPrice(Map<String, Integer> selectedProducts) {
        double totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : selectedProducts.entrySet()) {
            String itemCode = entry.getKey();
            int quantity = entry.getValue();
            double itemPrice = itemPrices[getIndexFromItemCode(itemCode)][0];

            totalPrice += itemPrice * quantity;
            stock[getIndexFromItemCode(itemCode)][0] -= quantity;
        }

        if (currentUser.getUserType() == UserType.CASHIER) {
            System.out.print("Is the customer a member? (y/n): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("y")) {
                totalPrice -= totalPrice * 0.01;
                System.out.println("Member discount applied!");
            }
        }

        return totalPrice;
    }

    private static void printReceipt(double totalPrice, Map<String, Integer> selectedProducts) {
        System.out.println("------------------------------");
        System.out.println("            RECEIPT           ");
        System.out.println("------------------------------");
        for (Map.Entry<String, Integer> entry : selectedProducts.entrySet()) {
            String itemCode = entry.getKey();
            int quantity = entry.getValue();
            double itemPrice = itemPrices[getIndexFromItemCode(itemCode)][0];

            System.out.printf("%-15s %dx Rp%,.0f%n", itemCode, quantity, itemPrice * quantity);
        }
        System.out.println("------------------------------");
        System.out.printf("Total Price: Rp%,.0f%n", totalPrice);
        System.out.println("------------------------------");
    }

    private static void manageMemberData() {
        System.out.println("1. View Members");
        System.out.println("2. Add Member");
        System.out.println("3. Update Member");
        System.out.println("4. Delete Member");
        System.out.println("5. Back to Main Menu");
        System.out.print("Select option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                viewMembers();
                break;
            case 2:
                addMember();
                break;
            case 3:
                updateMember();
                break;
            case 4:
                deleteMember();
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    private static void viewMembers() {
        System.out.println("List of Members:");
        for (Member member : members) {
            System.out.println("ID: " + member.getMemberId() + ", Name: " + member.getMemberName());
        }
        System.out.println("------------------------------");
    }

    private static void addMember() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();

        if (findMember(memberId) != null) {
            System.out.println("Member with ID " + memberId + " already exists.");
            return;
        }

        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine();

        Member newMember = new Member(memberId, memberName);
        members.add(newMember);

        System.out.println("Member added successfully.");
    }

    private static void updateMember() {
        System.out.print("Enter member ID to update: ");
        String memberId = scanner.nextLine();

        Member memberToUpdate = findMember(memberId);

        if (memberToUpdate != null) {
            System.out.print("Enter new member name: ");
            String newMemberName = scanner.nextLine();

            memberToUpdate = new Member(memberId, newMemberName);

            System.out.println("Member updated successfully.");
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void deleteMember() {
        System.out.print("Enter member ID to delete: ");
        String memberId = scanner.nextLine();

        Member memberToDelete = findMember(memberId);

        if (memberToDelete != null) {
            members.remove(memberToDelete);
            System.out.println("Member deleted successfully.");
        } else {
            System.out.println("Member not found.");
        }
    }

    private static Member findMember(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}
