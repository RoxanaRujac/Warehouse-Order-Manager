package dao;
import model.Product;

import java.sql.*;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) class for interacting with the Product table in the database.
 * Extends the AbstractDAO class.
 */
public class ProductDAO extends AbstractDAO<Product>{
    public ProductDAO(Class<Product> type) {
        super(type);
    }

    /**
     * Retrieves all products from the database.
     *
     * @return An ArrayList of Product objects representing all products in the database.
     */
    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/warehousedb";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM product";

            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("productId");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        double price = resultSet.getDouble("price");
                        double quantity = resultSet.getDouble("quantity");
                        String location = resultSet.getString("location");
                        Product product = new Product(id, name, description, price, quantity, location);
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
