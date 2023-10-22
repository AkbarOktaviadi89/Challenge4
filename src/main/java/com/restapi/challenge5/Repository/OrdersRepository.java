package com.restapi.challenge5.Repository;

import com.restapi.challenge5.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o WHERE o.orderId = :orderId")
    Optional<Orders> findByOrderId(Long orderId);
}
