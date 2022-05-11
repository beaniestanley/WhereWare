package at.tugraz.software22.ui;

import android.app.Application;

import androidx.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WherewareApplication extends Application {

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
    public static void setOrderService(OrderService testOrderService) {
        orderService = testOrderService;
    }

    @VisibleForTesting
    public static void setBackgroundExecutor(Executor testBackgroundExecutor) {
        backgroundExecutor = testBackgroundExecutor;
    }
}