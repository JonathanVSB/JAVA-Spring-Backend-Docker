package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CenterRepository centerRepository;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        // Fetch all orders from the database
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/order-assignations")
    public ResponseEntity<?> assignLogisticsCenters() {
        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");
        List<ProcessedOrderResponse> processedOrders = new ArrayList<>();

        for (Order order : pendingOrders) {
            ProcessedOrderResponse response = assignCenterToOrder(order);
            processedOrders.add(response);
        }

        return ResponseEntity.ok().body(processedOrders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        if (!order.getSize().matches("[BMS]")) {
            return ResponseEntity.badRequest().body("Invalid order size. Must be B, M, or S.");
        }

        order.setStatus("PENDING");
        orderRepository.save(order);

        return ResponseEntity.ok().body(new ProcessedOrderResponse(
                order.getId(),
                null,
                null,
                "PENDING",
                "Order created successfully in PENDING status."
        ));
    }

    private ProcessedOrderResponse assignCenterToOrder(Order order) {
        List<Center> availableCenters = centerRepository.findAvailableCenters(order.getSize());

        Center closestCenter = null;
        double minDistance = Double.MAX_VALUE;

        for (Center center : availableCenters) {
            if (center.getCurrentLoad() < center.getMaxCapacity()) {
                double distance = calculateHaversine(
                        order.getCoordinates().getLatitude(),
                        order.getCoordinates().getLongitude(),
                        center.getCoordinates().getLatitude(),
                        center.getCoordinates().getLongitude()
                );

                if (distance < minDistance) {
                    minDistance = distance;
                    closestCenter = center;
                }
            }
        }

        if (closestCenter != null) {
            closestCenter.setCurrentLoad(closestCenter.getCurrentLoad() + 1);
            centerRepository.save(closestCenter);

            order.setAssignedCenter(closestCenter.getName());
            order.setStatus("ASSIGNED");
            orderRepository.save(order);

            return new ProcessedOrderResponse(order.getId(), minDistance, closestCenter.getName(), "ASSIGNED");
        } else {
            String message = availableCenters.isEmpty()
                    ? "No available centers support the order type."
                    : "All centers are at maximum capacity.";

            return new ProcessedOrderResponse(order.getId(), null, null, "PENDING", message);
        }
    }

    private double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Earth radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public static class ProcessedOrderResponse {
        private Long orderId;
        private Double distance;
        private String assignedLogisticsCenter;
        private String status;
        private String message;

        public ProcessedOrderResponse(Long orderId, Double distance, String assignedLogisticsCenter, String status) {
            this(orderId, distance, assignedLogisticsCenter, status, null);
        }

        public ProcessedOrderResponse(Long orderId, Double distance, String assignedLogisticsCenter, String status, String message) {
            this.orderId = orderId;
            this.distance = distance;
            this.assignedLogisticsCenter = assignedLogisticsCenter;
            this.status = status;
            this.message = message;
        }

        // Getters and setters
        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String getAssignedLogisticsCenter() {
            return assignedLogisticsCenter;
        }

        public void setAssignedLogisticsCenter(String assignedLogisticsCenter) {
            this.assignedLogisticsCenter = assignedLogisticsCenter;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
