package presentation;

import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A graphical user interface for deleting products.
 */
public class DeleteProduct extends JFrame {

    private JComboBox<Product> productDropdown;
    private JButton deleteButton;

    public DeleteProduct(ArrayList<Product> products) {
        initializeUI(products);
        setupDeleteButtonAction();
    }

    private void initializeUI(ArrayList<Product> clients) {
        setTitle("Delete Product");
        setSize(600, 200);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 208, 208));

        JLabel titleLabel = new JLabel("Select a product to delete");
        titleLabel.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(58, 58, 68));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        productDropdown = new JComboBox<>(clients.toArray(new Product[0]));
        productDropdown.setBackground(new Color(202, 135, 135));

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        deleteButton.setForeground(new Color(240, 235, 227));
        deleteButton.setBackground(new Color(202, 135, 135));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 208, 208));
        panel.add(titleLabel);
        panel.add(productDropdown);
        panel.add(deleteButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Sets up the action listener for the delete button.
     * When the delete button is clicked, the selected product is deleted from the database.
     */
    private void setupDeleteButtonAction() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = (Product) productDropdown.getSelectedItem();
                if (selectedProduct != null) {
                    try {
                        ProductBLL client = new ProductBLL();
                        client.delete(selectedProduct);
                        JOptionPane.showMessageDialog(null, "Product " + selectedProduct.getName() + "' deleted successfully.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error deleting product: " + ex.getMessage());
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to delete.");
                }
            }
        });
    }
}