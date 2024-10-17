package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A graphical user interface for deleting clients.
 */

public class DeleteClient extends JFrame {

    private JComboBox<Client> clientDropdown;
    private JButton deleteButton;

    public DeleteClient(ArrayList<Client> clients) {
        initializeUI(clients);
        setupDeleteButtonAction();
    }

    /**
     * Initializes the user interface components.
     * @param clients The list of clients to be displayed in the dropdown menu for selection.
     */
    private void initializeUI(ArrayList<Client> clients) {
        setTitle("Delete Client");
        setSize(600, 200);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 208, 208));

        JLabel titleLabel = new JLabel("Select a client to delete");
        titleLabel.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(58, 58, 68));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        clientDropdown = new JComboBox<>(clients.toArray(new Client[0]));
        clientDropdown.setBackground(new Color(202, 135, 135));

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
        deleteButton.setForeground(new Color(240, 235, 227));
        deleteButton.setBackground(new Color(202, 135, 135));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 208, 208));
        panel.add(titleLabel);
        panel.add(clientDropdown);
        panel.add(deleteButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Sets up the action listener for the delete button.
     * When the delete button is clicked, the selected client is deleted from the database.
     */
    private void setupDeleteButtonAction() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client selectedClient = (Client) clientDropdown.getSelectedItem();
                if (selectedClient != null) {
                    try {
                        ClientBLL client = new ClientBLL();
                        client.delete(selectedClient);
                        JOptionPane.showMessageDialog(null, "Client '" + selectedClient.getName() + "' deleted successfully.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error deleting client: " + ex.getMessage());
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a client to delete.");
                }
            }
        });
    }
}