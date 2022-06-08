package at.tugraz.software22.service;

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
