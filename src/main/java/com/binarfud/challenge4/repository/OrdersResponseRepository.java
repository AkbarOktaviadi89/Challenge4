package com.binarfud.challenge4.repository;

import com.binarfud.challenge4.model.Orders;
import com.binarfud.challenge4.model.response.OrdersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersResponseRepository extends JpaRepository<OrdersResponse, Long> {
    @Query(nativeQuery = true,
            value = "select * from order_response order by order_id asc")
    List<OrdersResponse> getAllOrders();

    @Query(nativeQuery = true, value = "select * from order_response")
    Page<OrdersResponse> findAllWithPaging(Pageable pageable);
}
