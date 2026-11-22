import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private Scanner scanner;
    private GroceryStore store;

    // Constructor
    public Main() {

        scanner = new Scanner(System.in);
        store = new GroceryStore("GROCERIA");

        addSampleProducts();
    }

    // Runs the entire program
    public void run() {

        boolean running = true;

        System.out.println("========================================");
        System.out.println("                 " + store.getStoreName());
        System.out.println("          INVENTORY MANAGEMENT");
        System.out.println("========================================");

        while (running) {

            displayMenu();

            int choice = readInt(
                "Enter your choice: ", 0, 6
            );

            try {

                switch (choice) {

                    case 1:
                        viewProducts();
                        break;

                    case 2:
                        addProduct();
                        break;

                    case 3:
                        searchProduct();
                        break;

                    case 4:
                        updateStock();
                        break;

                    case 5:
                        deleteProduct();
                        break;

                    case 6:
                        viewLowStock();
                        break;

                    case 0:
                        running = false;
                        System.out.println(
                            "Thank you for using GROCERIA. Goodbye!"
                        );
                        break;
                }

            } catch (IllegalArgumentException ex) {

                System.out.println(
                    "Error: " + ex.getMessage()
                );
            }

            if (running) {
                pause();
            }
        }

        scanner.close();
    }

    // Displays the main menu
    public void displayMenu() {

        System.out.println("\n--------------- MAIN MENU ---------------");
        System.out.println("[1] View All Products");
        System.out.println("[2] Add New Product");
        System.out.println("[3] Search Product");
        System.out.println("[4] Update Product Stock");
        System.out.println("[5] Delete Product");
        System.out.println("[6] View Low Stock Products");
        System.out.println("[0] Exit");
        System.out.println("------------------------------------------");
    }

    // Views all products
    private void viewProducts() {

        store.displayInventory();
    }

    // Adds a new product
    private void addProduct() {

        System.out.println("\n--------------- ADD PRODUCT ---------------");

        String name = readNonEmpty("Product name: ");

        String category = readNonEmpty("Category: ");

        double price = readDouble("Price (PHP): ");

        int stock = readInt(
            "Initial stock: ", 0, Integer.MAX_VALUE
        );

        Product added = store.addProduct(
            name, category, price, stock
        );

        System.out.println(
            "Product added successfully. Assigned ID: "
            + added.getProductId()
        );
    }

    // Searches for a product
    private void searchProduct() {

        System.out.println("\n--------------- SEARCH PRODUCT ---------------");

        String keyword = readNonEmpty(
            "Enter product ID or name: "
        );

        ArrayList<Product> results =
            store.searchProducts(keyword);

        if (results.isEmpty()) {

            System.out.println("No matching product found.");

        } else {

            for (Product product : results) {

                System.out.println(
                    product.displayDetails()
                );
            }
        }
    }

    // Updates the stock quantity
    private void updateStock() {

        System.out.println("\n--------------- UPDATE STOCK ---------------");

        store.displayInventory();

        int id = readInt(
            "Enter product ID: ", 1, Integer.MAX_VALUE
        );

        int newStock = readInt(
            "Enter new stock quantity: ", 0, Integer.MAX_VALUE
        );

        store.updateProductStock(id, newStock);

        System.out.println("Stock updated successfully.");
    }

    // Deletes a product
    private void deleteProduct() {

        System.out.println("\n--------------- DELETE PRODUCT ---------------");

        store.displayInventory();

        int id = readInt(
            "Enter product ID to delete: ", 1, Integer.MAX_VALUE
        );

        // Check first if the ID exists before asking for confirmation
        Product product = store.findProductById(id);

        if (product == null) {

            System.out.println("Product ID not found.");
            return;
        }

        boolean confirmed = readYesNo(
            "Delete '" + product.getName() + "'? (Y/N): "
        );

        if (confirmed) {

            store.deleteProduct(id);

            System.out.println("Product deleted successfully.");

        } else {

            System.out.println("Delete cancelled.");
        }
    }

    // Displays low-stock products
    private void viewLowStock() {

        store.displayLowStockProducts();
    }

    // Adds initial sample products
    private void addSampleProducts() {

        store.addProduct(
            "Rice 1kg", "Staples", 58.00, 30
        );

        store.addProduct(
            "Canned Sardines", "Canned Goods", 25.00, 4
        );

        store.addProduct(
            "Bottled Water", "Beverages", 15.00, 25
        );
    }

    // Reads non-empty text input
    private String readNonEmpty(String prompt) {

        while (true) {

            System.out.print(prompt);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                "Input cannot be empty. Please try again."
            );
        }
    }

    // Keeps asking until the user types Y or N
    private boolean readYesNo(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y")) {
                return true;
            }

            if (input.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println(
                "Please enter Y for yes or N for no."
            );
        }
    }

    // Reads and validates whole numbers
    private int readInt(
        String prompt,
        int min,
        int max
    ) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                int value = Integer.parseInt(input);

                if (value < min || value > max) {

                    System.out.println(
                        "Enter a whole number from "
                        + min + " to " + max + "."
                    );

                } else {

                    return value;
                }

            } catch (NumberFormatException ex) {

                System.out.println(
                    "Invalid input. Please enter a whole number."
                );
            }
        }
    }

    // Reads and validates decimal numbers
    private double readDouble(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine()
                .trim()
                .replace(",", "");

            try {

                double value = Double.parseDouble(input);

                if (!Double.isFinite(value) || value <= 0) {

                    System.out.println(
                        "Price must be a valid number greater than zero."
                    );

                } else {

                    return value;
                }

            } catch (NumberFormatException ex) {

                System.out.println(
                    "Invalid price. Example: 25.50"
                );
            }
        }
    }

    // Pauses before returning to the menu
    private void pause() {

        System.out.print(
            "\nPress ENTER to return to the menu..."
        );

        scanner.nextLine();
    }

    // Program entry point
    public static void main(String[] args) {

        new Main().run();
    }
}