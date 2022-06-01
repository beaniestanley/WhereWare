package at.tugraz.software22.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Statuses;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.WhereWareApplication;

public class OrderViewModel extends AndroidViewModel {

    private final OrderService orderService;
    private final Executor executor;

    private final MutableLiveData<List<Order>> openOrders = new MutableLiveData<>();
    private final MutableLiveData<List<Order>> completedOrders = new MutableLiveData<>();

    public OrderViewModel(Application application) {
        super(application);
        WhereWareApplication whereWareApplication = (WhereWareApplication) getApplication();
        orderService = whereWareApplication.getOrderService();
        executor = whereWareApplication.getBackgroundExecutor();
    }

    public LiveData<List<Order>> getOpenOrders() {
        return openOrders;
    }

    public LiveData<List<Order>> getCompletedOrders() {
        return completedOrders;
    }

    public void loadData() {
        executor.execute(() -> {
            List<Order> orders = orderService.getAll();
            this.openOrders.postValue(
                    orders.stream()
                            .filter(o -> o.getStatus() != Statuses.FINISHED).
                            collect(Collectors.toList()));

            this.completedOrders.postValue(
                    orders.stream()
                            .filter(o -> o.getStatus() == Statuses.FINISHED)
                            .collect(Collectors.toList()));
        });
    }

    public void filterCompletedOrdersAndLoad(LocalDate date) {
        executor.execute(() -> {
            List<Order> orders = orderService.getAll();

            this.completedOrders.postValue(
                    orders.stream()
                            .filter(o -> o.getStatus() == Statuses.FINISHED)
                            .filter(o -> o.getLocalDate().getMonth().equals(date.getMonth()) && o.getLocalDate().getYear() == date.getYear())
                            .collect(Collectors.toList()));
        });
    }
}
