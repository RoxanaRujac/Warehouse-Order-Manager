package bll;

import bll.Validators.NegativeQuantity;
import bll.Validators.QuantityValidator;
import bll.Validators.Validator;
import dao.AbstractDAO;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The OrderBLL class handles business logic operations related to orders.
 * It provides methods for inserting orders, including validation of quantity,
 * updating product quantities, and generating bills.
 */
public class OrderBLL {

    /**
     * Constructs a new instance of OrderBLL.
     */
    public OrderBLL() {
    }

    /**
     * Inserts a new order into the database.
     *
     * @param client the client placing the order
     * @param product the product being ordered
     * @param quantity the quantity of the product being ordered
     * @throws SQLException if a database access error occurs
     * @throws QuantityValidator if the quantity is not valid
     * @throws NegativeQuantity if the quantity is negative
     */
    public void insert(Client client, Product product, int quantity) throws SQLException, QuantityValidator, NegativeQuantity {
        // Check if quantity is positive
        if (quantity <= 0) {
            showMessageDialog(null, "quantity must be a positive number");
        }

        // Check if quantity is under stock
        else if (quantity <= product.getQuantity() && quantity > 0) {
            Order order = new Order();
            order.setClientId(client.getClientId());
            order.setProductId(product.getProductId());
            order.setTotalPrice(product.getPrice() * quantity);
            order.setOrderDate(String.valueOf(LocalDateTime.now()));

            // Insert bill for the order
            insertBill(order, client, product, quantity);

            try {
                AbstractDAO<Order> orderDAO = new AbstractDAO<>(Order.class);
                orderDAO.insert(order);
                showMessageDialog(null, "order placed successfully");

                // Update product quantity
                ProductBLL productBLL =  new ProductBLL();
                productBLL.edit(product);
            } catch (SQLException e) {
                throw e;
            }
        } else {
            // Validate quantity
            Validator<Integer> v = new QuantityValidator();
            v.validate(quantity);
            showMessageDialog(null, "under stock");

        }
    }

    /**
     * Inserts a bill for an order into the database.
     *
     * @param order the order for which to generate the bill
     * @param client the client placing the order
     * @param product the product being ordered
     * @param quantity the quantity of the product being ordered
     * @throws SQLException if a database access error occurs
     */
    public void insertBill(Order order, Client client, Product product, int quantity) throws SQLException {
        Bill bill =  new Bill(LocalDateTime.now(), client.getName(), product.getName(), order.getTotalPrice(), quantity);
        try {
            AbstractDAO<Bill> billDAO = new AbstractDAO<>(Bill.class);
            billDAO.insert(bill);

            // Update product quantity
            product.setQuantity(product.getQuantity() - quantity);
            AbstractDAO<Product> productDAO = new AbstractDAO<>(Product.class);
            productDAO.update(product);
        } catch (SQLException e) {
            throw e;
        }
    }
}
