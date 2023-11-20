import java.util.Scanner;

public class nyobaWir {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Daftar barang
        String[] productList = {"Soap", "Shampoo", "Toothpaste", "Towel", "Toothbrush"};
        double[] productPrice = {2000, 5000, 4000, 10000, 6000};

        // Inisialisasi variabel
        double totalPrice = 0.0;
        boolean memberCard = false;
        double change = 0.0;

        // Menampilkan daftar barang
        System.out.println("List of items:");
        // For untuk mendeklarasi menggunakan tipe data apa dan inisialisasi
        for (int i = 1; i < productList.length; i++) {
            System.out.println(i + ". " + productList[i] + " - Rp." + productPrice[i]);
        }

        // List untuk menyimpan barang yang akan dibeli
        int[] selectedProducts = new int[productList.length];

        // Memilih barang
        int productCode;
        do { 
            System.out.print("Select item (enter -1 to finish): ");
            productCode = input.nextInt();

            if (productCode >= 0 && productCode < productList.length) {
                System.out.print("Enter the quantity of items: ");
                int productAmount = input.nextInt();
                selectedProducts[productCode] += productAmount;
            } else if (productCode == -1) {
                break;
            } else {
                System.out.println("Invalid item code!");
            }
        } while (productCode != 0); // menggunakan indefinite loop

        // Memeriksa kartu member
        System.out.print("Do you have a membership card? (y/n): ");
        String memberResponse = input.next();
        if (memberResponse.equalsIgnoreCase("y")) {
            memberCard = true;
        }

        // Menghitung total harga
        for (int i = 0; i < selectedProducts.length; i++) {
            if (selectedProducts[i] > 0) {
                totalPrice += productPrice[i] * selectedProducts[i];
            }
        }

        // Mengaplikasikan diskon member jika ada kartu member
        if (memberCard) {
            totalPrice = totalPrice - (totalPrice * 0.1); // Diskon 10% untuk member
        }

        // Menampilkan total harga
        System.out.println("Total Price: Rp." + totalPrice);

        // Input uang pelanggan
        System.out.print("Enter the amount of money from the customer: Rp. ");
        double customerMoney = input.nextDouble();

        if (customerMoney >= totalPrice) {
            change = customerMoney - totalPrice;
            System.out.println("Thank you for your purchase. Your change is: Rp. " + change);
        } else {
            System.out.println("Sorry, your payment is not sufficient. Please try again.");
        }

        input.close();
    }
}
