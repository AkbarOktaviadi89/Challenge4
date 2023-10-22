package com.restapi.challenge5.Service;

import com.restapi.challenge5.Model.Orders;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public interface OrdersService {
    List<Orders> getAllOrders();

    Optional<Orders> getById(Long id);

    Boolean addNewOrder(Orders order);
}
