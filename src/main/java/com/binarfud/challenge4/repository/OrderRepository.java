package com.binarfud.challenge4.repository;

import com.binarfud.challenge4.model.Merchant;
import com.binarfud.challenge4.model.Orders;
import com.binarfud.challenge4.model.Product;
import com.binarfud.challenge4.model.response.OrdersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(nativeQuery = true,
            value = "select * from orders order by order_id asc")
    List<Orders> getAllOrders();

    @Query(nativeQuery = true, value = "select * from orders")
    Page<OrdersResponse> findAllWithPaging(Pageable pageable);
}
