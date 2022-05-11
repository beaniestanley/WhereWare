package at.tugraz.software22.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    List<Product> allOrders_ = new ArrayList<>();
    Integer productQuantity_ = 0;
    Integer estimatedTime_ = 0;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String status = "started";

    public Order(){}

    public Order(Integer productQuantity, Integer estimatedTime, List<Product> products){
        productQuantity_ = productQuantity;
        estimatedTime_ = estimatedTime;
        allOrders_ = products;
        status = "started";
    }

    public List<Product> getAllOrders_() {
        return allOrders_;
    }

    public Integer getProductQuantity_() {
        return productQuantity_;
    }

    public Integer getEstimatedTime_() {
        return estimatedTime_;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}