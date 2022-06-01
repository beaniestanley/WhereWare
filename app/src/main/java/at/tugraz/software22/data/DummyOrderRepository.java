package at.tugraz.software22.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.OrderRepository;
import at.tugraz.software22.domain.Product;

public class DummyOrderRepository implements OrderRepository {
    private final List<Order> orders;

    public DummyOrderRepository() {
        orders = new ArrayList<>();

        Product firstProduct = new Product(200, 1, "Xbox One", "Aisle 1", 1);
        Product secondProduct = new Product(100, 2, "Xbox Controller", "Aisle 2", 2);
        Product thirdProduct = new Product(50, 1, "Xbox HDMI Adapter", "Aisle 3", 3);
        Product fourthProduct = new Product(250, 1, "Nintendo Switch", "Aisle 1", 4);
        Product fifthProduct = new Product(350, 2, "PS4 PRO", "Aisle 2", 5);
        Product sixthProduct = new Product(150, 1, "PS5 SLIM", "Aisle 1", 6);

        List<Integer> value;

        List<Product> productsOrderOne = new ArrayList<>(Arrays.asList(firstProduct, secondProduct, thirdProduct));
        value = getTimeAndQuantity(productsOrderOne);
        orders.add(new Order(value.get(0), value.get(1), productsOrderOne, 1));
        orders.get(0).startOrder();
        orders.get(0).setStartTime(LocalDateTime.of(2022, 5, 18, 12, 1,0));
        orders.get(0).finishOrder();

        List<Product> productsOrderTwo = new ArrayList<>(Arrays.asList(fifthProduct, firstProduct, sixthProduct));
        value = getTimeAndQuantity(productsOrderTwo);
        orders.add(new Order(value.get(0), value.get(1), productsOrderTwo, 2));

        List<Product> productsOrderThree = new ArrayList<>(Arrays.asList(fifthProduct, fourthProduct, sixthProduct));
        value = getTimeAndQuantity(productsOrderThree);
        orders.add(new Order(value.get(0), value.get(1), productsOrderThree, 3));

        List<Product> productsOrderFour = new ArrayList<>(Arrays.asList(fifthProduct, fourthProduct, sixthProduct));
        value = getTimeAndQuantity(productsOrderFour);
        orders.add(new Order(value.get(0), value.get(1), productsOrderFour, 3));
    }

    private ArrayList<Integer> getTimeAndQuantity(List<Product> products) {
        AtomicReference<Integer> productQuantity = new AtomicReference<>(0);
        AtomicReference<Integer> estimatedTime = new AtomicReference<>(0);
        products.forEach(elem -> productQuantity.updateAndGet(v -> v + elem.getProductQuantity()));
        products.forEach(elem -> estimatedTime.updateAndGet(v -> v + elem.getEstimatedTime()));
        return new ArrayList<>(Arrays.asList(productQuantity.get(), estimatedTime.get()));
    }

    public List<Order> getAll() {
        return orders;
    }
    
    public Order getOrder(int id) {
        for(Order o: orders) {
            if(o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}