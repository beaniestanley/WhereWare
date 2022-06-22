package at.tugraz.software22.service;

import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.OrderRepository;
import at.tugraz.software22.domain.Product;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    public List<Order> getAll() {
        return orderRepository.getAll();
    }
    
    public Order getOrder(int id) {
        return orderRepository.getOrder(id);
    }

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void removeProduct(Integer id) {
        orderRepository.deleteProduct(id);
    }

    public List<Product> getProducts() {return orderRepository.getProducts(); }

    public void addProduct(String product_name, Integer estimated_time, String location, Integer quantity)
    {orderRepository.addProduct(product_name, estimated_time, location, quantity);}

    public void updateProduct(Integer id, String product_name, Integer estimated_time, String location, Integer quantity)
    {orderRepository.updateProduct(id, product_name, estimated_time, location, quantity); }

}
