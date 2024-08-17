package com.example.VideoRentalNew.dao;

import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.model.User;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


@Repository
@Transactional
public class OrderDAO {

    private final String url = "jdbc:sqlite:src/main/resources/db/video_rental.db";

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Order order) {
        entityManager.persist(order);
    }

    public Order find(Integer id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> findByUser(User user) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user = :user", Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Order> findAll() {
        return entityManager.createQuery("SELECT o FROM Order o", Order.class)
                .getResultList();
    }

    public void update(Order order) {
        entityManager.merge(order);
    }

    public void delete(Integer id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            entityManager.remove(order);
        }
    }

    public List<Order> getRecentOrdersByUser(Integer userId, int limit) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC LIMIT ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId((int) rs.getInt("id"));
                    order.setUser((int) rs.getInt("user_id"));
                    order.setMovie((int) rs.getInt("movie_id"));
                    order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());

                    Timestamp returnDate = rs.getTimestamp("return_date");
                    if (returnDate != null) {
                        order.setReturnDate(returnDate.toLocalDateTime());
                    }

                    orders.add(order);
                }
            }
        }

        return orders;
    }

}
