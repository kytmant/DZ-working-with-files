import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static String[] products = {"Молоко", "Хлеб", "Гречка", "Яблоко", "Баранина"};
    static int[] prices = {50, 14, 80, 5, 1050};

    public static void main(String[] args) throws Exception {

        XMLSetting setting = new XMLSetting(new File("shop.xml"));
        File loadFile = new File(setting.loadFile);
        File saveFile = new File(setting.saveFile);
        File logFile = new File(setting.logFile);

        Basket basket = createBasket(loadFile, setting.isLoad, setting.loadFormat);

        Scanner scanner = new Scanner(System.in);


        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб.");
        }

        ClientLog log = new ClientLog();

        while (true) {
            System.out.println("Введите номер товара и его количество, 'basket' для вывода вашей корзины, 'end' для завершения программы");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                if (setting.isLog) {
                    log.exportAsCSV(logFile);
                    basket.printCart();
                }
                break;
            }
            if (input.equals("basket")) {
                basket.printCart();
                continue;
            }

            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]);
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            if (setting.isLog) {
                log.log(productNumber, productCount);
            }
            if (setting.isSave) {
                switch (setting.saveFormat) {
                    case "json" -> basket.saveJson(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);
                }
            }
        }
    }

        private static Basket createBasket (File loadFile,boolean isLoad, String loadFormat) throws IOException {
            Basket basket ;
            if (isLoad && loadFile.exists()) {
                basket = switch (loadFormat) {
                    case "json" -> Basket.loadFromJsonFile(loadFile);
                    case "txt" -> Basket.loadFromTxtFile(loadFile);
                    default -> new Basket(products, prices);
                };
            } else {
                basket = new Basket(products, prices);
            }
            return basket;
        }
    }