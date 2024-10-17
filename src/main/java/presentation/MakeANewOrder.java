package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import bll.OrderBLL;
import bll.Validators.NegativeQuantity;
import bll.Validators.QuantityValidator;
import model.Client;
import model.Product;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The MakeANewOrder class represents the window for making a new order.
 */
public class MakeANewOrder extends JFrame {
    private JComboBox<Client> clientDropdown;
    private JComboBox<Product> productDropdown;
    private JTextField quantityField;
    private JButton placeOrderButton;

    JLabel clientLabel;
    JLabel productLabel;
    JLabel quantityLabel;

    public MakeANewOrder(ArrayList<Client> clients, ArrayList<Product> products) {
        setTitle("Make an order");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initializeComponents(clients, products);
        addComponents();
        setVisible(true);
    }

    private void initializeComponents(ArrayList<Client> clients, ArrayList<Product> products) {
        clientLabel = new JLabel("Select a client:");
        clientLabel.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        clientLabel.setForeground(new Color(58, 58, 68));

        clientDropdown = new JComboBox<>(clients.toArray(new Client[0]));   //empty array of clients
        clientDropdown.setBackground(new Color(202, 135, 135));

        productLabel = new JLabel("Select a product:");
        productLabel.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        productLabel.setForeground(new Color(58, 58, 68));

        productDropdown = new JComboBox<>(products.toArray(new Product[0]));
        productDropdown.setBackground(new Color(202, 135, 135));

        quantityLabel = new JLabel("Select a quantity:");
        quantityLabel.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        quantityLabel.setForeground(new Color(58, 58, 68));

        quantityField = new JTextField(10);
        quantityField.setBackground(new Color(202, 135, 135));

        placeOrderButton = new JButton("Place Order");
        placeOrderButton.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        placeOrderButton.setForeground(new Color(240, 235, 227));
        placeOrderButton.setBackground(new Color(202, 135, 135));
        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client selectedClient = (Client) clientDropdown.getSelectedItem();
                Product selectedProduct = (Product) productDropdown.getSelectedItem();
                String quantity = quantityField.getText();

                if (selectedClient != null && selectedProduct != null && !quantity.isEmpty()) {
                    int quantityValue = Integer.parseInt(quantity);
                    OrderBLL orderBLL = new OrderBLL();
                    try {
                        orderBLL.insert(selectedClient, selectedProduct, quantityValue);
                       // showMessageDialog(null, "order placed successfully");

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (QuantityValidator ex) {
                        throw new RuntimeException(ex);
                    } catch (NegativeQuantity ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "select a client, a product, and enter a quantity.");
                }
            }
        });
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 208, 208));

        panel.add(clientLabel);
        panel.add(clientDropdown);
        panel.add(productLabel);
        panel.add(productDropdown);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(new JLabel());
        panel.add(placeOrderButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}
