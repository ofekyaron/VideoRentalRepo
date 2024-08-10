package com.example.VideoRentalNew.dao;

import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.model.User;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Order order) {
        entityManager.persist(order);
    }

    public Order find(Long id) {
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

    public void delete(Long id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            entityManager.remove(order);
        }
    }
}
