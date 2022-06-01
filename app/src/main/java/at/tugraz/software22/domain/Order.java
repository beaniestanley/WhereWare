package at.tugraz.software22.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Integer id_;
    List<Product> allOrders_ = new ArrayList<>();
    Integer productQuantity_ = 0;
    Integer estimatedTime_ = 0;
    LocalDateTime startTime;
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

    public List<Product> getAllProducts_() {
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getCollectionTime() {
        return Duration.between(startTime, endTime);
    }

    public LocalDate getLocalDate() {
        if(endTime == null)
            return null;

        return endTime.toLocalDate();
    }

    public void startOrder() {
        this.status = Statuses.STARTED;
        this.startTime = LocalDateTime.now();
    }

    public void finishOrder() {
        this.status = Statuses.FINISHED;
        this.endTime = LocalDateTime.now();
    }

    public void addProduct(Product product) {
        allOrders_.add(product);
    }
}