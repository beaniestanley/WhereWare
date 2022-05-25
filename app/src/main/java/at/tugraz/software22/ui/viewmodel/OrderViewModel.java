package at.tugraz.software22.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.WhereWareApplication;

public class OrderViewModel extends AndroidViewModel {

    private final OrderService orderService;
    private final Executor executor;

    private final MutableLiveData<List<Order>> orderLiveData = new MutableLiveData<>();

    public OrderViewModel(Application application){
        super(application);
        WhereWareApplication whereWareApplication = (WhereWareApplication) getApplication();
        orderService = whereWareApplication.getOrderService();
        executor = whereWareApplication.getBackgroundExecutor();
    }

    public LiveData<List<Order>> getOrderLiveData() {return orderLiveData;}

    public void loadData() {
        executor.execute(() -> {
            List<Order> orders = orderService.getAll();
            this.orderLiveData.postValue(orders);
        });
    }
}
