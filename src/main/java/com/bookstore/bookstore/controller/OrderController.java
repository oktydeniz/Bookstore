package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.ApiResponseModel;
import com.bookstore.bookstore.request.OrderRequest;
import com.bookstore.bookstore.response.OrderResponse;
import com.bookstore.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping("/details/{order_id}")
    public ApiResponseModel<OrderResponse> order(@PathVariable(required = true, value = "order_id") Long orderId) {
        return service.findOrder(orderId);
    }

    @GetMapping("/{user_id}")
    public ApiResponseModel<List<OrderResponse>> orderByUser(@PathVariable(required = true, value = "user_id") Long userId) {
        return service.findOrdersByUser(userId);
    }

    @PostMapping
    public ApiResponseModel<OrderResponse> save(@RequestBody OrderRequest req) {
        return service.save(req);
    }
}
