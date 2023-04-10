import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{

        File textFile = new File("basket.txt");
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Молоко", "Хлеб", "Гречка", "Яблоко", "Баранина"};
        int[] prices = {50, 14, 80, 5, 1050};
        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб.");
        }

        Basket basket = new Basket(products, prices);
        if (!textFile.exists()){
            System.out.println("Корзина не найдена, будет составлена заново");
        }
//        basket.loadFromBinFile(textFile); // не хочет принимать возвращаемое
        // здесь работает Через .bin
//        try {
//            FileInputStream fileInputStream = new FileInputStream("basket.bin");
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            Basket basketo = (Basket) objectInputStream.readObject();
//            objectInputStream.close();
//            System.out.println("Ваша корзина найдена, данные восстановлены.");
//            basketo.printCart();
//            basketo.prices = basket.prices;
//            basket = basketo;
//
//        }  catch (FileNotFoundException ignored) {
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        // здесь работает через .txt
        try {

            BufferedReader reader = new BufferedReader(new FileReader(textFile));
            LineNumberReader howLines = new LineNumberReader(new FileReader(textFile));

            int lines = 0;
            while (howLines.readLine() != null) {
                lines++;
            }

            howLines.close();
            String[] loadProducts = new String[lines];
            int[] loadPrices = new int[lines];
            int[] loadTotalSum = new int[lines];
            int i = 0;

            String line = reader.readLine();
            while (i < lines) {

                String[] parts = line.split(" ");
                String nameProducts = parts[0];
                int productCount = Integer.parseInt(parts[1]);

                loadProducts[i] = nameProducts;
                loadTotalSum[i] = productCount;

                line = reader.readLine();
                i++;
            }

            reader.close();
            Basket loadBasket = new Basket(loadProducts, loadPrices, loadTotalSum);
            System.out.println("Ваша корзина найдена, данные восстановлены.");
            loadBasket.printCart();
            loadBasket.prices = basket.prices;
            basket = loadBasket;

        } catch (FileNotFoundException e) {
        }

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