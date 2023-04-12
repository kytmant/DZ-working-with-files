import java.io.*;

public class Basket implements Serializable {
    protected static int[] prices;
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
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile));
             LineNumberReader howLines = new LineNumberReader(new FileReader(textFile))) {
            int lines = 0;
            while (howLines.readLine() != null) {
                lines++;
            }
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
            Basket loadBasket = new Basket(loadProducts, loadPrices, loadTotalSum);
            System.out.println("Ваша корзина найдена, данные восстановлены.");
            loadBasket.printCart();
            return loadBasket;
        }
    }

    public void saveBin(File file) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            int[] saveSum = this.totalSum;
            Basket basketo = new Basket(saveSum, products);
            objectOutputStream.writeObject(basketo);
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("basket.bin"))) {
            Basket basketo = (Basket) objectInputStream.readObject();
            System.out.println("Ваша корзина найдена, данные восстановлены.");
            basketo.printCart();
            return basketo;
        }catch (FileNotFoundException e) {
            System.out.println("Файл корзины не найден. Корзина будет составлена заново");
        }
        return null;
    }
}




