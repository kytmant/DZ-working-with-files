import java.io.*;

public class Basket implements Serializable {
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
            return loadBasket;

        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public void saveBin(File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            int[] saveSum = this.totalSum;
            Basket basketo = new Basket(saveSum, products);
            objectOutputStream.writeObject(basketo);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream("basket.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Basket basketo = (Basket) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println("Ваша корзина найдена, данные восстановлены.");
            basketo.printCart();
            return basketo;

        } catch (FileNotFoundException ignored) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }return null;
    }
}




