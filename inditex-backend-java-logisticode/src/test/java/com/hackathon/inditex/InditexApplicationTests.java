/*package com.hackathon.inditex;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.Repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
class InditexApplicationTests {

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // Clear database before each test
        orderRepository.deleteAll();
        centerRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(centerRepository).isNotNull();
        assertThat(orderRepository).isNotNull();
    }

    @Test
    void testCreateAndFetchCenter() {
        Center center = new Center("Center A", "B", "AVAILABLE", 0, 5, new Coordinates(47.8566, 2.3522));
        centerRepository.save(center);

        List<Center> centers = centerRepository.findAll();
        assertThat(centers).hasSize(1);
        assertThat(centers.get(0).getName()).isEqualTo("Center A");
    }

    @Test
    void testCreateAndFetchOrder() {
        Order order = new Order(101L, "B", "PENDING", null, new Coordinates(51.5074, -0.1278));
        orderRepository.save(order);

        List<Order> orders = orderRepository.findByStatus("PENDING");
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getCustomerId()).isEqualTo(101L);
    }

    @Test
    void testAssignOrderToCenter() {
        Center center = new Center("Center A", "B", "AVAILABLE", 0, 1, new Coordinates(47.8566, 2.3522));
        centerRepository.save(center);

        Order order = new Order(101L, "B", "PENDING", null, new Coordinates(47.8566, 2.3522));
        orderRepository.save(order);

        // Simulate assigning the order
        if (center.getCurrentLoad() < center.getMaxCapacity()) {
            center.setCurrentLoad(center.getCurrentLoad() + 1);
            centerRepository.save(center);

            order.setAssignedCenter(center.getName());
            order.setStatus("ASSIGNED");
            orderRepository.save(order);
        }

        // Verify results
        Order updatedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertThat(updatedOrder.getStatus()).isEqualTo("ASSIGNED");
        assertThat(updatedOrder.getAssignedCenter()).isEqualTo("Center A");
    }
}*/
