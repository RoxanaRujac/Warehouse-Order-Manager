package presentation;

import bll.ProductBLL;
import bll.Validators.NegativeQuantity;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * A graphical user interface for adding product information.
 */

public class AddProduct extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(255, 208, 208);
    private static final Color BUTTON_COLOR = new Color(202, 135, 135);
    private static final Color TEXT_COLOR = new Color(58, 58, 68);
    private static final Color FIELD_COLOR = new Color(202, 135, 135);

    Font font = new Font("TT Octosquares Trl", Font.PLAIN, 14);
    private JLabel lblTitle, lblProductId, lblName, lblDescription, lblPrice, lblQuantity, lblLocation;
    private JTextField txtProductId, txtName, txtDescription, txtPrice, txtQuantity, txtLocation;
    private JButton btnAddProduct;

    public AddProduct() {
        initializeFrame();
        initializeComponents();
        addComponentsToFrame();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Product Information");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void initializeComponents() {
        lblTitle = new JLabel("Insert Product Information:");
        lblTitle.setForeground(TEXT_COLOR);
        lblProductId = new JLabel("Product ID:");
        lblProductId.setForeground(TEXT_COLOR);
        lblName = new JLabel("Name:");
        lblName.setForeground(TEXT_COLOR);
        lblDescription = new JLabel("Description:");
        lblDescription.setForeground(TEXT_COLOR);
        lblPrice = new JLabel("Price:");
        lblPrice.setForeground(TEXT_COLOR);
        lblQuantity = new JLabel("Quantity:");
        lblQuantity.setForeground(TEXT_COLOR);
        lblLocation = new JLabel("Location:");
        lblLocation.setForeground(TEXT_COLOR);

        txtProductId = new JTextField(20);
        txtProductId.setBackground(FIELD_COLOR);
        txtName = new JTextField(20);
        txtName.setBackground(FIELD_COLOR);
        txtDescription = new JTextField(20);
        txtDescription.setBackground(FIELD_COLOR);
        txtPrice = new JTextField(20);
        txtPrice.setBackground(FIELD_COLOR);
        txtQuantity = new JTextField(20);
        txtQuantity.setBackground(FIELD_COLOR);
        txtLocation = new JTextField(20);
        txtLocation.setBackground(FIELD_COLOR);

        btnAddProduct = new JButton("Add Product");
        btnAddProduct.setBackground(BUTTON_COLOR);
        btnAddProduct.setForeground(TEXT_COLOR);

        lblTitle.setFont(font);
        lblProductId.setFont(font);
        lblName.setFont(font);
        lblDescription.setFont(font);
        lblPrice.setFont(font);
        lblQuantity.setFont(font);
        lblLocation.setFont(font);
        txtProductId.setFont(font);
        txtName.setFont(font);
        txtDescription.setFont(font);
        txtPrice.setFont(font);
        txtQuantity.setFont(font);
        txtLocation.setFont(font);
        btnAddProduct.setFont(font);
    }

    private void addComponentsToFrame() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        add(lblProductId, gbc);
        gbc.gridy++;
        add(lblName, gbc);
        gbc.gridy++;
        add(lblDescription, gbc);
        gbc.gridy++;
        add(lblPrice, gbc);
        gbc.gridy++;
        add(lblQuantity, gbc);
        gbc.gridy++;
        add(lblLocation, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(txtProductId, gbc);
        gbc.gridy++;
        add(txtName, gbc);
        gbc.gridy++;
        add(txtDescription, gbc);
        gbc.gridy++;
        add(txtPrice, gbc);
        gbc.gridy++;
        add(txtQuantity, gbc);
        gbc.gridy++;
        add(txtLocation, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAddProduct, gbc);

        btnAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!txtProductId.getText().isEmpty() && !txtName.getText().isEmpty() && !txtDescription.getText().isEmpty() && !txtPrice.getText().isEmpty() && !txtQuantity.getText().isEmpty() && !txtLocation.getText().isEmpty()) {
                    String productId = txtProductId.getText();
                    String name = txtName.getText();
                    String description = txtDescription.getText();
                    String price = txtPrice.getText();
                    String quantity = txtQuantity.getText();
                    String location = txtLocation.getText();

                    ProductBLL productBLL = new ProductBLL();
                    Product product = new Product(Integer.parseInt(productId), name, description, Double.parseDouble(price), Double.parseDouble(quantity), location);

                    try {
                        productBLL.insert(product);
                    } catch (IllegalArgumentException error) {
                        JOptionPane.showMessageDialog(null, "no negative numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException | NegativeQuantity ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
    }
}

