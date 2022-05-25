package at.tugraz.software22.domain;

import java.util.List;

public interface OrderRepository {

    List<Order> getAll();
    Order getOrder(int id);
    void addOrder(Order order);
}
