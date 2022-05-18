package at.tugraz.software22.service;

import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.OrderRepository;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    public List<Order> getAll() {
        return orderRepository.getAll();
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
//
//    public void changeStateOfProductInOrder(Integer orderId,Integer productId, String status){
//        List<Order> allOrders = orderRepository.getAll();
//        Product product = allOrders.get(orderId-1).getAllOrders_().get(productId-1);
//        switch (status){
//            case "pending":
//                product.setStatus(Statuses.PENDING);
//                break;
//            case "started":
//                product.setStatus(Statuses.STARTED);
//                break;
//            case "finished":
//                product.setStatus(Statuses.FINISHED);
//                break;
//        }
//    }
}
