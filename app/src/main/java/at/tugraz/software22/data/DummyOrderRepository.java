package at.tugraz.software22.data;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;

public class DummyOrderRepository implements OrderRepository {

    private final List<Order> orders;

    public DummyOrderRepository() {
        orders = new ArrayList<Order>();
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(new Product(30, 2, "Pilot 0.7 Pen", "Aisle 5"));
        orders.add(new Order(2, 30, products));
    }
    @Override
    public List<Order> getAll() {
        return orders;
    }
}
