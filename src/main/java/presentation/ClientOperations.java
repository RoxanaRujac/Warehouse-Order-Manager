package presentation;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A graphical user interface for performing various client operations.
 */
public class ClientOperations extends JFrame {

    public ClientOperations() {
        setTitle("Client Operations");
        setSize(700, 200);
        setLocationRelativeTo(null);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panel.setBackground(new Color(255, 208, 208));

        addButton(panel, "Add Client");
        addButton(panel, "Delete Client");
        addButton(panel, "Edit Client");
        addButton(panel, "View Clients");

        getContentPane().add(panel);
    }

    /**
     * Adds a button to the panel with the specified text and action listener.
     * @param panel The panel to add the button to.
     * @param text The text to be displayed on the button.
     */
    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(new Color(202, 135, 135));
        button.setForeground(new Color(240, 235, 227));
        button.setFont(new Font("TT Octosquares Trl", Font.BOLD, 15));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Add Client")) {
                    new AddClient();
                }
                else if (text.equals("View Clients")){
                    ArrayList<Client> clients = ClientDAO.getAllClients();
                    new ListAll<Client>().generateTable(clients);
                }
                else if (text.equals("Edit Client")){
                    ArrayList<Client> clients = ClientDAO.getAllClients();
                    new UpdateClient(clients);
                }
                else if (text.equals("Delete Client")){
                    ArrayList<Client> clients = ClientDAO.getAllClients();
                    new DeleteClient(clients);
                }
            }
        });
        panel.add(button);
    }

}
