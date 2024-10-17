package dao;
import model.Client;

import java.sql.*;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) for interacting with the 'client' table in the database.
 */
public class ClientDAO extends AbstractDAO<Client>{
    public ClientDAO(Class<Client> type) {
            super(type);
        }

    /**
     * Retrieves all clients from the 'client' table in the database.
     *
     * @return An ArrayList containing all clients retrieved from the database.
     */
    public static ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/warehousedb";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM client";

            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("clientId");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        String address = resultSet.getString("address");
                        String phone = resultSet.getString("phone");
                        Client client = new Client(id, name, email, address, phone);
                        clients.add(client);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
}
