package at.tugraz.software22.domain;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.domain.OrderRepository;

public class ReportTest {

    @Mock
    private OrderRepository orderRepositoryMock;

    private OrderService orderService;
    List<Order> fullList;
    List<Product> emptyList;
    Order o1;
    Order o2;
    Order o3;

    @Before
    public void setUp() {
        emptyList = new ArrayList<Product>();
        fullList = new ArrayList<Order>();
        orderRepositoryMock = mock(OrderRepository.class);
        orderService = new OrderService(orderRepositoryMock);

        o1 = new Order(1, emptyList, 1);
        o2 = new Order(1, emptyList, 2);
        o3 = new Order(1, emptyList, 3);

        o1.setStartTime(LocalDateTime.of(2022, 6, 1, 0, 0, 1));
        o1.setEndTime(LocalDateTime.of(2022, 6, 2, 0, 0, 0));
        o2.setStartTime(LocalDateTime.of(2022, 6, 1, 0, 0, 1));
        o2.setEndTime(LocalDateTime.of(2022, 6, 2, 0, 0, 0));
        o3.setStartTime(LocalDateTime.of(2022, 7, 1, 0, 0, 0));
        o3.setEndTime(LocalDateTime.of(2022, 7, 2, 0, 0, 0));

        fullList.add(o1);
        fullList.add(o2);
        fullList.add(o3);

        Mockito.when(orderRepositoryMock.getAll()).thenReturn(fullList);
    }

    @Test
    public void givenTimeFrame_whenGetOrders_onlyDeliverAskedOrders() {
        List<Order> expectedList = new ArrayList<Order>();

        expectedList.add(o1);
        expectedList.add(o2);

        LocalDate startDate = LocalDate.of(2022, 6, 1);
        LocalDate endDate = LocalDate.of(2022, 6, 30);

        List<Order> actualOrders = orderService.getOrdersFromTimeframe(startDate, endDate);

        Assert.assertEquals(expectedList, actualOrders);
    }

    @Test
    public void givenTimeFrameWithoutOrders_whenGetOrders_deliverNoOrders() {
        List<Order> expectedList = new ArrayList<Order>();

        LocalDate startDate = LocalDate.of(1990, 6, 1);
        LocalDate endDate = LocalDate.of(1990, 6, 30);

        List<Order> actualOrders = orderService.getOrdersFromTimeframe(startDate, endDate);

        Assert.assertEquals(expectedList, actualOrders);
    }
}
