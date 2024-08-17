package com.example.VideoRentalNew.bean;

import com.example.VideoRentalNew.model.Order;
import com.example.VideoRentalNew.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Component
@SessionScope
public class OrderBean implements Serializable {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserBean userBean;

    private List<Order> recentUserOrders;

    @PostConstruct
    public void init() {
        loadRecentUserOrders();
    }

    public void loadRecentUserOrders() {
        if (userBean.getCurrentUser() != null) {
            recentUserOrders = orderService.getRecentOrdersByUser(userBean.getCurrentUser().getId());
        }
    }

    public List<Order> getRecentUserOrders() {
        return recentUserOrders;
    }

    public void setRecentUserOrders(List<Order> recentUserOrders) {
        this.recentUserOrders = recentUserOrders;
    }
}
