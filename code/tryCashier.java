import java.util.Scanner; // Import the Scanner class

public class tryCashier {
    public static void main(String[] args) {
        String product_name; // Declare a variable to store the product name
        int product_amount, price, totalprice; // Declare variables for quantity, price, and total price
        double discount = 0.075; // Declare and initialize a discount rate
        double totalpayment; // Declare a variable for the total payment
        
        Scanner input = new Scanner(System.in); // Initialize a Scanner object
        
        System.out.println("Minimarket Cashier Program"); // Display a message
        System.out.println("-----------------------");
        System.out.println("Enter the following data");
        System.out.print("Product Name = "); // Prompt for product name
        product_name = input.nextLine(); // Read product name from the user
        
        System.out.print("Quantity = "); // Prompt for quantity
        product_amount = input.nextInt(); // Read quantity from the user
        
        if (product_amount <= 0) { // Check if quantity is less than or equal to zero
            System.out.println("Please enter a valid quantity and rerun the program");
            System.exit(0); // Exit the program if quantity is invalid
        }
        
        System.out.print("Price =  "); // Prompt for price
        price = input.nextInt(); // Read price from the user
        
        if (price < 100) { // Check if price is less than 100
            System.out.println("Please enter a valid price and rerun the program");
            System.exit(0); // Exit the program if price is invalid
        }
        
        System.out.println("You get a 7.5% discount"); // Display a discount message
        totalprice = product_amount * price; // Calculate the total price
        System.out.println("Total Price =" + totalprice); // Display the total price
        discount = totalprice * discount; // Calculate the discount amount
        totalpayment = totalprice - discount; // Calculate the total payment
        System.out.println("Total Payment =" + totalpayment); // Display the total payment
        System.out.println("-------------------------");
        System.out.println("Purchase Receipt");
        System.out.println("Product Name =" + product_name); // Display the product name
        System.out.println("Quantity =" + product_amount); // Display the quantity
        System.out.println("Price =" + price); // Display the price
        System.out.println("Total Price =" + totalprice); // Display the total price
        System.out.println("Total Payment =" + totalpayment); // Display the total payment
        System.out.println("-------------------------");
        
        input.close(); // Close the Scanner object
    }
}
