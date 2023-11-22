import java.util.Scanner;

public class nyobaWir {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Daftar barang
        String[] productList = {"Detil", "Pintane", "Emerin", "Pepsudin", "Towel", "Sinsyden", "Tari", "Ultri Milk", "Kenzlir", "Cimore"};
        double[] productPrice = {18000, 20000, 20000, 10000, 40000, 11000, 5000, 7000, 9000, 10000};

        // Inisialisasi variabel
        double totalPrice = 0.0;
        boolean memberCard = false;
        double discount = 0.01;
        double change = 0.0;

        System.out.println("-----WELCOME TO SIX ELEVEN MARKET-----");
        System.out.println("-------------LIST OF ITEM-------------"); // Menampilkan daftar barang
        // For untuk mendeklarasi menggunakan tipe data apa dan inisialisasi
        for (int i = 0; i < productList.length; i++) {
            String itemFormat = "%2d. %-20s  Rp.%,8.0f";
            System.out.println(String.format(itemFormat, i + 1, productList[i], productPrice[i]));
        }

        // List untuk menyimpan barang yang akan dibeli
        int[] selectedProducts = new int[productList.length];

        // Memilih barang
        int productCode;
        do { 
            System.out.print("Select item (enter -1 to finish): ");
            productCode = input.nextInt();

            if (productCode > 0 && productCode < productList.length) {
                System.out.print("Enter the quantity of items: ");
                int productAmount = input.nextInt();
                selectedProducts[productCode] += productAmount;
            } else if (productCode == -1) {
                break;
            } else {
                System.out.println("Invalid item code!");
            }
        } while (productCode != -1); // menggunakan indefinite loop

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
                if (totalPrice >= 50000) {
                    totalPrice -= totalPrice * discount;
                }
            }
        }

        // Mengaplikasikan diskon member jika ada kartu member
        if (memberCard) {
            totalPrice -= totalPrice * discount; // Diskon 10% untuk member
        }

        // Menampilkan total harga 
        String itemFormat = String.format("Total Price RP.%,8.0f", totalPrice) ;
        System.out.println(itemFormat);

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
