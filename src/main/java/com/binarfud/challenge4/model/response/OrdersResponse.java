package com.binarfud.challenge4.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderResponse")
public class OrdersResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderResponse_id;

    private String nameProduct;

    private String nameCustomer;

    private String location;

    private int qty;

    private BigDecimal total_price;
}
