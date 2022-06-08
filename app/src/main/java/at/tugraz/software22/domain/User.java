package at.tugraz.software22.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    Integer id_;
    String forename;
    String lastname;
    Integer avgCollectionTime;
    List<Order> completedOrders;


    public User(Integer id_, String forName, String lastName) {
        this.id_ = id_;
        this.forename = forName;
        this.lastname = lastName;
        this.avgCollectionTime = 0;
        this.completedOrders = new ArrayList<>();
    }

    public Integer getId() {
        return id_;
    }

    public String getForename() {
        return forename;
    }

    public String getLastname() {
        return lastname;
    }

    public List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public void setFirstName(String firstName) {
        this.forename = firstName;
    }
    public void setLastName(String lastName) {
        this.lastname = lastName;
    }
}
