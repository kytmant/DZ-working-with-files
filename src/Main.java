import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sumProduction = 0;
        String[] products = {"Молоко", "Хлеб", "Гречневая крупа", "Яблоко", "Баранина"};
        int[] prices = {50, 14, 80, 5, 1050};

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println( (i+1) + ". "+ products[i] + " " + prices[i] + " руб./шт.");
            }

        int[] totalSum = new int[products.length];
        int[] totalCount = new int[products.length];
        for (int i = 0; i < products.length; i++) {
            totalSum[i] = 0;
            totalCount[i] = 0;
        }

        while (true) {

            System.out.println("Введите номер товара и его количество или введите 'end' для завершения программы");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]);
            int productCount = Integer.parseInt(parts[1]);
            if ((0 < productNumber) && (productNumber < products.length + 1)) {
                totalSum[productNumber - 1] += productCount * prices[productNumber - 1];
                totalCount[productNumber - 1] += productCount;
            } else {
                System.out.println("Введите корректный номер товара!");
                continue;
            }
        }

        System.out.println("Ваша корзина:\n");
        for (int i = 0; i < products.length; i++) {
            if (totalSum[i] > 0) {                                                          
                System.out.println(products[i] + " " + totalCount[i] + "шт. " + prices[i] + " руб./шт. " + totalSum[i] + " руб. в сумме.\n");
            } else {
                continue;
            }
        }
    }
}