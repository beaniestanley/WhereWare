package at.tugraz.software22.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.OrderRepository;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.Statuses;

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

    public List<Order> getOrdersFromTimeframe(LocalDate startDate, LocalDate endDate)
    {
        List<Order> allOrders = getAll();

        List<Order> validOrders = new ArrayList<>();

        for(Order order_to_test : allOrders)
        {
            if(order_to_test.getStartTime() == null || order_to_test.getEndTime() == null)
                continue;

            if (order_to_test.getStartTime().isAfter(startDate.atStartOfDay()) && order_to_test.getEndTime().isBefore(endDate.atStartOfDay()))
            {
                validOrders.add(order_to_test);
            }
        }

        return  validOrders;
    }

    public boolean tickProduct(int order_id, int productId) {

        Order o = null;
        for(Order order : orderRepository.getAll())
        {
            if(order.getId() == order_id)
            {
                o = order;
                break;
            }
        }

        if(o == null)
        {
            System.out.println("GAG2");
            return false;
        }

        Product p = null;
        for(Product product : o.getAllProducts_())
        {
            if(product.getId() == productId)
            {
                p = product;
                break;
            }
        }

        if(p == null)
        {
            System.out.println("GAG1");
            return false;
        }

        System.out.println("GAG3");
        p.setStatus(p.getStatus() == Statuses.FINISHED ? Statuses.PENDING : Statuses.FINISHED);
        return true;
    }

    //TODO afterwards could be used for changing status of products
//    public void changeStateOfOrder(Integer id, String status){
//        List<Order> allOrders = orderRepository.getAll();
//        switch (status){
//            case "pending":
//                allOrders.get(id-1).setStatus(Statuses.PENDING);
//                break;
//            case "started":
//                allOrders.get(id-1).setStatus(Statuses.STARTED);
//                break;
//            case "finished":
//                allOrders.get(id-1).setStatus(Statuses.FINISHED);
//                break;
//        }
//    }
}
