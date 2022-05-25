package at.tugraz.software22.ui.viewmodel;

import android.app.Application;

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

    public void loadData() {
        executor.execute(() -> {
            Order order = orderService.getOrder(1);
            orderLiveData.postValue(order.getAllProducts_());
        });
    }

    //If create-function needed, implement here createOrder()
}
