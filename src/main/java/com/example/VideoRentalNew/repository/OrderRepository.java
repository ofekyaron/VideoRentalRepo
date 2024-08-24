package com.example.VideoRentalNew.repository;

import com.example.VideoRentalNew.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    long countByReturnDateIsNull();

    @Query(value = "SELECT o FROM Order o ORDER BY o.orderDate DESC")
    List<Order> findAllOrderByOrderDateDesc();

    @Query(value = "SELECT o FROM Order o WHERE o.userId = :userId ORDER BY o.orderDate DESC")
    List<Order> findRecentOrdersByUser(@Param("userId") Integer userId);

    @Query(value = "SELECT o FROM Order o WHERE o.returnDate IS NULL")
    List<Order> findActiveRentals();
}