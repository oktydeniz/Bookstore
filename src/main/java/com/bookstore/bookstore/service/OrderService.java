package com.bookstore.bookstore.service;

import com.bookstore.bookstore.auth.JWTUserDetail;
import com.bookstore.bookstore.entitiy.Book;
import com.bookstore.bookstore.entitiy.Order;
import com.bookstore.bookstore.entitiy.User;
import com.bookstore.bookstore.enums.BookStatus;
import com.bookstore.bookstore.exception.EmptyFieldException;
import com.bookstore.bookstore.model.ApiResponseModel;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.repository.OrderRepository;
import com.bookstore.bookstore.repository.UserRepository;
import com.bookstore.bookstore.request.OrderRequest;
import com.bookstore.bookstore.response.OrderResponse;
import com.bookstore.bookstore.util.FormControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    private ArrayList<String> outOfStockBooks;
    private ExecutorService executorService;

    public ApiResponseModel<OrderResponse> save(OrderRequest req) {
        JWTUserDetail detail = getUser();
        outOfStockBooks = new ArrayList<>();
        if (detail != null) {
            User user = userRepository.findById(detail.getId()).orElse(null);
            List<Book> selectedBooks = bookRepository.findAllById(req.getBooks());
            List<Book> validBooks = addBooks(selectedBooks);
            if (user != null) {
                if (!selectedBooks.isEmpty()) {
                    Order order = new Order();
                    order.setOrderDate(new Date());
                    order.setCreatedAt(new Date());
                    order.setUser(user);
                    order.setListOfBook(validBooks);
                    BigDecimal price = findTotalPrice(validBooks);
                    try {
                        FormControl.isMinimumOrderPrice(price);
                    } catch (EmptyFieldException e) {
                        return ApiResponseModel.create(e.getMessage(), false);
                    }
                    order.setTotalPrice(price);
                    Order _order = orderRepository.save(order);
                    OrderResponse orderResponse = new OrderResponse(_order);
                    String message;
                    if (!outOfStockBooks.isEmpty()) {
                        String books = getOtOfStocksBooks();
                        message = userDetailMessage("orders.failed.for.stock", books);
                    } else {
                        message = message("order.post.success");
                    }
                    return ApiResponseModel.create(orderResponse, message, true);
                } else {
                    return ApiResponseModel.create(message("order.post.list.error"), false);
                }
            } else {
                return ApiResponseModel.create(message("order.post.user.error"), false);
            }
        }
        return ApiResponseModel.create(message("error.basic"), false);
    }

    private List<Book> addBooks(List<Book> books) {
        outOfStockBooks.clear();
        executorService = Executors.newFixedThreadPool(books.size());
        List<Book> selectedBooks = new ArrayList<>();
        List<Future<Void>> futures = new ArrayList<>();
        for (Book book : books) {
            if (book.getStockQuantity() != 0 && book.getStatus() == BookStatus.ACTIVE) {
                selectedBooks.add(book);
                Future<Void> future = executorService.submit(() -> {
                    try {
                        updateBookStock(book);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });
                futures.add(future);
            } else {
                outOfStockBooks.add(book.getTitle());
            }
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        return selectedBooks;
    }

    private void updateBookStock(Book book) {
        Integer stock = Math.max((book.getStockQuantity() - 1), 0);
        book.setStockQuantity(stock);
        bookRepository.save(book);
    }

    private BigDecimal findTotalPrice(List<Book> books) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Book book : books) {
            total = total.add(book.getPrice());
        }
        return total;
    }

    private JWTUserDetail getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            return (JWTUserDetail) token.getPrincipal();
        }
        return null;
    }


    public ApiResponseModel<List<OrderResponse>> findOrdersByUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Order> orders = orderRepository.getUserOrders(userId);
            List<OrderResponse> orderResponses = orders.stream().map(OrderResponse::new).collect(Collectors.toList());
            return ApiResponseModel.create(orderResponses, userDetailMessage("user.orders.message", user.getName()), true);
        } else {
            return ApiResponseModel.create(message("order.post.user.error"), false);
        }
    }

    private String message(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }

    private String userDetailMessage(String name, String args) {
        return messageSource.getMessage(name, new Object[]{args}, LocaleContextHolder.getLocale());
    }

    public ApiResponseModel<OrderResponse> findOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            OrderResponse orderResponse = new OrderResponse(order);
            return ApiResponseModel.create(orderResponse, "", true);
        }
        return ApiResponseModel.create(message("orders.by.id.error"), false);
    }

    private String getOtOfStocksBooks() {
        return String.join(", ", this.outOfStockBooks);
    }
}
