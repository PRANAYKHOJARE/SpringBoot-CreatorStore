package org.pranay.creatorstore.Controllers;

import org.pranay.creatorstore.dto.OrderRequestDTO;
import org.pranay.creatorstore.entities.Order;
import org.pranay.creatorstore.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO request) {
        try {
            return ResponseEntity.ok(orderService.placeOrder(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

   @PatchMapping("/{id}/status")
  public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
      try {
        Order order = orderService.updateStatus(id, body.get("status"));
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
   }
}