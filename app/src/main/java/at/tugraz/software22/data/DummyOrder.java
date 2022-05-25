package at.tugraz.software22.data;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;

public class DummyOrder extends Order {
    List<Product> allOrders_ = new ArrayList<>();
    
    public DummyOrder() {
        allOrders_ = new ArrayList<>();
        
        Product p1 = new Product(10, 1, "Xbox Controller", "Regal 1",1);
        
        allOrders_.add(p1);
    }
}
