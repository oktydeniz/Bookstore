package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entitiy.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query(value = "SELECT * from orders where user_id =:userId order by updated_at desc;", nativeQuery = true)
    public List<Order> getUserOrders(Long userId);
}
