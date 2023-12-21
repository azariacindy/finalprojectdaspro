import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class ComplexCashierApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize item stock
        Map<String, Integer> itemStock = new HashMap<>();
        itemStock.put("Item1", 20);
        itemStock.put("Item2", 30);
        itemStock.put("Item3", 40);

        // Initialize member database
        Map<String, Double> memberDiscounts = new HashMap<>();
        memberDiscounts.put("Member1", 0.1); // Member1 gets a 10% discount
        memberDiscounts.put("Member2", 0.15); // Member2 gets a 15% discount

        // Initialize sales records
        Map<String, Double> salesRecords = new HashMap<>();

        System.out.println("Welcome to the Complex Cashier App!");

        while (true) {
            System.out.print("Are you a member? (yes/no/exit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            boolean isDiscountMember = input.equalsIgnoreCase("yes");
            double discountRate = isDiscountMember ? 0.0 : 0.0; // Default discount rate for non-members

            if (isDiscountMember) {
                System.out.print("Enter your member ID: ");
                String memberId = scanner.nextLine();

                if (memberDiscounts.containsKey(memberId)) {
                    discountRate = memberDiscounts.get(memberId);
                    System.out.println("Welcome, " + memberId + "! You get a " + (discountRate * 100) + "% discount.");
                } else {
                    System.out.println("Invalid member ID. You will not receive a discount.");
                }
            }

            // Display available items
            System.out.println("Available items:");
            for (String item : itemStock.keySet()) {
                System.out.println(item + " - $" + getItemPrice(item) + " (Stock: " + itemStock.get(item) + ")");
            }

            System.out.print("Enter the number of items you want to purchase: ");
            int itemCount = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            double totalCost = 0.0;

            for (int i = 1; i <= itemCount; i++) {
                System.out.print("Enter the name of item " + i + ": ");
                String itemName = scanner.nextLine();
                if (!itemStock.containsKey(itemName) || itemStock.get(itemName) <= 0) {
                    System.out.println("Sorry, " + itemName + " is not available.");
                    continue;
                }

                double itemPrice = getItemPrice(itemName);
                totalCost += itemPrice;
                itemStock.put(itemName, itemStock.get(itemName) - 1);
            }

            totalCost *= (1 - discountRate); // Apply the discount

            System.out.print("Enter the amount paid by the customer: $");
            double amountPaid = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            double change = amountPaid - totalCost;

            if (change < 0) {
                System.out.println("Insufficient payment. Please pay $" + (-change) + " more.");
            } else {
                System.out.println("Thank you for your purchase!");
                System.out.println("Total cost: $" + totalCost);
                System.out.println("Change: $" + change);

                // Update sales records
                String salesRecord = "Transaction ID: " + generateTransactionId() + ", Total Cost: $" + totalCost;
                salesRecords.put(salesRecord, totalCost);
            }
        }

        // Display updated item stock
        System.out.println("Updated item stock:");
        for (String item : itemStock.keySet()) {
            System.out.println(item + " - Stock: " + itemStock.get(item));
        }

        // Display sales records
        System.out.println("Sales Records:");
        for (String record : salesRecords.keySet()) {
            System.out.println(record);
        }

        scanner.close();
    }

    // Function to get the price of an item (you can replace this with your actual item price logic)
    private static double getItemPrice(String itemName) {
        // You can implement your own logic to fetch item prices from a database or elsewhere
        // For simplicity, we'll use a fixed price here
        switch (itemName) {
            case "Item1":
                return 5.0;
            case "Item2":
                return 7.0;
            case "Item3":
                return 10.0;
            default:
                return 0.0; // Return 0 for unknown items
        }
    }

    // Function to generate a unique transaction ID (you can replace this with your own logic)
    private static String generateTransactionId() {
        return "T" + System.currentTimeMillis();
    }
}
