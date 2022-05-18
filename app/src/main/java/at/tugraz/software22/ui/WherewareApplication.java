package at.tugraz.software22.ui;

import android.app.Application;

import androidx.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import at.tugraz.software22.data.DummyOrderRepository;
import at.tugraz.software22.data.DummyUserRepository;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.service.UserService;

public class WherewareApplication extends Application {

    private static OrderService orderService;
    private static UserService userService;
    private static Executor backgroundExecutor;

    private OrderService createOrderService() {
        return new OrderService(new DummyOrderRepository());
    }
    private UserService createUserService() {
        return new UserService(new DummyUserRepository());
    }

    public OrderService getOrderService() {
        if (orderService == null) {
            orderService = createOrderService();
        }
        return orderService;
    }
    public UserService getUserService() {
        if (userService == null) {
            userService = createUserService();
        }
        return userService;
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
    public static void setUserService(UserService testUserService) {
        userService = testUserService;
    }
    @VisibleForTesting
    public static void setUserService(UserService testUserService) {
        userService = testUserService;
    }

    @VisibleForTesting
    public static void setBackgroundExecutor(Executor testBackgroundExecutor) {
        backgroundExecutor = testBackgroundExecutor;
    }
}