import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{

        File textFile = new File("File.txt");
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Молоко", "Хлеб", "Гречка", "Яблоко", "Баранина"};
        int[] prices = {50, 14, 80, 5, 1050};
        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб.");
        }

        Basket basket = new Basket(products, prices);
        if (!textFile.exists()){
            System.out.println("Корзина не найдены, будет составлена заново");
        }
        basket.loadFromTxtFile(textFile);

        while (true) {
            System.out.println("Введите номер товара и его количество, 'basket' для вывода вашей корзины, 'end' для завершения программы");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                basket.printCart();
                basket.saveTxt(textFile);
                break;
            }
            if (input.equals("basket")){
                basket.printCart();
                continue;
            }

            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]);
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);

        }
    }
}