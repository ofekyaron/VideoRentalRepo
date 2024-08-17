package com.example.VideoRentalNew.repository;

import com.example.VideoRentalNew.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    long countByReturnDateIsNull();

    @Query(value = "SELECT * FROM orders ORDER BY order_date DESC LIMIT :limit", nativeQuery = true)
    List<Order> findTopNOrderByOrderDateDesc(int limit);
}
