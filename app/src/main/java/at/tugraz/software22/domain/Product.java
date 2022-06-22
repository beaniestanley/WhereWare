package at.tugraz.software22.domain;

import java.io.Serializable;

public class Product implements Serializable {

    private final Integer id;
    Integer estimatedTime;
    Integer productQuantity;
    String name;
    Statuses status;
    String location;

    public Product(Integer estimatedTime, Integer productQuantity, String name, String location, Integer id) {
        this.id = id;
        this.estimatedTime = estimatedTime;
        this.productQuantity = productQuantity;
        this.name = name;
        this.status = Statuses.PENDING;
        this.location = location;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public String getName() {
        return name;
    }

    public Statuses getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public Integer getId() {
        return id;
    }

    public void setProductName(String name) {this.name = name; }

    public void setEstimatedTime(Integer estimatedTime) {this.estimatedTime = estimatedTime; }

    public void setProductQuantity(Integer quantity) {productQuantity = quantity; }

    public void setLocation(String location) {this.location = location; }

}