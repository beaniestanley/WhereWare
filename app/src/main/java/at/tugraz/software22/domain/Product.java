package at.tugraz.software22.domain;

public class Product {


    Integer estimatedTime_ = 0;
    Integer productQuantity_ = 0;
    String name_;
    String status_;
    String location_;

    public Product(Integer estimatedTime_, Integer productQuantity_, String name_, String location_) {
        this.estimatedTime_ = estimatedTime_;
        this.productQuantity_ = productQuantity_;
        this.name_ = name_;
        this.status_ = "pending";
        this.location_ = location_;
    }

    public Integer getEstimatedTime() {
        return estimatedTime_;
    }

    public Integer getProductQuantity() {
        return productQuantity_;
    }

    public String getName() {
        return name_;
    }

    public String getStatus() {
        return status_;
    }

    public String getLocation() {
        return location_;
    }
}