package bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.Validators.NegativeQuantity;
import bll.Validators.Validator;
import dao.AbstractDAO;
import model.Product;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The ProductBLL class handles business logic operations related to products.
 * It provides methods for CRUD operations on products, including insertion,
 * deletion, and editing.
 */
public class ProductBLL {

    private List<Validator<Product>> validators;
    private AbstractDAO<Product> productDAO;

    /**
     * Constructs a new instance of ProductBLL.
     */
    public ProductBLL() {
        validators = new ArrayList<>();
        productDAO = new AbstractDAO<>(Product.class);
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the ID of the product to find
     * @return the product with the specified ID
     * @throws NoSuchElementException if the product with the given ID is not found
     */
    public Product findAll(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id=" + id + " was not found!");
        }
        return product;
    }

    /**
     * Deletes a product from the database.
     *
     * @param product the product to delete
     * @throws SQLException if a database access error occurs
     * @throws IllegalAccessException if the product cannot be deleted due to access restrictions
     */
    public void delete(Product product) throws SQLException, IllegalAccessException {
        productDAO.delete(product);
    }

    /**
     * Inserts a new product into the database.
     *
     * @param product the product to insert
     * @throws NegativeQuantity if the quantity of the product is negative
     * @throws SQLException if a database access error occurs
     */
    public void insert(Product product) throws NegativeQuantity, SQLException {
        if (product.getQuantity() > 0 || product.getPrice() > 0) {
            productDAO.insert(product);
            showMessageDialog(null, "product inserted successfully");
        }
        else
            showMessageDialog(null, "negative quantity");
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product the product to update
     * @throws SQLException if a database access error occurs
     */
    public void edit(Product product) throws SQLException {
        productDAO.update(product);
        showMessageDialog(null, "product edited succesfully");
    }
}
