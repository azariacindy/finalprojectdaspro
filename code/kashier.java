import java.util.Scanner;

public class kashier {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Initialize the Scanner object
        String nama_barang;
        int jumlah_barang, harga, totalharga;
        double diskon = 0.075;
        double totalbayar;

        System.out.println("Program kasir sederhana");
        System.out.println("-----------------------");
        System.out.println("Masukan data berikut");
        System.out.print("Nama Barang= ");
        nama_barang = input.nextLine();
        System.out.print("Jumlah Barang= ");
        jumlah_barang = input.nextInt(); // Use nextInt() for integer input
        System.out.println("Anda mendapatkan diskon 7,5%");
        System.out.print("Harga Barang= "); // Added input for harga
        harga = input.nextInt(); // Use nextInt() for integer input

        totalharga = jumlah_barang * harga;
        System.out.println("Total Harga= " + totalharga); // Corrected print statement
        diskon = totalharga * diskon;
        totalbayar = totalharga - diskon;
        System.out.println("Total Bayar =" + totalbayar); // Corrected print statement
        System.out.println("-------------------------");
        System.out.println("Nota Belanja");
        System.out.println("Nama Barang =" + nama_barang);
        System.out.println("Jumlah Barang =" + jumlah_barang);
        System.out.println("Harga Barang =" + harga);
        System.out.println("Total Harga=" + totalharga); // Corrected print statement
        System.out.println("Total Bayar =" + totalbayar); // Corrected print statement
        System.out.println("-------------------------");

        input.close(); // Close the Scanner object
    }
}
