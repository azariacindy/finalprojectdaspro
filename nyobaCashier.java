import java.util.Scanner;

public class nyobaCashier {
    public static void main(String[] args) {
        // Initialize variables
        Scanner scanner = new Scanner(System.in);
        String productName;
        double pricePerUnit;
        int quantity;
        String customerName;
        String contactInfo;
        double cashPayment;
        double totalCost;

        // Input product details
        System.out.print("Enter product name: ");
        productName = scanner.nextLine();
        System.out.print("Enter price per unit: ");
        pricePerUnit = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        quantity = scanner.nextInt();

        // Calculate total cost
        totalCost = pricePerUnit * quantity;

        // Input customer information
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter customer name: ");
        customerName = scanner.nextLine();
        System.out.print("Enter contact information: ");
        contactInfo = scanner.nextLine();

        // Input payment amount
        System.out.print("Enter cash payment amount: ");
        cashPayment = scanner.nextDouble();

        // Calculate change
        double change = cashPayment - totalCost;

        // Generate receipt
        String receipt = generateReceipt(productName, pricePerUnit, quantity, totalCost, customerName, contactInfo, cashPayment, change);

        // Display receipt and change
        System.out.println("Receipt:");
        System.out.println(receipt);
        System.out.println("Change: " + change);

        // Close the scanner
        scanner.close();
    }

    // Method to generate receipt
    public static String generateReceipt(String productName, double pricePerUnit, int quantity, double totalCost, String customerName, String contactInfo, double cashPayment, double change) {
        // Implement receipt formatting here
        // You can format the receipt as a String and return it
        // Include product details, customer information, total cost, and payment details
        // Example:
        String receipt = "Product: " + productName + "\n"
                + "Price per unit: " + pricePerUnit + "\n"
                + "Quantity: " + quantity + "\n"
                + "Total cost: " + totalCost + "\n"
                + "Customer: " + customerName + "\n"
                + "Contact: " + contactInfo + "\n"
                + "Cash payment: " + cashPayment + "\n"
                + "Change: " + change + "\n";
        return receipt;
    }
}
