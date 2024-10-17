package presentation;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The ProductOperations class represents the window for product operations.
 */
public class ProductOperations extends JFrame {

    public ProductOperations() {
        setTitle("Product Operations");
        setSize(700, 200);
        setLocationRelativeTo(null);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panel.setBackground(new Color(255, 208, 208));

        addButton(panel, "Add Product");
        addButton(panel, "Delete Product");
        addButton(panel, "Edit Product");
        addButton(panel, "View Products");

        getContentPane().add(panel);
    }

    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(new Color(202, 135, 135));
        button.setForeground(new Color(240, 235, 227));
        button.setFont(new Font("TT Octosquares Trl", Font.BOLD, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Add Product")) {
                    new AddProduct();
                } else if (text.equals("View Products")) {
                    ArrayList<Product> products = ProductDAO.getAllProducts();
                    new ListAll<Product>().generateTable(products);
                } else if (text.equals("Edit Product")) {
                    ArrayList<Product> products = ProductDAO.getAllProducts();
                    new UpdateProduct(products);
                }
                else if (text.equals("Delete Product")) {
                    ArrayList<Product> products = ProductDAO.getAllProducts();
                    new DeleteProduct(products);
                }
            }
        });
        panel.add(button);
    }

}
