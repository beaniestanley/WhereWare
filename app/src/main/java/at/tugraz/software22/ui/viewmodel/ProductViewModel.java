package at.tugraz.software22.ui.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.WhereWareApplication;


public class ProductViewModel extends AndroidViewModel {
    private final OrderService orderService;
    private final Executor executor;

    private final MutableLiveData<List<Product>> orderLiveData = new MutableLiveData<>();

    public ProductViewModel(Application application) {
        super(application);

        WhereWareApplication wherewareApplication = (WhereWareApplication) application;
        orderService = wherewareApplication.getOrderService();
        executor = wherewareApplication.getBackgroundExecutor();
    }

    public LiveData<List<Product>> getOrderLiveData() {
        return orderLiveData;
    }

    public void loadData(int order_id) {
        executor.execute(() -> {
            Order order = orderService.getOrder(order_id);
            orderLiveData.postValue(order.getAllProducts_());
        });
    }

    public void loadProductData() {
        executor.execute(() -> {
            List<Product> products = orderService.getProducts();
            orderLiveData.postValue(products);
        });
    }

    public Order getOrder(int order_id)
    {
        return orderService.getOrder(order_id);
    }

    public void removeProduct(Integer id) {
        executor.execute(() -> {
            orderService.removeProduct(id);
            List<Product> products = orderService.getProducts();
            this.orderLiveData.postValue(products);
        });
    }

    public void createProduct(String product_name, Integer estimated_time, String location, Integer quantity) {
        executor.execute(() -> {
            orderService.addProduct(product_name, estimated_time, location, quantity);
            List<Product> products = orderService.getProducts();
            this.orderLiveData.postValue(products);
        });
    }

    public void updateProduct(Integer id, String product_name, Integer estimated_time, String location, Integer quantity) {
        executor.execute(() -> {
            orderService.updateProduct(id, product_name, estimated_time, location, quantity);
            List<Product> products = orderService.getProducts();
            this.orderLiveData.postValue(products);
        });
    }
    public boolean tickProduct(int orderId, int productId)
    {
        if(orderService.tickProduct(orderId, productId))
        {
            executor.execute(() -> {
                orderLiveData.postValue(orderService.getOrder(orderId).getAllProducts_());
                System.out.println("Getting: " + orderService.getOrder(orderId).getAllProducts_().get(1));
                System.out.println("Value: " + orderService.getOrder(orderId).getAllProducts_().get(1).getStatus());
            });

            return true;
        }
        return false;
    }
}
