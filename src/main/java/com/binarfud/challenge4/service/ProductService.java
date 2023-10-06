package com.binarfud.challenge4.service;

import com.binarfud.challenge4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> findAllProduct();
    Boolean addNewProduct(Product product);

    Boolean updateById(Long product_code, String product_name, Long price);

    Boolean deleteProductbyId(Long product_code);

    Page<Product> getProductPaged(int page);
}
