import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {
    protected int[] prices;
    protected String[] products;
    protected int[] totalSum;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        totalSum = new int[products.length];
    }

    public Basket(String[] products, int[] prices, int[] totalSum) {
        this.products = products;
        this.prices = prices;
        this.totalSum = totalSum;
    }

    public void addToCart(int productNum, int amount) {
        if ((0 < productNum) && (productNum < products.length + 1)) {
            totalSum[productNum - 1] += amount * prices[productNum - 1];
        } else {
            System.out.println("Введите корректный номер товара!");
        }
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + totalSum[i] + " руб.");
        }
    }

    public void saveTxt(File textFile) throws IOException {
        OutputStream text = new FileOutputStream(textFile);
        for (int i = 0; i < products.length; i++) {
            text.write((products[i] + " " + totalSum[i]).getBytes());
            text.write("\n".getBytes());
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile));
             LineNumberReader howLines = new LineNumberReader(new FileReader(textFile))) {
            int lines = 0;
            while (howLines.readLine() != null) {
                lines++;
            }

            howLines.close();
            String[] loadProducts = new String[lines];
            int[] loadPrices = new int[lines];
            int[] totalSum = new int[lines];
            int i = 0;

            String line = reader.readLine();
            while (i < lines) {

                String[] parts = line.split(" ");
                String nameProducts = parts[0];
                int productCount = Integer.parseInt(parts[1]);

                loadProducts[i] = nameProducts;
                totalSum [i] = productCount;

                if (productCount != 0) {
                    loadPrices[i] = productCount / (productCount / Main.prices[i]);
                } else {
                    loadPrices[i] = Main.prices[i];
                }

                line = reader.readLine();
                i++;
            }

            reader.close();
            Basket loadBasket = new Basket(loadProducts, loadPrices, totalSum);
            System.out.println("Ваша корзина найдена, данные восстановлены.");
            loadBasket.printCart();
            return loadBasket;

        } catch (FileNotFoundException ignored) {
        }
        return null;
        }

    public void saveJson (File textFile){
        try (PrintWriter writer = new PrintWriter(textFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            writer.print(json);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJsonFile(File file) {
        Basket basket = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            StringBuilder builder = new StringBuilder();
            String line ;
            while ((line = reader.readLine()) !=null){
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);
        } catch (IOException e) {
        }
        System.out.println("Ваша корзина найдена, данные восстановлены.");
        return basket;
    }
}




