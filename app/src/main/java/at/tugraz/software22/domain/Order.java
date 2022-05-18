package at.tugraz.software22.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Order {
    private Integer id_;
    List<Product> allOrders_ = new ArrayList<>();
    Integer productQuantity_ = 0;
    Integer estimatedTime_ = 0;
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime;
    Statuses status = Statuses.PENDING;

    public Order(){
        id_ = 1;
    }

    public Order(Integer productQuantity, Integer estimatedTime, List<Product> products, Integer id){
        id_ = id;
        productQuantity_ = productQuantity;
        estimatedTime_ = estimatedTime;
        allOrders_ = products;
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

    public Statuses getStatus() {
        return status;
    }

    public Integer getId() {
        return id_;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getCollectionTime() {
        return Duration.between(startTime, endTime);
    }
}