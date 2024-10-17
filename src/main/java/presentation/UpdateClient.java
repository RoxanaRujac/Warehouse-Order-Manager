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
 * The UpdateClient class represents the window for updating client information.
 */
public class UpdateClient extends JFrame {

    private JComboBox<String> clientDropdown;
    private JComboBox<String> attributeDropdown;
    private JTextField newValueField;
    private JButton updateButton;
    private ClientBLL clientbll;

    public UpdateClient(ArrayList<Client> clients) {
        setTitle("Update Client");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        clientbll = new ClientBLL();

        initializeComponents(clients);
        addComponents();
        setVisible(true);
    }

    private void initializeComponents(ArrayList<Client> clients) {
        ArrayList<String> clientNames = new ArrayList<>();
        for (Client client : clients) {
            clientNames.add(client.getName());
        }

        clientDropdown = new JComboBox<>(clientNames.toArray(new String[0]));
        attributeDropdown = new JComboBox<>(new String[]{"ID", "Name", "Email", "Address", "Phone"});
        newValueField = new JTextField(20);
        updateButton = new JButton("Update");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClientName = (String) clientDropdown.getSelectedItem();
                String selectedAttribute = (String) attributeDropdown.getSelectedItem();
                String newValue = newValueField.getText();

                if (selectedClientName == null || newValue.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a client and enter a new value.");
                    return;
                }

                Client selectedClient = null;
                for (Client client : clients) {
                    if (client.getName().equals(selectedClientName)) {
                        selectedClient = client;
                        break;
                    }
                }

                if (selectedClient != null) {
                    updateClient(selectedClient, selectedAttribute, newValue);
                } else {
                    JOptionPane.showMessageDialog(null, "Selected client not found.");
                }
            }
        });
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label1 = new JLabel("Select Client:");
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
        panel.add(clientDropdown);
        panel.add(label2);
        panel.add(attributeDropdown);
        panel.add(label3);
        panel.add(newValueField);
        panel.add(new JLabel());
        panel.add(updateButton);
        panel.setBackground(new Color(255, 208, 208));

        attributeDropdown.setBackground(new Color(202, 135, 135));
        clientDropdown.setBackground(new Color(202, 135, 135));
        newValueField.setBackground(new Color(202, 135, 135));


        getContentPane().add(panel, BorderLayout.CENTER);
    }

    /**
     * Updates the specified client with the new value for the selected attribute.
     * @param client The client to be updated.
     * @param attribute The attribute to be updated.
     * @param newValue The new value for the attribute.
     */
    private void updateClient(Client client, String attribute, String newValue) {
        try {
            switch (attribute.toLowerCase()) {
                case "clientid":
                    JOptionPane.showMessageDialog(null, "Cannot update client ID.");
                    break;
                case "name":
                    client.setName(newValue);
                    break;
                case "email":
                    client.setEmail(newValue);
                    break;
                case "address":
                    client.setAddress(newValue);
                    break;
                case "phone":
                    client.setPhone(newValue);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid attribute.");
                    return;
            }
            clientbll.edit(client);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating client: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
