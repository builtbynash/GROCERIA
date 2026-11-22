public class Product {

    // Stock level at or below this number is considered "low stock"
    private static final int LOW_STOCK_LIMIT = 5;

    // Attributes of a product
    private int productId;
    private String name;
    private String category;
    private double price;
    private int stock;

    // Constructor
    public Product(int productId, String name, String category,
                   double price, int stock) {

        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Updates the product stock
    public void updateStock(int newStock) {

        if (newStock < 0) {
            throw new IllegalArgumentException(
                "Stock cannot be negative."
            );
        }

        stock = newStock;
    }

    // Checks if the product has low stock
    public boolean isLowStock() {
        return stock <= LOW_STOCK_LIMIT;
    }

    // Displays product information
    public String displayDetails() {

        return String.format(
            "ID: %d | Name: %s | Category: %s | Price: PHP %.2f | Stock: %d",
            productId, name, category, price, stock
        );
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}