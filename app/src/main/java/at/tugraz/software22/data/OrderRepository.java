package at.tugraz.software22.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;

public class OrderRepository {
    private final List<Order> orders;

    public OrderRepository() {
        orders = new ArrayList<>();

        Product firstProduct = new Product(200, 1, "Xbox One", "Aisle 1");
        Product secondProduct = new Product(100, 2, "Xbox Controller", "Aisle 2");
        Product thirdProduct = new Product(50, 1, "Xbox HDMI Adapter", "Aisle 3");
        Product fourthProduct = new Product(250, 1, "Nintendo Switch", "Aisle 1");
        Product fithProduct = new Product(350, 2, "PS4 PRO", "Aisle 2");
        Product sixthProduct = new Product(150, 1, "PS5 SLIM", "Aisle 1");

        List<Integer> value;

        List<Product> productsOrderOne = new ArrayList<>(Arrays.asList(firstProduct, secondProduct, thirdProduct));
        value = getTimeAndQuantity(productsOrderOne);
        orders.add(new Order(value.get(0), value.get(1), productsOrderOne));

        List<Product> productsOrderTwo = new ArrayList<>(Arrays.asList(fithProduct, firstProduct, sixthProduct));
        value = getTimeAndQuantity(productsOrderTwo);
        orders.add(new Order(value.get(0), value.get(1), productsOrderTwo));

        List<Product> productsOrderThree = new ArrayList<>(Arrays.asList(fithProduct, fourthProduct, sixthProduct));
        value = getTimeAndQuantity(productsOrderThree);
        orders.add(new Order(value.get(0), value.get(1), productsOrderTwo));
    }

    private ArrayList<Integer> getTimeAndQuantity(List<Product> products) {
        AtomicReference<Integer> productQuantity = new AtomicReference<>(0);
        AtomicReference<Integer> estimatedTime = new AtomicReference<>(0);
        products.stream().forEach(elem -> {
            productQuantity.updateAndGet(v -> v + elem.getProductQuantity());
        });
        products.stream().forEach(elem -> {
            estimatedTime.updateAndGet(v -> v + elem.getEstimatedTime());
        });
        return new ArrayList<Integer>(Arrays.asList(productQuantity.get(), estimatedTime.get()));
    }

    public List<Order> getOrders() {
        return orders;
    }
}
