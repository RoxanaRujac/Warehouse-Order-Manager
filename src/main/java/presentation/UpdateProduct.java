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
 * The UpdateProduct class represents the window for updating product information.
 */
public class UpdateProduct extends JFrame {

    private JComboBox<String> productDropdown;
    private JComboBox<String> attributeDropdown;
    private JTextField newValueField;
    private JButton updateButton;
    private ProductBLL productBLL;

    public UpdateProduct(ArrayList<Product> products) {
        setTitle("Update Product");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        productBLL = new ProductBLL();

        initializeComponents(products);
        addComponents();
        setVisible(true);
    }

    private void initializeComponents(ArrayList<Product> products) {
        ArrayList<String> productNames = new ArrayList<>();
        for (Product product : products) {
            productNames.add(product.getName());
        }

        productDropdown = new JComboBox<>(productNames.toArray(new String[0]));
        attributeDropdown = new JComboBox<>(new String[]{"ID", "Name", "Description", "Price", "Quantity", "Location"});
        newValueField = new JTextField(20);
        updateButton = new JButton("Update");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductName = (String) productDropdown.getSelectedItem();
                String selectedAttribute = (String) attributeDropdown.getSelectedItem();
                String newValue = newValueField.getText();

                if (selectedProductName == null || newValue.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a product and enter a new value.");
                    return;
                }

                Product selectedProduct = null;
                for (Product product : products) {
                    if (product.getName().equals(selectedProductName)) {
                        selectedProduct = product;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    updateProduct(selectedProduct, selectedAttribute, newValue);
                } else {
                    JOptionPane.showMessageDialog(null, "Selected product not found.");
                }
            }
        });
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label1 = new JLabel("Select Product:");
        JLabel label2 = new JLabel("Select Attribute:");
        JLabel label3 = new JLabel("New Value:");

        label1.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        label2.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        label3.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        label1.setForeground(new Color(58, 58, 68));
        label2.setForeground(new Color(58, 58, 68));
        label3.setForeground(new Color(58, 58, 68));

        updateButton.setBackground(new Color(202, 135, 135));
        updateButton.setForeground(new Color(240, 235, 227));
        updateButton.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 12));

        panel.add(label1);
        panel.add(productDropdown);
        panel.add(label2);
        panel.add(attributeDropdown);
        panel.add(label3);
        panel.add(newValueField);
        panel.add(new JLabel());
        panel.add(updateButton);
        panel.setBackground(new Color(255, 208, 208));

        attributeDropdown.setBackground(new Color(202, 135, 135));
        productDropdown.setBackground(new Color(202, 135, 135));
        newValueField.setBackground(new Color(202, 135, 135));

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void updateProduct(Product product, String attribute, String newValue) {
        try {
            switch (attribute.toLowerCase()) {
                case "productid":
                    JOptionPane.showMessageDialog(null, "Cannot update product ID.");
                    break;
                case "name":
                    product.setName(newValue);
                    break;
                case "description":
                    product.setDescription(newValue);
                    break;
                case "price":
                    product.setPrice(Double.parseDouble(newValue));
                    break;
                case "quantity":
                    product.setQuantity(Double.parseDouble(newValue));
                    break;
                case "location":
                    product.setLocation(newValue);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid attribute.");
                    return;
            }
            productBLL.edit(product);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating product: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
