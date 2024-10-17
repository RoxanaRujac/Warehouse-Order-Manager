package bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.Validators.EmailValidator;
import bll.Validators.Validator;
import dao.AbstractDAO;
import model.Client;

/**
 * The ClientBLL class handles business logic operations related to clients.
 * It provides methods for finding clients by ID, retrieving all clients,
 * deleting, inserting, and editing clients in the database, and validating client data.
 */


import static javax.swing.JOptionPane.showMessageDialog;

public class ClientBLL {
    private List<Validator<Client>> validators;
    private AbstractDAO<Client> clientDAO;

    /**
     * Constructs a new instance of ClientBLL.
     * Initializes the list of validators and the client DAO.
     */
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        clientDAO = new AbstractDAO<Client>(Client.class);
    }

    /**
     * Finds a client by ID.
     *
     * @param id the ID of the client to find
     * @return the found client
     * @throws NoSuchElementException if no client with the specified ID is found
     */
    public Client findbyID(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id=" + id + " was not found!");
        }
        return client;
    }

    /**
     * Retrieves all clients.
     *
     * @return a list of all clients
     */
    public List<Client> findAll(){
        return clientDAO.findAll();
    }

    /**
     * Deletes a client.
     *
     * @param client the client to delete
     * @throws SQLException if a database access error occurs
     * @throws IllegalAccessException if the client cannot be accessed
     */
    public void delete(Client client) throws SQLException, IllegalAccessException {
        clientDAO.delete(client);
    }

    /**
     * Inserts a new client into the database.
     *
     * @param client the client to insert
     * @throws SQLException if a database access error occurs
     */
    public void insert(Client client) throws SQLException {
        validate(client);

        clientDAO.insert(client);
        showMessageDialog(null, "client inserted successfully");
    }

    /**
     * Edits an existing client in the database.
     *
     * @param client the client to edit
     * @throws SQLException if a database access error occurs
     */
    public void edit(Client client) throws SQLException {
        validate(client);

        clientDAO.update(client);
        showMessageDialog(null, "client edited successfully");
    }

    /**
     * Validates a client's data.
     *
     * @param client the client to validate
     */
    public void validate(Client client) {
        EmailValidator v = new EmailValidator();
        v.validate(client);
    }
}
