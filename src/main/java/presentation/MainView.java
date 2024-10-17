package presentation;

import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The MainView class represents the main window of the Warehouse Managing System.
 * It provides buttons for accessing client operations, product operations, and making a new order.
 */
public class MainView extends JFrame {

    public MainView() {
        setTitle("Warehouse Managing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 208, 208));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        addClientOperationsButton(panel, constraints);
        addProductOperationsButton(panel, constraints);
        addNewOrderButton(panel, constraints);

        getContentPane().add(panel);
    }

    private void addClientOperationsButton(JPanel panel, GridBagConstraints constraints) {
        JButton clientOperationsBtn = new JButton("Client Operations");
        clientOperationsBtn.setPreferredSize(new Dimension(250, 80));
        clientOperationsBtn.setMinimumSize(new Dimension(250, 80));
        clientOperationsBtn.setMaximumSize(new Dimension(250, 80));
        clientOperationsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performClientOperations();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(clientOperationsBtn, constraints);

        clientOperationsBtn.setFont(new Font("TT Octosquares Trl", Font.BOLD, 18));
        clientOperationsBtn.setForeground(new Color(240, 235, 227));
        clientOperationsBtn.setBackground(new Color(202, 135, 135));
    }

    private void addProductOperationsButton(JPanel panel, GridBagConstraints constraints) {
        JButton productOperationsBtn = new JButton("Product Operations");
        productOperationsBtn.setPreferredSize(new Dimension(250, 80));
        productOperationsBtn.setMinimumSize(new Dimension(250, 80));
        productOperationsBtn.setMaximumSize(new Dimension(250, 80));
        productOperationsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performProductOperations();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(productOperationsBtn, constraints);

        productOperationsBtn.setFont(new Font("TT Octosquares Trl", Font.BOLD, 18));
        productOperationsBtn.setForeground(new Color(240, 235, 227));
        productOperationsBtn.setBackground(new Color(202, 135, 135));
    }

    private void addNewOrderButton(JPanel panel, GridBagConstraints constraints) {
        JButton newOrderBtn = new JButton("Make a New Order");
        newOrderBtn.setPreferredSize(new Dimension(250, 80));
        newOrderBtn.setMinimumSize(new Dimension(250, 80));
        newOrderBtn.setMaximumSize(new Dimension(250, 80));
        newOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeNewOrder();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(newOrderBtn, constraints);

        newOrderBtn.setFont(new Font("TT Octosquares Trl", Font.BOLD, 18));
        newOrderBtn.setForeground(new Color(240, 235, 227));
        newOrderBtn.setBackground(new Color(202, 135, 135));
    }

    private void performClientOperations() {
        new ClientOperations();
    }

    private void performProductOperations() {
        new ProductOperations();
    }

    private void makeNewOrder() {
        ArrayList<Client> clients = ClientDAO.getAllClients();
        ArrayList<Product> products = ProductDAO.getAllProducts();
        new MakeANewOrder(clients, products);
    }
}
