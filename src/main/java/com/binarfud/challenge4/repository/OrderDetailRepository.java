package com.binarfud.challenge4.repository;

import com.binarfud.challenge4.model.OrderDetail;
import com.binarfud.challenge4.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
//    @Query(nativeQuery = true,
//            value = "select * from orders_detail order by product_code asc")
//    List<OrderDetail> getAllOrderDetail();
}
