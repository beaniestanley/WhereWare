package at.tugraz.software22.domain;

import java.util.List;

public interface OrderRepository {

    List<Order> getAll();
    Order getOrder(int id);
    void addOrder(Order order);
    void deleteProduct(Integer id);
    List<Product> getProducts();
    void addProduct(String product_name, Integer estimated_time, String location, Integer quantity);
    void updateProduct(Integer id, String product_name, Integer estimated_time, String location, Integer quantity);
}