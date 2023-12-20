import java.util.Scanner;
/**
 * square
 */
public class square {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter N :");       
        int N = sc.nextInt();
        System.out.print("Enter number to display");
        int displayNumber = sc.nextInt();
        if (N<3) {
            System.out.println("Minimum N value");
            return;
            
        }
        printSquareNumber(N, displayNumber);
    }
    static void printSquareNumber(int N, int displayNumber){
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                // Check if it's on the outer edge of the square
                if (i == 1 || i == N || j == 1 || j == N) {
                    System.out.print(displayNumber + " ");
                } else {
                    System.out.print("  "); // Print two spaces for the empty center
                }
            }
            System.out.println();
        }
    }
    

}
    
