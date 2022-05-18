package at.tugraz.software22.domain;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class User {
    Integer id_;
    String forName;
    String lastName;
    Integer avgCollectionTime;
    List<Order> completedOrders;


    public User(Integer id_, String forName, String lastName) {
        this.id_ = id_;
        this.forName = forName;
        this.lastName = lastName;
        this.avgCollectionTime = 0;
        this.completedOrders = new ArrayList<>();
    }

    public Integer getId() {
        return id_;
    }

    public String getForName() {
        return forName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Order> getCompletedOrders() {
        return completedOrders;
    }
}
