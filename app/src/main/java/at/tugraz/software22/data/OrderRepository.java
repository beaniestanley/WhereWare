package at.tugraz.software22.data;

import java.util.List;

import at.tugraz.software22.domain.Order;

public interface OrderRepository {

    List<Order> getAll();
}
