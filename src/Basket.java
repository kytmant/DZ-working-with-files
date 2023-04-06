import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable{
    protected static int[] prices;
    protected static String[] products;
    protected static int[] totalSum;

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

    public Basket(int[] totalSum, String[] products) {
        this.products = products;
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
            int i = 0;

            String line = reader.readLine();
            while (i < lines) {

                String[] parts = line.split(" ");
                String nameProducts = parts[0];
                int productCount = Integer.parseInt(parts[1]);

                loadProducts[i] = nameProducts;
                totalSum[i] = productCount;

                if (productCount != 0) {
                    loadPrices[i] = productCount / (productCount / prices[i]);
                } else {
                    loadPrices[i] = prices[i];
                }

                line = reader.readLine();
                i++;
            }

            reader.close();
            Basket loadBasket = new Basket(loadProducts, loadPrices, totalSum);
            System.out.println("Ваша корзина найдена, данные восстановлены.");
            loadBasket.printCart();
            return loadBasket;

        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public void saveBin (File file){
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            int [] saveSum = this.totalSum;
            Basket basketo = new Basket(saveSum, products);
            objectOutputStream.writeObject(basketo);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(File file){
        try {
            FileInputStream fileInputStream = new FileInputStream("basket.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Basket basketo = (Basket) objectInputStream.readObject();

            String[] loadProducts = new String[products.length];
            int[] loadPrices = new int[products.length];
            int[] loadSum = new int[products.length];
            int i = 0;

            while (i < products.length) {
                loadProducts[i] = basketo.products[i];
                loadSum[i] = basketo.totalSum[i];
                loadPrices[i] = basketo.prices[i];
                i++;
            }
            objectInputStream.close();

            return basketo;

        }  catch (IOException i) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}



