package at.tugraz.software22.domain;

public class Product {

    private final Integer id_;
    Integer estimatedTime_;
    Integer productQuantity_;
    String name_;
    Statuses status_;
    String location_;

    public Product(Integer estimatedTime_, Integer productQuantity_, String name_, String location_, Integer id) {
        id_ = id;
        this.estimatedTime_ = estimatedTime_;
        this.productQuantity_ = productQuantity_;
        this.name_ = name_;
        this.status_ = Statuses.PENDING;
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

    public Statuses getStatus() {
        return status_;
    }

    public String getLocation() {
        return location_;
    }

    public Integer getId() {
        return id_;
    }

}