package at.tugraz.software22.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.data.OrderRepository;

public class OrderService {
    public final OrderRepository orderRepository;

    public OrderService (OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        return orderRepository.getAll();
    }
}
