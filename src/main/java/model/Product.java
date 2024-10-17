package model;

/**
 * A class representing a product in the inventory.
 */
public class Product {
    private int productId;
    private String name;
    private String description;
    private Double price;
    private Double quantity;
    private String location;

    public Product(){};
    public Product(int productId, String name, String description, Double price, Double quantity, String location) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.location = location;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return   productId +
                " | " + name + '\'' +
                " | " + description + '\'' +
                " | " + price +
                " | " + quantity +
                " | " + location + '\'';
    }
}
