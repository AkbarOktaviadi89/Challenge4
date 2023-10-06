package com.binarfud.challenge4.service.Impl;

import com.binarfud.challenge4.model.OrderDetail;
import com.binarfud.challenge4.model.Orders;
import com.binarfud.challenge4.model.Product;
import com.binarfud.challenge4.model.Users;
import com.binarfud.challenge4.model.response.OrdersResponse;
import com.binarfud.challenge4.repository.*;
import com.binarfud.challenge4.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrdersResponseRepository ordersResponseRepository;

    @Override
    public List<OrdersResponse> findAllOrders() {
        log.info("Fetching all orders.");
        return ordersResponseRepository.getAllOrders();
    }

    @Override
    public Boolean createNewOrders(Long userId, Long productCode, String destinationAddress, int productQty, boolean isOpen) {
        log.info("Creating a new order...");

        Users users = usersRepository.findById(userId).orElse(null);

        Product product = productRepository.findById(productCode).get();

        Orders orders = Orders.builder()
                .destinationAddress(destinationAddress)
                .completed(isOpen)
                .users(users)
                .build();

        OrderDetail orderDetail = OrderDetail.builder()
                .quantity(productQty)
                .totalPrice(BigDecimal.valueOf(productQty * product.getPrice()))
                .build();

        OrdersResponse ordersResponse = OrdersResponse.builder()
                .nameProduct(product.getProductName())
                .nameCustomer(users != null ? users.getUsername() : "")
                .location(orders.getDestinationAddress())
                .qty(orderDetail.getQuantity())
                .total_price(orderDetail.getTotalPrice())
                .build();

        Boolean isOrderCreated = Optional.ofNullable(ordersResponseRepository.save(ordersResponse)).map(Objects::nonNull).orElse(Boolean.FALSE);

        if (isOrderCreated) {
            log.info("New order created successfully.");
        } else {
            log.error("Failed to create a new order.");
        }

        return isOrderCreated;
    }

    @Override
    public Page<OrdersResponse> getOrdersPaged(int page) {
        try {
            log.info("Retrieving paged orders for page {}.", (page + 1));
            return ordersResponseRepository.findAllWithPaging(PageRequest.of(page, 3));
        } catch (Exception e) {
            log.error("Error while retrieving paged orders: {}", e.getMessage(), e);
            return Page.empty();
        }
    }
}
