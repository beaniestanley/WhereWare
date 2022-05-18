package at.tugraz.software22;

import android.app.Application;

import androidx.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import at.tugraz.software22.data.DummyOrderRepository;
import at.tugraz.software22.service.OrderService;

public class WhereWareApplication extends Application {

    private static OrderService orderService;
    private static Executor backgroundExecutor;

    private OrderService createOrderService() {
        return new OrderService(new DummyOrderRepository());
    }

    public OrderService getOrderService() {
        if (orderService == null) {
            orderService = createOrderService();
        }
        return orderService;
    }

    public Executor getBackgroundExecutor() {
        if (backgroundExecutor == null) {
            backgroundExecutor = Executors.newFixedThreadPool(4);
        }
        return backgroundExecutor;
    }

    @VisibleForTesting
    public static void setSprintService(OrderService testSprintService) {
        orderService = testSprintService;
    }

    @VisibleForTesting
    public static void setBackgroundExecutor(Executor testBackgroundExecutor) {
        backgroundExecutor = testBackgroundExecutor;
    }
}