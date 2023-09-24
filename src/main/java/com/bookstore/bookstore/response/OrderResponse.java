package com.bookstore.bookstore.response;

import com.bookstore.bookstore.entitiy.Book;
import com.bookstore.bookstore.entitiy.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {

    private BigDecimal totalPrice;
    private List<OrderBookDetail> listOfBook;
    private Date orderDate;
    private Date updateDate;

    public OrderResponse(Order order) {
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.updateDate = order.getUpdatedAt();
        this.listOfBook = order.getListOfBook().stream().map(OrderBookDetail::new).collect(Collectors.toList());
    }
}
