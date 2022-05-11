package at.tugraz.software22.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;

import at.tugraz.software22.data.DummyOrder;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.ui.WherewareApplication;

public class ProductViewModel extends AndroidViewModel {
    private final OrderService orderService;
    private final Executor executor;

    private final MutableLiveData<Order> orderLiveData = new MutableLiveData<>();

    public ProductViewModel(Application application) {
        super(application);

        WherewareApplication wherewareApplication = (WherewareApplication) application;
        orderService = wherewareApplication.getOrderService();
        executor = wherewareApplication.getBackgroundExecutor();
    }

    public LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    public void loadData() {
        executor.execute(() -> {
            Order order = orderService.getOrder(id);
            orderLiveData.postValue(order);
        });
    }

    //If create-function needed, implement here createOrder()
}
