package model;

/**
 * A class representing a client with properties such as client ID, name, email, address, and phone number.
 */

public class Client {
    private int clientId;
    private String name;
    private String email;
    private String address;
    private String phone;

    public Client(){}
    public Client(int clientId, String name, String email, String address, String phone) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return  clientId +
                " | " + name +
                " | " + email +
                " | " + address +
                " | " + phone;
    }
}
