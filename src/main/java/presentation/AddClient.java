package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * A graphical user interface for adding client information.
 */

public class AddClient extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(255, 208, 208);
    private static final Color BUTTON_COLOR = new Color(202, 135, 135);
    private static final Color TEXT_COLOR = new Color(58, 58, 68);
    private static final Color FIELD_COLOR = new Color(202, 135, 135);

    Font font = new Font("TT Octosquares Trl", Font.PLAIN, 14);
    private JLabel lblTitle, lblClientId, lblName, lblEmail, lblAddress, lblPhone;
    private JTextField txtClientId, txtName, txtEmail, txtAddress, txtPhone;
    private JButton btnAddClient;

    public AddClient() {
        initializeFrame();
        initializeComponents();
        addComponentsToFrame();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Client Information");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void initializeComponents() {
        lblTitle = new JLabel("Insert Client Information:");
        lblTitle.setForeground(TEXT_COLOR);
        lblClientId = new JLabel("Client ID:");
        lblClientId.setForeground(TEXT_COLOR);
        lblName = new JLabel("Name:");
        lblName.setForeground(TEXT_COLOR);
        lblEmail = new JLabel("Email:");
        lblEmail.setForeground(TEXT_COLOR);
        lblAddress = new JLabel("Address:");
        lblAddress.setForeground(TEXT_COLOR);
        lblPhone = new JLabel("Phone:");
        lblPhone.setForeground(TEXT_COLOR);

        txtClientId = new JTextField(20);
        txtClientId.setBackground(FIELD_COLOR);
        txtName = new JTextField(20);
        txtName.setBackground(FIELD_COLOR);
        txtEmail = new JTextField(20);
        txtEmail.setBackground(FIELD_COLOR);
        txtAddress = new JTextField(20);
        txtAddress.setBackground(FIELD_COLOR);
        txtPhone = new JTextField(20);
        txtPhone.setBackground(FIELD_COLOR);

        btnAddClient = new JButton("Add Client");
        btnAddClient.setBackground(BUTTON_COLOR);
        btnAddClient.setForeground(TEXT_COLOR);

        lblTitle.setFont(font);
        lblClientId.setFont(font);
        lblName.setFont(font);
        lblEmail.setFont(font);
        lblAddress.setFont(font);
        lblPhone.setFont(font);
        txtClientId.setFont(font);
        txtName.setFont(font);
        txtEmail.setFont(font);
        txtAddress.setFont(font);
        txtPhone.setFont(font);
        btnAddClient.setFont(font);
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
        add(lblClientId, gbc);
        gbc.gridy++;
        add(lblName, gbc);
        gbc.gridy++;
        add(lblEmail, gbc);
        gbc.gridy++;
        add(lblAddress, gbc);
        gbc.gridy++;
        add(lblPhone, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(txtClientId, gbc);
        gbc.gridy++;
        add(txtName, gbc);
        gbc.gridy++;
        add(txtEmail, gbc);
        gbc.gridy++;
        add(txtAddress, gbc);
        gbc.gridy++;
        add(txtPhone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAddClient, gbc);

        btnAddClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!txtClientId.getText().isEmpty() && !txtName.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtAddress.getText().isEmpty() && !txtPhone.getText().isEmpty()) {
                    String clientId = txtClientId.getText();
                    String name = txtName.getText();
                    String email = txtEmail.getText();
                    String address = txtAddress.getText();
                    String phone = txtPhone.getText();

                    ClientBLL clientBLL = new ClientBLL();
                    Client client = new Client(Integer.parseInt(clientId), name, email, address, phone);

                    try {
                        clientBLL.insert(client);
                    } catch (IllegalArgumentException error) {
                        JOptionPane.showMessageDialog(null, "wrong email", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
    }
}

