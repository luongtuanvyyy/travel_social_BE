package com.app.api;

import com.app.dto.OrderDto;
import com.app.repository.OrderRepository;
import com.app.security.CurrentUser;
import com.app.security.UserPrincipal;
import com.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderApi {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody OrderDto dto, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(orderService.checkout(dto, userPrincipal));
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrder(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(orderService.getOrder((userPrincipal)));
    }

}
