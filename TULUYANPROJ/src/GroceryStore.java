import java.util.ArrayList;

public class GroceryStore {

    // Attributes
    private String storeName;
    private ArrayList<Product> products;
    private int nextProductId;

    // Constructor
    public GroceryStore(String storeName) {

        this.storeName = storeName;
        products = new ArrayList<>();
        nextProductId = 1;
    }

    // Adds a new product
    public Product addProduct(
        String name,
        String category,
        double price,
        int stock
    ) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Product name cannot be empty."
            );
        }

        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Category cannot be empty."
            );
        }

        if (!Double.isFinite(price) || price <= 0) {
            throw new IllegalArgumentException(
                "Price must be greater than zero."
            );
        }

        if (stock < 0) {
            throw new IllegalArgumentException(
                "Stock cannot be negative."
            );
        }

        // Prevent duplicate product names
        for (Product product : products) {

            if (product.getName().equalsIgnoreCase(name.trim())) {
                throw new IllegalArgumentException(
                    "A product with that name already exists."
                );
            }
        }

        Product product = new Product(
            nextProductId++,
            name.trim(),
            category.trim(),
            price,
            stock
        );

        products.add(product);

        return product;
    }

    // Searches for a product using its ID
    public Product findProductById(int id) {

        for (Product product : products) {

            if (product.getProductId() == id) {
                return product;
            }
        }

        return null;
    }

    // Searches products by ID or name
    public ArrayList<Product> searchProducts(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Search keyword cannot be empty."
            );
        }

        ArrayList<Product> results = new ArrayList<>();

        String search = keyword.trim().toLowerCase();

        for (Product product : products) {

            if (
                String.valueOf(product.getProductId()).equals(search)
                || product.getName().toLowerCase().contains(search)
            ) {

                results.add(product);
            }
        }

        return results;
    }

    // Updates the stock of a product
    public void updateProductStock(int id, int newStock) {

        Product product = findProductById(id);

        if (product == null) {
            throw new IllegalArgumentException(
                "Product ID not found."
            );
        }

        product.updateStock(newStock);
    }

    // Deletes a product
    public void deleteProduct(int id) {

        Product product = findProductById(id);

        if (product == null) {
            throw new IllegalArgumentException(
                "Product ID not found."
            );
        }

        products.remove(product);
    }

    // Displays all products
    public void displayInventory() {

        if (products.isEmpty()) {

            System.out.println(
                "No products in the inventory yet."
            );

            return;
        }

        System.out.println(
            "\n---------------- PRODUCT INVENTORY ----------------"
        );

        System.out.printf(
            "%-5s %-20s %-18s %-12s %-8s%n",
            "ID", "NAME", "CATEGORY", "PRICE", "STOCK"
        );

        System.out.println(
            "-------------------------------------------------------------------"
        );

        for (Product product : products) {

            // Price is turned into a String first so the column stays aligned
            String price = String.format("PHP %.2f", product.getPrice());

            System.out.printf(
                "%-5d %-20s %-18s %-12s %-8d%n",
                product.getProductId(),
                shorten(product.getName(), 20),
                shorten(product.getCategory(), 18),
                price,
                product.getStock()
            );
        }
    }

    // Displays products with five or fewer stocks
    public void displayLowStockProducts() {

        boolean found = false;

        System.out.println(
            "\n------------- LOW STOCK PRODUCTS -------------"
        );

        for (Product product : products) {

            if (product.isLowStock()) {

                System.out.println(
                    product.displayDetails() + " [LOW STOCK]"
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println(
                "There are no low-stock products."
            );
        }
    }

    // Shortens long text for table display
    private String shorten(String text, int maxLength) {

        if (text.length() <= maxLength) {
            return text;
        }

        return text.substring(0, maxLength - 3) + "...";
    }

    // Getters
    public String getStoreName() {
        return storeName;
    }

    public int getProductCount() {
        return products.size();
    }
}